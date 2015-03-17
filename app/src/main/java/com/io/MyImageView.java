package com.io;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by ysy on 2015/3/17.
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            Log.i(MyImageView.class.getSimpleName(), "Catch Canvas: trying to use a recycled bitmap");
        }
    }
}
