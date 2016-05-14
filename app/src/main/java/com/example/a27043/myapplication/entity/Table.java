package com.example.a27043.myapplication.entity;

/**
 * Created by 27043 on 2016-05-07.
 */
public class Table {
    public int id;
    public String code;
    public int seats;
    public int customers;
    public String description;

    @Override
    public String toString() {
        return code;
    }
}
