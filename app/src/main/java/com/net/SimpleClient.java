package com.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.Buffer;

public class SimpleClient extends Activity {

    EditText show;
    String line;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    show.setText("来自服务器的消息：" + line);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_client);
        show = (EditText) findViewById(R.id.show);

        new Thread() {

            @Override
            public void run() {

                try {
                    //建立连接到远程服务器的socket
                    Socket socket = new Socket("192.168.9.33", 3000);
                    socket.setSoTimeout(10000);
                    //将socket对应的输入流包装成BufferedReader
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    //进行普通的I/O操作
                    line = br.readLine();
                    Message message = new Message();
                    message.what = 0x01;
                    handler.sendMessage(message);
                    //关闭输入流
                    br.close();
                    socket.close();
                } catch (SocketTimeoutException ex) {
                    Toast.makeText(SimpleClient.this, "网络超时", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
