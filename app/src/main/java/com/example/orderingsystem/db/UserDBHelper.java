package com.example.orderingsystem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {
    public static final int DBVERSION = 1;
    public static final String DB_NAME = "DishMenu.db";
    public static final String TABLE = "UserInfo";
    public static final String UID = "uid";
    public static final String NAME = "name";
    public static final String UKEY = "ukey";
    public static final String TRUENAME = "truename";
    public static final String SEX = "sex";
    public static final String AGE = "age";
    public static final String PHONE = "phone";
    public static final String COUNTRY = "country";
    public static final String HOBBY = "hobby";
    public static final String ADDRESS = "address";

    public static final String DISHTABLE = "usermenu";
    public static final String DISH_UID = "uid";
    public static final String DISH_MENUID = "dishmenuid";
    public static final String DISH_ID = "dishid";
    public static final String DISH_NAME = "dishname";
    public static final String DISH_NUM = "dishnum";
    public static final String DISH_PRICE = "dishprice";
    public static final String DISH_STATUS = "dishstatus";


    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE + "(" + UID + " text primary key," +
                NAME + " text," + UKEY + " text," + TRUENAME + " text, " +
                SEX + " text, " + AGE + " integer," + PHONE + " text," + COUNTRY + " text,"
                + HOBBY + " text, " + ADDRESS + " text );";
        System.out.println("db=" + sql);
        db.execSQL(sql);
        sql = "create table " + DISHTABLE + "(" + DISH_MENUID + " integer primary key autoincrement," +
                DISH_ID + " integer," + DISH_UID + " text," + DISH_NAME + " text, " +
                DISH_NUM + " integer, " + DISH_PRICE + " float," + DISH_STATUS + "text );";
        System.out.println("db=" + sql);
        db.execSQL(sql);
    }

}
