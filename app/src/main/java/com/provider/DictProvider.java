package com.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.io.MyDatabaseHelper;

/**
 * Created by ysy on 2015/3/18.
 */
public class DictProvider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int WORDS = 1;
    private final static int WORD = 2;
    private MyDatabaseHelper dbOpenHelper;

    static {
        //为UriMatcher注册两个Uri
        matcher.addURI(Words.AUTHORITY, "words", WORDS);
        matcher.addURI(Words.AUTHORITY, "word/#", WORD);
    }

    //第一调用该DictProvider时，系统先创建DictProvider对象，并回调该方法
    @Override
    public boolean onCreate() {
        dbOpenHelper = new MyDatabaseHelper(this.getContext(), "myDict.db3", 1);
        return true;
    }

    //查询数据的方法
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)) {
            //若果uri参数带表操作全部数据项
            case WORDS:
                //执行查询
                return db.query("dict", projection, selection, selectionArgs, null, null, sortOrder);

            //若果uri参数带表操作指定数据项
            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;
                //如果原来的seletion子句存在，连接seletion子句
                if (selection != null && !"".equals(selection)) {
                    whereClause = whereClause + "and" + selection;
                }
                return db.query("dict", projection, whereClause, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("未知Uri：" + uri);

        }

    }

    //返回Uri参数对应的数据的MIME类型
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            //若果操作的数据是多项记录
            case WORDS:
                return "vnd.android.cursor.dir/com.exp.ysy.demo.dict";
            //若果操作的数据是单项记录
            case WORD:
                return "vnd.android.cursor.item/com.exp.ysy.demo.dict";
            default:
                throw new IllegalArgumentException("未知Uri：" + uri);
        }
    }

    //插入数据的方法
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        //获取数据库实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)) {
            //若果uri参数带表操作全部数据项
            case WORDS:
                //插入数据，返回插入记录的ID
                long rowID = db.insert("dict", Words.Word._ID, values);
                //如果插入成功返回uri
                if (rowID > 0) {
                    //在已有的uri后面追加ID
                    Uri wordUri = ContentUris.withAppendedId(uri, rowID);
                    //通知数据已经改变
                    getContext().getContentResolver().notifyChange(wordUri, null);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri：" + uri);

        }
        return null;
    }

    //删除数据的方法
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //获取数据库实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        //记录删除的记录数
        int num = 0;
        switch (matcher.match(uri)) {
            //若果uri参数带表操作全部数据项
            case WORDS:
                num = db.delete("dict", selection, selectionArgs);
                break;
            //若果操作的数据是单项记录
            case WORD:
                //解析出所需删除记录的id
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;

                if (selection != null && !"".equals(selection)) {
                    whereClause = whereClause + "and" + selection;
                }
                num = db.delete("dict", whereClause, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("未知Uri：" + uri);
        }

        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //获取数据库实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        //记录修改的记录数
        int num = 0;
        switch (matcher.match(uri)) {
            case WORDS:
                num = db.update("dict", values, selection, selectionArgs);
                break;
            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;

                if (selection != null && !"".equals(selection)) {
                    whereClause = whereClause + "and" + selection;
                }
                num = db.update("dict", values, whereClause, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("未知Uri：" + uri);
        }
        return num;
    }
}
