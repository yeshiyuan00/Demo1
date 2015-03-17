package com.io;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/17.
 */
public class GestureFlip extends Activity implements GestureDetector.OnGestureListener {

    //ViewFlipper实例
    ViewFlipper viewFlipper;
    //手势检测器实例
    GestureDetector detector;
    //定义一个动画数组，用于为ViewFlipper指定切换动画效果
    Animation[] animations = new Animation[4];
    //定义动作手势两点之间的最小距离
    final int FILP_DISTANCE = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewflipper);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        detector = new GestureDetector(this, this);
        //为viewflipper添加五个ImageView组件
        viewFlipper.addView(addImageView(R.drawable.dengta));
        viewFlipper.addView(addImageView(R.drawable.tulips));
        viewFlipper.addView(addImageView(R.drawable.bt));
        viewFlipper.addView(addImageView(R.drawable.lmj));
        viewFlipper.addView(addImageView(R.drawable.panda1));
        //初始化Animations数组
        animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
        animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
        animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
        animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);
    }

    //定义添加ImageView的工具方法
    private View addImageView(int resId) {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //如果第一个触点事件的X坐标大于第二个触点事件的X坐标超过FLIP_DISTANCE
        //也就是手势从右向左滑
        if (e1.getX() - e2.getX() > FILP_DISTANCE) {
            //为Filpper设置动画切换效果
            viewFlipper.setInAnimation(animations[0]);
            viewFlipper.setOutAnimation(animations[1]);
            viewFlipper.showPrevious();
            return true;
        }
        //如果第一个触点事件的X坐标大于第二个触点事件的X坐标超过FLIP_DISTANCE
        //也就是手势从左向右滑
        if (e2.getX() - e1.getX() > FILP_DISTANCE) {
            //为Filpper设置动画切换效果
            viewFlipper.setInAnimation(animations[2]);
            viewFlipper.setOutAnimation(animations[3]);
            viewFlipper.showNext();
            return true;
        }
        return true;
    }
}
