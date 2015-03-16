package com.resources;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/6.
 */
public class AnimatorTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animatortest);

        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        //添加MyAnimationView组件
        container.addView(new MyAnimationView(this));
    }

    public class MyAnimationView extends View {


        public MyAnimationView(Context context) {
            super(context);

            ObjectAnimator colorAnim = (ObjectAnimator)
                    AnimatorInflater.loadAnimator(AnimatorTest.this, R.animator.color_anim);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setTarget(this);
            colorAnim.start();
        }
    }
}
