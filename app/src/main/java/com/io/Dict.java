package com.io;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ysy on 2015/3/16.
 */
public class Dict extends Activity {
    private EditText edt_word, edt_detail, edt_key;
    private Button bn_insert, bn_search;

    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dict);
        edt_word = (EditText) findViewById(R.id.edt_word);
        edt_detail = (EditText) findViewById(R.id.edt_detail);
        edt_key = (EditText) findViewById(R.id.edt_key);
        bn_insert = (Button) findViewById(R.id.bn_insert);
        bn_search = (Button) findViewById(R.id.bn_search);
        //创建MyDatabaseHelper对象，指定数据库版本号为1，此处使用相对路径即可
        //数据库文件自动会保存在程序的数据文件夹的database目录下
        dbHelper = new MyDatabaseHelper(this, "myDict.db3", 1);
        bn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入
                String word = edt_word.getText().toString();
                String detail = edt_detail.getText().toString();
                //插入生词记录
                insertData(dbHelper.getReadableDatabase(), word, detail);
                Toast.makeText(Dict.this, "添加生词成功", Toast.LENGTH_SHORT).show();
            }
        });

        bn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入
                String key = edt_key.getText().toString();
                //执行查询
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                        "select * from dict where word like ? or detail like ?",
                        new String[]{"%" + key + "%", "%" + key + "%"});
                //创建一个Bundle对象
                Bundle data = new Bundle();
                data.putSerializable("data", converCursorToList(cursor));
                //创建一个Intent
                Intent intent = new Intent(Dict.this, ResultActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    protected ArrayList<Map<String, String>> converCursorToList(Cursor cursor) {

        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        //遍历cursor结果集
        while (cursor.moveToNext()) {
            //将结果集中的数据存入ArrayList中
            Map<String, String> map = new HashMap<String, String>();
            //取出查询记录中的第2、第3列的值
            map.put("word", cursor.getString(1));
            map.put("detail", cursor.getString(2));
            result.add(map);
        }

        return result;
    }

    private void insertData(SQLiteDatabase db, String word, String detail) {
        //执行插入语句
        db.execSQL("insert into dict values(null,?,?)", new String[]{word, detail});

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
