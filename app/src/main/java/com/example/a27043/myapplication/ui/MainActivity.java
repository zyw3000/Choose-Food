package com.example.a27043.myapplication.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.a27043.myapplication.R;
import com.example.a27043.myapplication.service.UpdateDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 27043 on 2016-05-11.
 */
public class MainActivity extends BasicActivity{
    int[] icons = { R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5,};
    String[] iconTexts = {"点餐","结账","查桌","更新数据","设置"};
    GridView gdv;

    ProgressDialog updateDialog;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gdv = (GridView) findViewById(R.id.gdv);

        List<Map<String, Object>> iconList = new ArrayList<Map<String, Object>>();
        for(int i = 0, j = icons.length; i < j; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageView", icons[i]);
            map.put("imageTitle", iconTexts[i]);
            iconList.add(map);
        }

        gdv.setAdapter(new SimpleAdapter(this, iconList, R.layout.mainitem,
                new String[] {"imageView", "imageTitle"}, new int[]{
                    R.id.imageView, R.id.imageTitle}));
        gdv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int idx, long arg3) {
                switch (idx){
                    case 0:
                        showActivity(MainActivity.this, OrderActivity.class);
                        break;
                    case 1:
                        showActivity(MainActivity.this, PayActivity.class);
                        break;
                    case 2:
                        showMessageDialog("查桌", R.drawable.info, null);
                        break;
                    case 3:
                        updateData();
                        break;
                    case 4:
                        showActivity(MainActivity.this, ConfigActivity.class);
                        break;
                }
            }
        });

        updateDialog = new ProgressDialog(MainActivity.this);
        updateDialog.setMessage("正在更新数据，请稍后......");
        updateDialog.setCancelable(false);
        IntentFilter f = new IntentFilter();
        f.addAction(UpdateDataService.OK);
        f.addAction(UpdateDataService.EXCEPTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateDialog.hide();
                String action = intent.getAction();
                if (UpdateDataService.OK.equals(action))
                    showMessageDialog("更新完成", R.drawable.info, null);
                else if (UpdateDataService.EXCEPTION.equals(action))
                    showMessageDialog("更新失败: " + intent.getStringExtra(UpdateDataService.EXCEPTION) ,
                            R.drawable.warning, null);
            }
        };
        registerReceiver(broadcastReceiver, f);
    } 

    private void updateData() {
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setIcon(R.drawable.warning);
        b.setTitle("更新需要较长时间，确定需要更新吗？");
        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDialog.show();
                startService(new Intent(MainActivity.this,
                        UpdateDataService.class));
            }
        });
        b.setNegativeButton("取消", null);
        b.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected String getName() {
        return "主菜单";
    }

}
