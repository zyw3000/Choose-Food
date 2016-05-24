package com.example.a27043.myapplication.service;

import android.app.Application;

import com.example.a27043.myapplication.entity.User;

import java.io.IOException;

/**
 * Created by 27043 on 2016-05-07.
 */
public class App extends Application{
    public User user;

    public String serverUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigService configService = new ConfigService(this);
        try {
            serverUrl = configService.read().getProperty("serverUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
