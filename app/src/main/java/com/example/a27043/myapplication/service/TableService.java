package com.example.a27043.myapplication.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a27043.myapplication.entity.Table;
import com.example.a27043.myapplication.sql.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27043 on 2016-05-11.
 */
public class TableService {
    DbHelper dbHelper;

    public TableService(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<Table> getTables() {
        List<Table> list = new ArrayList<Table>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("tables", null, null, null, null, null ,null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Table t = new Table();
            t.id = cursor.getInt(0);
            t.code = cursor.getString(1);
            t.seats = cursor.getInt(2);
            t.description = cursor.getString(3);
            list.add(t);
        }
        cursor.close();
        db.close();
        return list;
    }
}
