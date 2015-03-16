package com.images;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.exp.ysy.demo.R;

import java.util.ArrayList;


/**
 * Created by ysy on 2015/3/12.
 */
public class BouncingBalls extends Activity {
    //定义小球的大小常量
    static final float BALL_SIZE = 50F;
    //定义小球从屏幕上方落到屏幕底端所需要的时间
    static final int FULL_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_linear);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
        linearLayout.addView(new MyAnimationView(this));
    }

    class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {
        private final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();

        public MyAnimationView(Context context) {
            super(context);
            //加载动画资源
            ObjectAnimator colorAnim = (ObjectAnimator)
                    AnimatorInflater.loadAnimator(BouncingBalls.this, R.animator.color_anim);
            colorAnim.setEvaluator(new ArgbEvaluator());
            //对该view本身应用属性动画
            colorAnim.setTarget(this);
            //开始属性动画
            colorAnim.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            //如果触碰事件爱你不是按下、移动时间
            if (event.getAction() != MotionEvent.ACTION_DOWN &&
                    event.getAction() != MotionEvent.ACTION_MOVE) {
                return false;
            }
            //在事件发生点画一个小球，圆代替
            ShapeHolder newball = addball(event.getX(), event.getY());
            //计算小球下落动画开始时的Y坐标
            float startY = newball.getY();
            //计算小球下落动画结束时的Y坐标（落到屏幕最下方，就是屏幕的高度减去小球的高度）
            float endY = getHeight() - BALL_SIZE;
            //获取屏幕高度
            float h = (float) getHeight();
            float eventY = event.getY();
            //计算动画的持续时间
            int duration = (int) (FULL_TIME * ((h - eventY) / h));
            //定义让小球落下的动画，让newball对象的y属性从时间发生点到屏幕的下方
            ValueAnimator fallanim = ObjectAnimator.ofFloat(newball, "y", startY, endY);
            //设置fallanim动画的持续时间
            fallanim.setDuration(duration);
            //设施fallanim的差值方式：加速插值
            fallanim.setInterpolator(new AccelerateInterpolator());
            //为fallanim动画设置监听器
            //当ValueAnimator的属性值发生改变时，将会激发该监听器的事件监听方法
            fallanim.addUpdateListener(this);
            //定义小球压扁的动画,该动画控制小球的x坐标向左移半个球
            ValueAnimator squashAnim1 = ObjectAnimator.ofFloat(newball, "x", newball.getX(),
                    newball.getX() - BALL_SIZE / 2);
            //设置动画持续时间
            squashAnim1.setDuration(duration / 4);
            //动画重复一次，重复方式,减速插值
            squashAnim1.setRepeatCount(1);
            squashAnim1.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim1.setInterpolator(new DecelerateInterpolator());
            //定义小球压扁的动画,该动画控制小球的宽度加倍
            ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(newball, "width",
                    newball.getWidth(), newball.getWidth() + BALL_SIZE);
            //设置动画持续时间
            squashAnim2.setDuration(duration / 4);
            //动画重复一次，重复方式,减速插值
            squashAnim2.setRepeatCount(1);
            squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim2.setInterpolator(new DecelerateInterpolator());
            //定义小球拉伸动画，该动画控制小球的y坐标向左移半个球
            ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(newball, "y", endY,
                    endY + BALL_SIZE / 2);
            //设置动画持续时间
            stretchAnim1.setDuration(duration / 4);
            //动画重复一次，重复方式,减速插值
            stretchAnim1.setRepeatCount(1);
            stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
            stretchAnim1.setInterpolator(new DecelerateInterpolator());
            //定义小球压扁的动画,该动画控制小球的高度减半
            ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(newball, "height",
                    newball.getHeight(), newball.getHeight() - BALL_SIZE);
            //设置动画持续时间
            stretchAnim2.setDuration(duration / 4);
            //动画重复一次，重复方式,减速插值
            stretchAnim2.setRepeatCount(1);
            stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
            stretchAnim2.setInterpolator(new DecelerateInterpolator());
            //定义小球弹起动画
            ObjectAnimator bounceBackAnim = ObjectAnimator.ofFloat(newball, "y", endY, startY);
            //持续时间
            bounceBackAnim.setDuration(duration);
            //减速插值
            bounceBackAnim.setInterpolator(new DecelerateInterpolator());
            //使用AnimatorSet按顺序播放"掉落/压扁&拉伸/弹起动画"
            AnimatorSet bouncer = new AnimatorSet();
            bouncer.play(fallanim).before(squashAnim1);
            //由于小球在屏幕下方弹起时，小球要被压扁
            //即：宽度加倍，x坐标左移半个球，高度减半，y坐标下一半个球
            //因此指定此处播放squashAnim1时
            //还播放squashAnim2、stretchAnim1、stretchAnim2
            bouncer.play(squashAnim1).with(squashAnim2);
            bouncer.play(squashAnim2).with(stretchAnim1);
            bouncer.play(stretchAnim1).with(stretchAnim2);
            //指定播放stretchAnim2动画之后，播放bounceBackAnim弹起动画
            bouncer.play(bounceBackAnim).after(stretchAnim2);
            //定义对newball对象的alpha属性执行从1到0的变化
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(newball, "alpha", 1f, 0f);
            //设置动画持续时间
            fadeAnim.setDuration(250);
            //位fadeAnim动画添加监听器
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                //当动画结束时
                @Override
                public void onAnimationEnd(Animator animation) {
                    //动画结束时将该动画关联的ShapeHolder删除
                    balls.remove(((ObjectAnimator) animation).getTarget());
                }
            });
            //再次定义一个AnimatorSet来组合动画
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(bouncer).before(fadeAnim);
            animatorSet.start();
            //为fadeanim动画设置监听器
            //当ValueAnimator的属性值发生改变时，将会激发该监听器的事件监听方法
            //fadeAnim.addUpdateListener(this);
            return true;
        }

        private ShapeHolder addball(float x, float y) {

            //创建一个圆
            OvalShape circle = new OvalShape();
            //设置该椭圆的宽、高
            circle.resize(BALL_SIZE, BALL_SIZE);
            //将圆包装成drawable对象
            ShapeDrawable drawable = new ShapeDrawable(circle);
            //创建一个ShapeHolder对象
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            //设置shapeH的x、y坐标
            shapeHolder.setX(x - BALL_SIZE / 2);
            shapeHolder.setY(y - BALL_SIZE / 2);
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            //将red、green、blue三个随机数组合成ARGB颜色
            int color = 0xff000000 + red << 16 + green << 8 + blue;
            //获取drawable上关联的画笔
            Paint paint = drawable.getPaint();
            //将red、green、blue三个随机数除以4得到的商值组合成ARGB颜色
            int drakColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            //创建圆形渐变
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, BALL_SIZE,
                    color, drakColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            balls.add(shapeHolder);
            return shapeHolder;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //遍历balls集合的每个ShapeHolder对象
            for (ShapeHolder shapeHolder : balls) {
                //保存canvas的坐标系统
                canvas.save();
                //坐标变换，将画布坐标系统平移到shapeHolder对象的x、y坐标处
                canvas.translate(shapeHolder.getX(), shapeHolder.getY());
                //将shapeHolder所持有的圆形绘制在canvas上
                shapeHolder.getShape().draw(canvas);
                //恢复canvas的坐标系统
                canvas.restore();
            }
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            this.invalidate();
        }
    }
}
