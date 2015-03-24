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
import android.widget.ProgressBar;

import com.exp.ysy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class MultiThreadDown extends Activity {
    EditText url, target;
    Button downBn;
    ProgressBar bar;

    DownUtil downUtil;

    private int mDownStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread_down);
        url = (EditText) findViewById(R.id.url);
        target = (EditText) findViewById(R.id.target);
        bar = (ProgressBar) findViewById(R.id.bar);
        downBn = (Button) findViewById(R.id.downbn);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    bar.setProgress(mDownStatus);
                }
            }
        };

        downBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化 DownUtil对象，最后一个参数指定线程数
                downUtil = new DownUtil(url.getText().toString(), target.getText().toString(), 6);
                new Thread() {

                    @Override
                    public void run() {

                        try {
                            //开始下载
                            downUtil.download();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //定义每秒调度获取一次系统的完成进度
                        final Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                //获取下载任务的完成比例
                                double completeRate = downUtil.getCompleteRate();
                                mDownStatus = (int) (completeRate * 100);
                                handler.sendEmptyMessage(0x123);
                                //下载完成后取消任务调度
                                if (mDownStatus >= 100) {
                                    timer.cancel();
                                }
                            }
                        }, 0, 100);
                    }
                }.start();
            }
        });
    }


}
