package com.images;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/11.
 */
public class FatPo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_frame);

        Button play = (Button) findViewById(R.id.btn_start);
        Button stop = (Button) findViewById(R.id.btn_stop);
        ImageView imageView = (ImageView) findViewById(R.id.img_panda);
        //获取AnimationDrawable对象
        final AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.stop();
            }
        });
    }
}
