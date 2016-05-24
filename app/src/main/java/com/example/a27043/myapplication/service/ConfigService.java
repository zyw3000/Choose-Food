package com.example.a27043.myapplication.service;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by 27043 on 2016-05-24.
 */
public class ConfigService {
    static final String CONFIG_FILE = "config";

    Context context;

    public ConfigService(Context context) {
        this.context = context;
    }

    public Properties read() throws IOException {
        createFile();
        Properties config = new Properties();
        FileInputStream fis = context.openFileInput(CONFIG_FILE);
        config.load(fis);
        fis.close();
        return config;
    }

    public void write(Properties config) throws IOException {
        createFile();
        FileOutputStream fos = context.openFileOutput(CONFIG_FILE, Context.MODE_PRIVATE);
        config.store(fos, null);
        fos.close();
    }

    private void createFile() throws IOException {
        File file = new File(context.getFilesDir(), CONFIG_FILE);
        if(!file.exists())
            file.createNewFile();
    }
}
