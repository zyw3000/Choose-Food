package com.example.a27043.myapplication.service;

import com.example.a27043.myapplication.entity.Food;
import com.example.a27043.myapplication.entity.FoodType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27043 on 2016-05-11.
 */
public class FoodService {
    static List<FoodType> types = new ArrayList<FoodType>();
    static List<Food> foods = new ArrayList<Food>();

    static {
        FoodType t = new FoodType();
        t.id = 0;
        t.name = "全部";
        types.add(t);
        t.id = 1;
        t.name = "热菜";
        types.add(t);
        t.id = 2;
        t.name = "凉菜";
        types.add(t);
        t.id = 3;
        t.name = "烧烤";
        types.add(t);
        t.id = 4;
        t.name = "酒水";
        types.add(t);
        t.id = 5;
        t.name = "主食";
        types.add(t);
    }

    static {
        for(int i = 1; i <= 40; i++){
            Food f = new Food();
            f.id = i;
            f.code = "FOOD" + i;
            f.typeId = i % 5 + 1;
            f.name = "餐品" + i;
            f.price = (i % 7 + 1) * 10;
            foods.add(f);
        }
    }

    public List<FoodType> getFoodTypes() {
        return types;
    }

    public List<Food> getFoods(int foodTypeId) {
        if(foodTypeId == 0)
            return foods;
        List<Food> list = new ArrayList<Food>();
        for(Food f : foods)
            if(f.typeId == foodTypeId)
                list.add(f);
        return list;
    }
}
