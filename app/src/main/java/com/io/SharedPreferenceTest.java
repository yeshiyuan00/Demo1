package com.io;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ysy on 2015/3/13.
 */
public class SharedPreferenceTest extends Activity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sharedpreferences);
        //获取只能被本地应用读写的SharedPreference对象
        sharedPreferences = getSharedPreferences("Demo", MODE_WORLD_READABLE);
        editor = sharedPreferences.edit();
        Button read = (Button) findViewById(R.id.bn_read);
        Button write = (Button) findViewById(R.id.bn_write);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取字符串数据
                String time = sharedPreferences.getString("time", null);
                //读取int类型数据
                int readNum = sharedPreferences.getInt("random", 0);
                String result = time == null ? "您暂时还没有写数据" : "写入时间为：" +
                        time + "\n上次生成的随机数为：" + readNum;
                Toast.makeText(SharedPreferenceTest.this, result, Toast.LENGTH_LONG).show();
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日" + "hh:mm:ss");
                //存入当前时间
                editor.putString("time", sdf.format(new Date()));
                //存入一个随机数
                editor.putInt("random", (int) (Math.random() * 100));
                //提交所有存入的数据
                editor.commit();
            }
        });
    }
}
