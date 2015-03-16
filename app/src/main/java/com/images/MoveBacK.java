package com.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.exp.ysy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/11.
 */
public class MoveBacK extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        //记录背景图的实际高度
        final int BACK_HEIGHT = 1800;
        //背景图片
        private Bitmap back;
        private Bitmap plane;
        //背景图片的开始位置
        final int WIDTH = 880;
        final int HEIGHT = 880;
        private int startY = BACK_HEIGHT - HEIGHT;

        public MyView(Context context) {
            super(context);
            back = BitmapFactory.decodeResource(context.getResources(), R.drawable.bt);
            plane = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        //重新开始移动
                        if (startY <= 0) {
                            startY = BACK_HEIGHT - HEIGHT;
                        } else {
                            startY -= 1;
                        }
                    }
                    invalidate();
                }
            };

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    handler.sendEmptyMessage(0x123);
                }
            }, 0, 30);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            Bitmap bitmap2 = Bitmap.createBitmap(back, 0, startY, WIDTH, HEIGHT);
            canvas.drawBitmap(bitmap2, 100, 380, null);
            canvas.drawBitmap(plane, 440, 1100, null);
        }
    }}
