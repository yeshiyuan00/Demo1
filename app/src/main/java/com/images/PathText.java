package com.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ysy on 2015/3/10.
 */
public class PathText extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
    }

    class TextView extends View {

        final String DRW_STR = "测试PathText的文字";
        Path[] path = new Path[3];
        Paint paint;

        public TextView(Context context) {
            super(context);
            path[0] = new Path();
            path[0].moveTo(0, 0);
            for (int i = 0; i < 7; i++) {
                path[0].lineTo(i * 30, (float) Math.random() * 30);
            }

            path[1] = new Path();
            RectF rectF = new RectF(0, 0, 200, 120);
            path[1].addOval(rectF, Path.Direction.CCW);
            path[2] = new Path();
            path[2].addArc(rectF, 60, 180);
            //初始化画笔
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(1);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.translate(40, 40);
            //设置从右边开始绘制（右对齐）
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(20);
            //绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path[0], paint);
            //沿着路径绘制一段文本
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRW_STR, path[0], -8, 20, paint);

            canvas.translate(0, 60);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path[1], paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRW_STR, path[1], -20, 20, paint);

            canvas.translate(0, 120);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path[2], paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRW_STR, path[2], -10, 20, paint);
        }
    }
}
