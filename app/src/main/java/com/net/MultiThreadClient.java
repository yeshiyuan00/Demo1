package com.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exp.ysy.demo.R;

public class MultiThreadClient extends Activity {
    EditText input;
    Button send;
    TextView show;
    Handler handler;
    //定义与服务器通信的子线程
    ClientThread clientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread_client);
        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);
        show = (TextView) findViewById(R.id.show);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //如果消息来自于子线程
                if (msg.what == 0x123) {
                    show.append("\n" + msg.obj.toString());
                }
            }
        };
        clientThread = new ClientThread(handler);
        //客户端启动clientThread线程创建网络连接、读取服务器的数据
        new Thread(clientThread).start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 0x345;
                message.obj = input.getText().toString();
                clientThread.revHandler.sendMessage(message);

                input.setText("");
            }
        });
    }


}
