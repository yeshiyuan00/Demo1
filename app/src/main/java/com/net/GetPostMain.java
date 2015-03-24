package com.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exp.ysy.demo.R;

public class GetPostMain extends Activity {

    Button get, post;
    EditText show;
    //代表服务器响应的字符串
    String response;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                show.setText(response);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post_main);
        get = (Button) findViewById(R.id.get);
        post = (Button) findViewById(R.id.post);
        show = (EditText) findViewById(R.id.show);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        response = GetPostUtil.sendGet("http://www.amsoft.cn/rss.php", null);
                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        response = GetPostUtil.sendPost("http://www.amsoft.cn/sort/10",
                                "param1=1&param2=2&param3=10");
                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });
    }


}
