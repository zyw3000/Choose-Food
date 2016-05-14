package com.example.a27043.myapplication.service;

import com.example.a27043.myapplication.entity.Order;
import com.example.a27043.myapplication.entity.OrderDetail;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27043 on 2016-05-11.
 */
public class OrderService {
    static List<Order> orders = new ArrayList<Order>();

    static {
        Order o = new Order();
        o.id = 1;
         o.code = "1";
        o.tableId = 1;
        o.waiterId = 1;
        o.orderTime = new Date();
        o.customers = 4;
        o.status = 1;
        o.orderDateils = new ArrayList<OrderDetail>();
        for (int i = 1; i <= 5; i++){
            OrderDetail od = new OrderDetail();
            od.id = i;
            od.orderId = 1;
            od.foodId = i;
            od.num = 1;
            o.orderDateils.add(od);
        }
        orders.add(o);

        o = new Order();
        o.id = 2;
        o.code = "2";
        o.tableId = 2;
        o.waiterId = 2;
        o.orderTime = new Date();
        o.customers = 10;
        o.status = 0;
        o.orderDateils = new ArrayList<OrderDetail>();
        for (int i = 6; i <= 15; i++){
            OrderDetail od = new OrderDetail();
            od.id = i;
            od.orderId = 2;
            od.foodId = i;
            od.num = 1;
            o.orderDateils.add(od);
        }
        orders.add(o);
    }

    public  void addOrder(Order order){

    }
}
