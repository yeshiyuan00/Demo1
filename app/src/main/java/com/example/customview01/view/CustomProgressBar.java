package com.example.customview01.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.exp.ysy.demo.R;

import java.security.PrivateKey;

/**
 * Created by ysy on 2015/1/23.
 */
public class CustomProgressBar extends View {
    private int mWidth;
    private int mHeight;

    /**
     * 第一圈颜色
     */
    private int mFirstColor;

    /**
     * 第二圈颜色
     */
    private int mSecondColor;

    /**
     * 画的宽度
     */
    private int mCircleWidth;

    /**
     * 画笔
     */
    private Paint mpaint;

    /**
     * 当前进度
     */
    private int mprogress;

    /**
     * 速度
     */
    private int mSpeed;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;


    public CustomProgressBar(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomProgressBar, defStyle, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {

            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:

                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_PX, 20,
                                    getResources().getDisplayMetrics()));

                    break;
                case R.styleable.CustomProgressBar_speed:

                    mSpeed = a.getInt(attr, 20);
                    break;

            }
        }

        a.recycle();
        mpaint = new Paint();
        // 绘图线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mprogress++;
                    if (mprogress == 360) {
                        mprogress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else isNext = false;
                    }
                    postInvalidate();

                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;  //圆心坐标
        int radius = centre - mCircleWidth / 2;  //半径
        mpaint.setStrokeWidth(mCircleWidth); //设置圆环宽度
        mpaint.setAntiAlias(true); // 消除锯齿
        mpaint.setStyle(Paint.Style.STROKE); // 设置空心

        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius,
                centre + radius, centre + radius);

        if (!isNext) {
            // 第一颜色的圈完整，第二颜色跑
            mpaint.setColor(mFirstColor);
            canvas.drawCircle(centre, centre, radius, mpaint);

            mpaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mprogress, false, mpaint);

        } else {

            mpaint.setColor(mSecondColor);
            canvas.drawCircle(centre, centre, radius, mpaint);

            mpaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mprogress, false, mpaint);
        }
    }


}

