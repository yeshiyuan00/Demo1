package com.images;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/11.
 */
public class TweenAnim extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tweenanim);

        final ImageView image = (ImageView) findViewById(R.id.image);
        final Button bn = (Button) findViewById(R.id.bn);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_7_5);
        final Animation reverse = AnimationUtils.loadAnimation(this, R.anim.anim_reverse);
        anim.setFillAfter(true);
        reverse.setFillAfter(true);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    image.startAnimation(reverse);
                }
            }
        };
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.startAnimation(anim);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0x123);
                    }
                }, 3500);
            }
        });
    }
}
