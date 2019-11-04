package com.example.orderingsystem.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dish {
    public String id;
    public String title;
    public Integer imageID;
    public Double price;

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public void setDishItem(ArrayList<Object> dishes) {
        this.id = (String) dishes.get(0);
        this.title = (String) dishes.get(1);
        this.price = (Double) dishes.get(2);
        this.imageID = (Integer) dishes.get(3);

    }

    public ArrayList<Map<String, Object>> getFoodData(ArrayList<ArrayList<Object>> dishes) {
        ArrayList<Map<String, Object>> fooddata = new ArrayList<Map<String, Object>>();
        ArrayList<Object> dishkind;
        for (int i = 0; i < dishes.size(); i++) {
            dishkind = dishes.get(i);
            setDishItem(dishkind);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dishid", this.id);
            map.put("image", this.imageID);
            map.put("title", this.title);
            map.put("price", this.price);
            fooddata.add(map);
        }
        return fooddata;
    }
}
