package com.resources;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/2/28.
 */
public class AnimTest extends Activity {

    private ImageView imageView;
    private Button bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animation);

        imageView = (ImageView) findViewById(R.id.clip_image);
        bn = (Button) findViewById(R.id.bn);

        //加载动画资源
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_anim);

        //设置动画结束后保留结束状态
        anim.setFillAfter(true);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(anim);
            }
        });


    }
}
