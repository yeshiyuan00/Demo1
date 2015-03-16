package com.io;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ysy on 2015/3/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    final String CREAT_TABLE_SQL = "create table dict(_id integer primary key autoincrement," +
            "word,detail)";

    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次使用数据库时自动建表
        db.execSQL(CREAT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("-----onUpdate Called-----" + oldVersion + "--->" + newVersion);
    }
}
