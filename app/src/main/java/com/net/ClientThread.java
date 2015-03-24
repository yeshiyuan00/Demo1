package com.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ysy on 2015/3/24.
 */
public class ClientThread implements Runnable {
    private Socket s;
    //定义向UI线程发送消息的Handler对象
    private Handler handler;
    //定义接收UI线程消息的Handler对象
    public Handler revHandler;
    //该线程处理socket所对应的输入流
    BufferedReader br = null;
    OutputStream os = null;

    public ClientThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        try {
            s = new Socket("192.168.9.33", 30000);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            //启动一条子线程来读服务器数据
            new Thread() {

                @Override
                public void run() {
                    String content = null;
                    //不断读取socket输入流中的内容
                    try {
                        while ((content = br.readLine()) != null) {
                            //读到数据，发消息界面显示
                            Message message = new Message();
                            message.what = 0x123;
                            message.obj = content;
                            handler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            //为当前线程初始化Looper
            Looper.prepare();
            //创建revHandler对象
            revHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //接收到UI线程中用户输入的数据
                    if (msg.what == 0x345) {
                        //将用户在文本框的输入写入网络
                        try {
                            os.write((msg.obj.toString() + "\r\n").getBytes("utf-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //启动Looper
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
