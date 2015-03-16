package com.images;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ysy on 2015/3/11.
 */
public class MyShaderView extends View {

    Paint paint;

    public MyShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        canvas.drawRect(80, 80, 400, 300, paint);
        canvas.drawCircle(240,400,80,paint);
    }
}
