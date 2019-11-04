package com.example.orderingsystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderingsystem.structure.DishMenu;

public class DishMenuDBManager {
    public static final int DBVERSION = 1;

    public static final String DB_NAME = "DishMenu.db";
    public static final String TABLE = "usermenu";
    public static final String DISH_UID = "uid";
    public static final String DISH_MENUID = "dishmenuid";
    public static final String DISH_ID = "dishid";
    public static final String DISH_NAME = "dishname";
    public static final String DISH_NUM = "dishnum";
    public static final String DISH_PRICE = "dishprice";
    public static final String DISH_STATUS = "dishstatus";
    private final Context context;
    private SQLiteDatabase db;
    private DishDBHelper dbhelper;

    public DishMenuDBManager(Context context) {
        this.context = context;
    }

    //添加一个菜单
    public long insert(DishMenu dishmenu) {
        ContentValues cv = new ContentValues();
        cv.put(DISH_UID, dishmenu.uid);
        cv.put(DISH_ID, dishmenu.dishid);
        cv.put(DISH_NAME, dishmenu.dishname);
        cv.put(DISH_NUM, dishmenu.dishnum);
        cv.put(DISH_PRICE, dishmenu.dishprice);
        return db.insert(TABLE, null, cv);
    }

    //删除一个菜
    public void deleteonedish(DishMenu dishMenu, String uid) {
        db.delete(TABLE, DISH_ID + "='" + dishMenu.dishid + "'and " + DISH_UID + "='" + uid + "'", null);
    }

    //删除一个用户的菜单
    public void deletedishByUid(String uid) {
        db.delete(TABLE, DISH_UID + "='" + uid + "'", null);
    }

    public void open() {
        dbhelper = new DishDBHelper(context, DB_NAME, null, DBVERSION);
        try {
            db = dbhelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbhelper.getReadableDatabase();
        }
    }

    //查询一个菜单
    public DishMenu queryonedish(String uid, int id) {
        DishMenu adDishMenu = new DishMenu();
        Cursor cursor = db.query(TABLE,
                new String[]{DISH_UID, DISH_MENUID, DISH_ID,
                        DISH_NAME, DISH_NUM, DISH_PRICE},
                DISH_UID + "='" + uid + "'and " + DISH_ID + "='" +
                        id + "'", null, null, null, null, null);
        int count = cursor.getCount();
        if (count == 0 || !cursor.moveToFirst()) {
            System.out.println("数据表中没有数据");
            return null;
        } else {
            adDishMenu.uid = uid;
            adDishMenu.dishid = cursor.getInt(cursor.getColumnIndex(DISH_ID));
            adDishMenu.dishmenuid = cursor.getInt(cursor.getColumnIndex(DISH_MENUID));
            adDishMenu.dishname = cursor.getString(cursor.getColumnIndex(DISH_NAME));
            adDishMenu.dishnum = cursor.getInt(cursor.getColumnIndex(DISH_NUM));
            adDishMenu.dishprice = cursor.getDouble(cursor.getColumnIndex(DISH_PRICE));
            return adDishMenu;
        }
    }

    //查询一个菜单
    public int querydishIDbyName(String uid, String dishname) {
        Cursor cursor = db.query(TABLE,
                new String[]{DISH_UID, DISH_MENUID, DISH_ID,
                        DISH_NAME, DISH_NUM, DISH_PRICE},
                DISH_UID + "='" + uid + "'and " + DISH_NAME + "='" + dishname + "'",
                null, null, null, null, null);
        int count = cursor.getCount();
        if (count == 0) {
            System.out.println("数据表中没有数据");
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex(DISH_ID));
        }
    }

    //查询某个用户的菜单
    public DishMenu[] querydishByUser(String uid) {
        Cursor cursor = db.query(TABLE, new String[]{DISH_UID, DISH_MENUID, DISH_ID,
                        DISH_NAME, DISH_NUM, DISH_PRICE}, DISH_UID + "='" + uid + "'",
                null, null, null, null, null
        );
        int count = cursor.getCount();
        if (count == 0 || !cursor.moveToFirst()) {
            return null;
        } else {
            DishMenu[] dishlist = new DishMenu[count];
            for (int i = 0; i < count; i++) {
                dishlist[i] = new DishMenu();
                dishlist[i].uid = uid;
                dishlist[i].dishid = cursor.getInt(cursor.getColumnIndex(DISH_ID));
                dishlist[i].dishmenuid = cursor.getInt(cursor.getColumnIndex(DISH_MENUID));
                dishlist[i].dishname = cursor.getString(cursor.getColumnIndex(DISH_NAME));
                dishlist[i].dishnum = cursor.getInt(cursor.getColumnIndex(DISH_NUM));
                dishlist[i].dishprice = cursor.getDouble(cursor.getColumnIndex(DISH_PRICE));
                cursor.moveToNext();
            }
            return dishlist;
        }
    }

    //更新一条记录的数量
    public int updateNumemenu(String suid, int sid, int sdishnum) {
        ContentValues cv = new ContentValues();
        cv.put(DISH_NUM, sdishnum);
        return db.update(TABLE, cv, DISH_UID + "='" + suid + "'and " + DISH_ID + "='" + sid + "'" + DISH_STATUS + "= '0'", null);
    }
}
