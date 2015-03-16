package com.images;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/11.
 */
public class Butterfly extends Activity {

    //记录蝴蝶当前位置
    private float currentX = 0;
    private float currentY = 960;
    //蝴蝶下一个位置
    private float nextX = 0;
    private float nextY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_butterfly);

        final ImageView imageView = (ImageView) findViewById(R.id.image);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    //横向上一直向右飞
                    if (nextX > 1080) {
                        currentX = nextX = 0;
                    } else {
                        nextX += 8;
                    }
                    //纵向上可以随机上下飞
                    nextY = currentY + (float) (Math.random() * 20 - 10);
                    //设置显示蝴蝶的IMAGeView发生位移改变
                    TranslateAnimation anim = new TranslateAnimation(currentX, nextX,
                            currentY, nextY);
                    currentX = nextX;
                    currentY = nextY;
                    anim.setDuration(200);
                    imageView.startAnimation(anim);
                }
            }
        };

        final AnimationDrawable butterfly = (AnimationDrawable) imageView.getBackground();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butterfly.start();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0x123);
                    }
                }, 0, 200);
            }
        });
    }
}
