package com.example.a27043.myapplication.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a27043.myapplication.entity.Food;
import com.example.a27043.myapplication.entity.FoodType;
import com.example.a27043.myapplication.sql.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27043 on 2016-05-11.
 */
public class FoodService {
    DbHelper dbHelper;

    public FoodService(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<FoodType> getFoodTypes() {
        List<FoodType> list = new ArrayList<FoodType>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("food_type", null, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            FoodType t = new FoodType();
            t.id = cursor.getInt(0);
            t.name = cursor.getString(1);
            list.add(t);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Food> getFoods(int foodTypeId) {
        List<Food> list = new ArrayList<Food>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = null;
        if (foodTypeId != 0)
            where = "type_id = " + foodTypeId;
        Cursor cursor = db.query("food", null, where, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Food t = new Food();
            t.id = cursor.getInt(0);
            t.code = cursor.getString(1);
            t.typeId = cursor.getInt(2);
            t.name = cursor.getString(3);
            t.price = cursor.getInt(4);
            t.description = cursor.getString(5);
            list.add(t);
        }
        cursor.close();
        db.close();
        return list;
    }

    public Food getFood(int foodId){
        Food t = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("food", null, "id = " + foodId, null, null, null, null);
        if (cursor.moveToFirst() ) {
            t = new Food();
            t.id = cursor.getInt(0);
            t.code = cursor.getString(1);
            t.typeId = cursor.getInt(2);
            t.name = cursor.getString(3);
            t.price = cursor.getInt(4);
            t.description = cursor.getString(5);
        }
        cursor.close();
        db.close();
        return t;
    }
}
