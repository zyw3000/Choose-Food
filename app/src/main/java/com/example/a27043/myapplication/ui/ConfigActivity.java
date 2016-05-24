package com.example.a27043.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a27043.myapplication.R;
import com.example.a27043.myapplication.service.App;
import com.example.a27043.myapplication.service.ConfigService;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by 27043 on 2016-05-24.
 */
public class ConfigActivity extends BasicActivity {
    EditText serverUrlEdt;
    Button okBtn;
    Button cancelBtn;

    ConfigService configService;
    Properties config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        configService = new ConfigService(this);

        serverUrlEdt = (EditText) findViewById(R.id.serverUrlEdt);
        okBtn = (Button) findViewById(R.id.okBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        try {
            config = configService.read();
            serverUrlEdt.setText(config.getProperty("serverUrl"));
        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog("配置文件读取失败: " + e.getMessage(), R.drawable.warning, null);
            config = new Properties();
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverUrl = serverUrlEdt.getText().toString();
                config.put("serverUrl", serverUrl);
                App app = (App) getApplicationContext();
                app.serverUrl = serverUrl;

                try {
                    configService.write(config);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                    showMessageDialog("配置文件保存失败: " + e.getMessage(), R.drawable.warning, null);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigActivity.this.finish();
            }
        });
    }

    @Override
    protected String getName() {
        return "配置";
    }
}
