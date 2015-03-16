package com.resources;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/9.
 */
public class AlphaImageView extends ImageView {

    //图片透明度每次改变的大小
    private int AlphaDelta = 0;
    //记录图片当前透明度
    private int curAlpha = 0;
    //每隔多少毫秒透明度改变以一次
    private final int SPEED = 300;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                //每次怎家curAlpha的值
                curAlpha += AlphaDelta;
                if (curAlpha > 255) curAlpha = 255;
                //修改ImageView的透明度
                AlphaImageView.this.setAlpha(curAlpha);
            }
        }
    };

    public AlphaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaImageView);
        //获取duration参数
        int duration = typedArray.getInt(R.styleable.AlphaImageView_duration, 0);
        //计算图片透明度每次改变的大小
        AlphaDelta = 255 * SPEED / duration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setAlpha(curAlpha);
        super.onDraw(canvas);
        final Timer timer = new Timer();
        //按固定间隔发送消息，通知系统改变图片透明度
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0x123;
                if (curAlpha >= 255) {
                    timer.cancel();
                } else {
                    handler.sendMessage(message);
                }

            }
        }, 0, SPEED);
    }
}
