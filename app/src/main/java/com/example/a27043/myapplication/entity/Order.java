package com.example.a27043.myapplication.entity;


import java.util.Date;
import java.util.List;

/**
 * Created by 27043 on 2016-05-07.
 */
public class Order {
    public int id;
    public String code;
    public int tableId;
    public int waiterId;
    public Date orderTime;
    public int customers;
    public int status;
    public String description;

    public List<OrderDetail> orderDateils;
}
