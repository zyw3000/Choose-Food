package com.example.a27043.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.a27043.myapplication.R;

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
                        showMessageDialog("结账", R.drawable.info, null);
                        break;
                    case 2:
                        showMessageDialog("查桌", R.drawable.info, null);
                        break;
                    case 3:
                        showMessageDialog("更新数据", R.drawable.info, null);
                        break;
                    case 4:
                        showMessageDialog("设置", R.drawable.info, null);
                        break;
                }
            }
        });
    }

    @Override
    protected String getName() {
        return "主菜单";
    }

}
