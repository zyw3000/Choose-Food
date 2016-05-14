package com.example.a27043.myapplication.entity;

/**
 * Created by 27043 on 2016-05-07.
 */
public class Food {
    public int id;
    public String code;
    public int typeId;
    public String name;
    public int price;
    public String description;

    @Override
    public String toString() {
        return code + " " + name + " ¥" + price;
    }
}
