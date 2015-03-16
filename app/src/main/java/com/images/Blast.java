package com.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.lang.reflect.Field;

/**
 * Created by ysy on 2015/3/11.
 */
public class Blast extends Activity {

    private MyView myView;
    private AnimationDrawable anim;
    private MediaPlayer boom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用FrameLayout布局管理器，他允许组件自己控制位置
        FrameLayout frame = new FrameLayout(this);
        setContentView(frame);

        //加载音效
        boom = MediaPlayer.create(this, R.raw.boom);
        myView = new MyView(this);
        //设置myview用来显示Blast动画
        myView.setBackgroundResource(R.drawable.fat_po);
        //设置myView默认为隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim = (AnimationDrawable) myView.getBackground();
        frame.addView(myView);
        frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    anim.stop();
                    float x = event.getX();
                    float y = event.getY();
                    //控制myview的显示位置
                    myView.setLocation((int) y - 40, (int) x - 20);
                    myView.setVisibility(View.VISIBLE);
                    anim.start();
                    boom.start();
                }
                return false;
            }
        });
    }

    //定义一个View，该View用于播放爆炸效果
    class MyView extends ImageView {

        public MyView(Context context) {
            super(context);
        }

        public void setLocation(int top, int left) {

            this.setFrame(left, top, left + 40, top + 40);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            try {
                Field field = AnimationDrawable.class.getDeclaredField("mCurFrame");
                field.setAccessible(true);
                //获取动画的当前帧
                int CurFrame = field.getInt(anim);
                //如果到了最后一帧，view隐藏
                if (CurFrame == anim.getNumberOfFrames() - 1) {
                    setVisibility(INVISIBLE);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            super.onDraw(canvas);
        }
    }
}
