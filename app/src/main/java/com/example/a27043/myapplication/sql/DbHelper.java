package com.example.a27043.myapplication.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 27043 on 2016-05-17.
 */
public class DbHelper extends SQLiteOpenHelper{

    public static String dbName = "flyrestaurant.db";

    Context context;

    public DbHelper(Context context){
        super(context, dbName, null, 1);
        this.context = context;
    }

    public void deleteDb() {
        context.deleteDatabase(dbName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists food (" +
                "id integer primary key," +
                "code varchar(5)," +
                "type_id integer," +
                "name varchar(20)," +
                "price integer," +
                "description varchar(100))");
        db.execSQL("create table if not exists food_type (" +
                "id integer primary key," +
                "name varchar(10))");
        db.execSQL("create table if not exists tables (" +
                "id integer primary key," +
                "code varchar(5)," +
                "seats integer," +
                "description varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
