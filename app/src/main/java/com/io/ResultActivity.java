package com.io;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.exp.ysy.demo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by ysy on 2015/3/16.
 */
public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview);
        ListView listView = (ListView) findViewById(R.id.list);
        Intent intent = getIntent();
        //获取该intent所携带的数据
        Bundle data = intent.getExtras();
        //从BUndle包中取出数据
        List<Map<String, String>> list = (List<Map<String, String>>) data.getSerializable("data");
        //将list封装成SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.line, new String[]{"word", "detail"},
                new int[]{R.id.my_title, R.id.my_content});
        listView.setAdapter(adapter);
    }
}
