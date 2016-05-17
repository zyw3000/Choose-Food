package com.example.a27043.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by 27043 on 2016-05-17.
 */
public class UpdateDataService extends Service{

    public static final String OK = "OK";
    public static final String EXCEPTION = "EXCEPTION";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                try {
                    updateData();
                    sendBroadcast(new Intent(OK));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(EXCEPTION);
                    intent.putExtra(EXCEPTION, e.getMessage());
                    sendBroadcast(intent);
                }
                stopSelf();
            }
        }.start();
    }

    public void updateData() throws InterruptedException {
        Thread.sleep(3000);
    }
}
