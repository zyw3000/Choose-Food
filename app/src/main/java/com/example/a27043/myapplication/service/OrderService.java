package com.example.a27043.myapplication.service;

import android.content.Context;

import com.example.a27043.myapplication.entity.Food;
import com.example.a27043.myapplication.entity.Order;
import com.example.a27043.myapplication.entity.OrderDetail;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 27043 on 2016-05-11.
 */
public class OrderService {
    static List<Order> orders = new ArrayList<Order>();
    FoodService foodService;

    public OrderService(Context context) {
        foodService = new FoodService(context);
    }

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

    public  OrderDto getOrder(String code){
        OrderDto dto = new OrderDto();

        for(Order order : orders)
            if (order.code.equals(code)) {
                dto.order = order;
                break;
            }
        if (dto.order == null)
            return null;

        for (OrderDetail od : dto.order.orderDateils) {
            Food food = foodService.getFood(od.foodId);
            Map<String, Object> line = new HashMap<String, Object>();
            line.put("no", dto.orderedList.size() + 1);
            line.put("name", food.name);
            line.put("description", od.description);
            line.put("num", od.num);
            line.put("price", food.price);
            dto.orderedList.add(line);
            dto.sum += od.num * food.price;
        }
        return  dto;
    }


    public void  pay(int orderId){

    }

    public  static class OrderDto{
        public Order order;

        public int sum;

        public List<Map<String, Object>> orderedList = new ArrayList<Map<String, Object>>();
    }
    public  void addOrder(Order order){

    }
}
