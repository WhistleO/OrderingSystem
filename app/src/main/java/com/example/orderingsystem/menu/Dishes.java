package com.example.orderingsystem.menu;

import com.example.orderingsystem.R;

import java.util.ArrayList;

public class Dishes {
    private static ArrayList<ArrayList<Object>> DishesArray = new ArrayList<ArrayList<Object>>();
    private ArrayList<Object> dishitem;

    // 获取热菜菜品信息，存放在ArrayList中
    public ArrayList<ArrayList<Object>> getReCaiDishes() {
        DishesArray.clear();

        dishitem = new ArrayList<Object>();
        dishitem.add("1001");
        dishitem.add("宫保鸡丁");
        dishitem.add(20.0);
        dishitem.add(R.drawable.gongbaojiding);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("1002");
        dishitem.add("椒盐玉米");
        dishitem.add(24.0);
        dishitem.add(R.drawable.jiaoyanyumi);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("1003");
        dishitem.add("清蒸武昌鱼");
        dishitem.add(48.0);
        dishitem.add(R.drawable.qingzhengwuchangyu);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("1004");
        dishitem.add("鱼香肉丝");
        dishitem.add(25.0);
        dishitem.add(R.drawable.yuxiangrousi);
        DishesArray.add(dishitem);

        return DishesArray;
    }

    // 获取凉菜菜品信息，存放在ArrayList中
    public ArrayList<ArrayList<Object>> getLiangCaiDishes() {
        DishesArray.clear();

        dishitem = new ArrayList<Object>();
        dishitem.add("2001");
        dishitem.add("凉拌莲菜");
        dishitem.add(10.0);
        dishitem.add(R.drawable.liancai);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("2002");
        dishitem.add("拌三丝");
        dishitem.add(15.0);
        dishitem.add(R.drawable.bansansi);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("2003");
        dishitem.add("酱牛肉");
        dishitem.add(40.0);
        dishitem.add(R.drawable.jiangniurou);
        DishesArray.add(dishitem);

        return DishesArray;
    }

    // 获取主食菜品信息
    public ArrayList<ArrayList<Object>> getZhushiDishes() {
        DishesArray.clear();

        dishitem = new ArrayList<Object>();
        dishitem.add("3001");
        dishitem.add("米饭");
        dishitem.add(2.0);
        dishitem.add(R.drawable.mifan);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("3002");
        dishitem.add("馒头");
        dishitem.add(1.0);
        dishitem.add(R.drawable.mantou);
        DishesArray.add(dishitem);

        return DishesArray;
    }

    // 获取汤类菜品信息
    public ArrayList<ArrayList<Object>> getTangDishes() {
        DishesArray.clear();

        dishitem = new ArrayList<Object>();
        dishitem.add("4001");
        dishitem.add("紫菜蛋花汤");
        dishitem.add(5.0);
        dishitem.add(R.drawable.zicaidanhuatang);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("4002");
        dishitem.add("青菜汤");
        dishitem.add(4.0);
        dishitem.add(R.drawable.qincaitang);
        DishesArray.add(dishitem);

        return DishesArray;
    }

    // 获取饮料菜品信息
    public ArrayList<ArrayList<Object>> getYinliaoDishes() {
        DishesArray.clear();

        dishitem = new ArrayList<Object>();
        dishitem.add("5001");
        dishitem.add("水");
        dishitem.add(2.0);
        dishitem.add(R.drawable.shui);
        DishesArray.add(dishitem);

        dishitem = new ArrayList<Object>();
        dishitem.add("5002");
        dishitem.add("果汁");
        dishitem.add(3.0);
        dishitem.add(R.drawable.guozhi);
        DishesArray.add(dishitem);

        return DishesArray;
    }
}

