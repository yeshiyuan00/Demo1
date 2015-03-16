package com.io;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.exp.ysy.demo.R;


/**
 * Created by ysy on 2015/3/16.
 */
public class DBTest extends Activity {
    SQLiteDatabase db;
    private EditText edt_title;
    private EditText edt_content;
    private Button bn_insert;
    private ListView lv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dbtest);
        edt_title = (EditText) findViewById(R.id.title);
        edt_content = (EditText) findViewById(R.id.content);
        bn_insert = (Button) findViewById(R.id.bn_insert);
        lv_show = (ListView) findViewById(R.id.show);
        //创建或打开数据库，此处需要使用绝对路径
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/my.db3",
                null);

        bn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString();
                String content = edt_content.getText().toString();

                try {
                    insertData(db, title, content);
                    Cursor cursor = db.rawQuery("select * from news_inf", null);
                    inflateList(cursor);
                } catch (SQLiteException se) {
                    //执行DDL创建数据表
                    db.execSQL("create table news_inf(_id integer primary " +
                            "key autoincrement,"
                            + " news_title varchar(50),"
                            + " news_content varchar(255))");
                    //执行insert语句插入数据
                    insertData(db, title, content);
                    //执行查询
                    Cursor cursor = db.rawQuery("select * from news_inf", null);
                    inflateList(cursor);

                }

            }
        });

    }

    private void insertData(SQLiteDatabase db, String title, String content) {
        //执行插入语句
        db.execSQL("insert into news_inf values(null,?,?)", new String[]{title, content});

    }

    private void inflateList(Cursor cursor) {

        //填充SimpleCursorAdapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(DBTest.this,
                R.layout.line, cursor, new String[]{"news_title", "news_content"},
                new int[]{R.id.my_title, R.id.my_content},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        //显示数据
        lv_show.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出时关闭SQLiteDatabase
        if ((db != null) && db.isOpen()) {
            db.close();
        }
    }
}
