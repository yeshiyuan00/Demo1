package com.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by ysy on 2015/3/17.
 */
public class FirstProvider extends ContentProvider {
    //第一次创建ContentProvider时调用该方法
    @Override
    public boolean onCreate() {

        System.out.println("===onCreat方法被调用===");
        return false;
    }

    //实现查询方法，该方法应该返回查询得到的Cursor
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        System.out.println(uri + "===query方法被调用===");
        System.out.println("where 参数为：" + selection);
        return null;
    }

    //该方法的返回值代表了该ContentProvider所提供数据的MIME类型
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //实现插入的方法，该方法应该返回新插入的记录的URi
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        System.out.println(uri + "===insert方法被调用===");
        System.out.println("values 参数为：" + values);
        return null;
    }

    //实现删除方法，该方法应该返回被更新的记录条数
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        System.out.println(uri + "===delete方法被调用===");
        System.out.println("where 参数为：" + selection);
        return 0;
    }

    //实现更新方法，该方法应该返回被更新的记录条数
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        System.out.println(uri + "===update方法被调用===");
        System.out.println("where 参数为：" + selection + ",value 参数为：" + values);
        return 0;
    }
}
