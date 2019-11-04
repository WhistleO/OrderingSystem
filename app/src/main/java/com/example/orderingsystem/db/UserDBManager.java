package com.example.orderingsystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderingsystem.structure.User;

public class UserDBManager {
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
    private final Context context;
    private SQLiteDatabase db;
    private UserDBHelper dbhelper;

    public UserDBManager(Context context) {
        this.context = context;
    }

    public void open() {
        dbhelper = new UserDBHelper(context, DB_NAME, null, DBVERSION);
        try {
            db = dbhelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbhelper.getReadableDatabase();
        }
    }

    // 添加一个用户
    public long insert(User userinfo) {
        ContentValues cv = new ContentValues();
        cv.put(UID, userinfo.uid);
        cv.put(NAME, userinfo.name);
        cv.put(UKEY, userinfo.ukey);
        cv.put(TRUENAME, userinfo.truename);
        cv.put(AGE, userinfo.age);
        return db.insert(TABLE, null, cv);
    }

    // 查询用户是否存在 存在true
    public boolean haveUid(String uid) {
        Cursor cursor = db.query(TABLE,
                new String[]{UID, UKEY},
                UID + "='" + uid + "'",
                null, null, null, null, null);
        int count = cursor.getCount();
        if (count == 0) {
            System.out.println("用户不存在");
            return false;
        } else {
            return true;
        }
    }

    // 检查密码
    public boolean checkKey(String uid, String ukey) {
        Cursor cursor = db.query(TABLE,
                new String[]{UID, UKEY},
                UID + "='" + uid + "' and " + UKEY + "='" + ukey + "'",
                null, null, null, null, null);
        int count = cursor.getCount();
        if (count == 0) {
            System.out.println("密码错误");
            return false;
        } else {
            return true;
        }
    }

    // 获取User信息:用户名 电话号码 通讯地址
    public User getInfo(String uid) {
        Cursor cursor = db.query(TABLE,
                new String[]{UID, NAME, PHONE, ADDRESS},
                UID + "='" + uid + "'",
                null, null, null, null, null);
        User data = new User();// 存储数据
        int count = cursor.getCount();
        if (count == 0 || !cursor.moveToFirst()) {
            System.out.println("数据表中没有数据");
            return null;
        } else {
            data.uid = uid;
            data.name = cursor.getString(cursor.getColumnIndex(NAME));
            data.phone = cursor.getString(cursor.getColumnIndex(PHONE));
            data.address = cursor.getString(cursor.getColumnIndex(ADDRESS));
            return data;
        }
    }

    // 修改信息
    public int modifyInfo(User data) {
        ContentValues cv = new ContentValues();
        cv.put(UKEY, data.ukey);
        cv.put(PHONE, data.phone);
        cv.put(ADDRESS, data.address);
        return db.update(TABLE, cv, UID + "='" + data.uid + "'", null);
    }

}

