package com.images;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by ysy on 2015/3/12.
 * 自定义补间动画
 */
public class MyAnimation extends Animation {
    private float centerX;
    private float centerY;
    //定义动画持续时间
    private int duration;
    private Camera camera = new Camera();

    public MyAnimation(float x, float y, int duration) {
        this.centerX = x;
        this.centerY = y;
        this.duration = duration;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置动画持续时间
        setDuration(duration);
        //设置动画结束后保留
        setFillAfter(true);
        //线性变换
        setInterpolator(new LinearInterpolator());
        setRepeatCount(INFINITE);
        setRepeatMode(Animation.REVERSE);
    }

    /**
     * 改方法的interpolatedTime代表抽象的动画持续时间，不管动画的实际持续时间多长
     * interpolatedTime总是从0（动画开始）~1（动画结束）
     * Transformation参数代表了对目标数组所做的改变
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        camera.save();
        //根据interpolatedTime时间来控制x、y、z方向上的偏移
        camera.translate(100.0f - 100.0f * interpolatedTime, 150.0f * interpolatedTime - 150,
                80.0f - 80.0f * interpolatedTime);
        //设置根据interpolatedTime时间在Y轴上旋转不同的角度
        camera.rotateY(360 * interpolatedTime);
        //设置根据interpolatedTime时间在X轴上旋转不同的角度
        camera.rotateZ(360 * interpolatedTime);
        //获取Transformation参数的matrix对象
        Matrix matrix = t.getMatrix();
        camera.getMatrix(matrix);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();
    }
}
