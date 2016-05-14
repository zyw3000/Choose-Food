package com.example.a27043.myapplication.service;

import com.example.a27043.myapplication.entity.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27043 on 2016-05-11.
 */
public class TableService {
    static List<Table> tables = new ArrayList<Table>();

    static {
        for (int i = 1; i <= 20; i++){
            Table t = new Table();
            t.id = i;
            t.code = "TABLE" + i;
            t.seats = i % 5 * 2 + 2;
            t.customers = i % 3 == 0 ? t.seats : 0;
            t.description = i % 4 == 0 ? "靠窗" : "";
            tables.add(t);
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}
