package com.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ysy on 2015/3/10.
 */
public class PathTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        float phase;
        PathEffect[] effect = new PathEffect[7];
        int[] colors;
        private Paint paint;
        Path path;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            //创建并初始化Path
            path = new Path();
            path.moveTo(0, 0);
            for (int i = 0; i < 15; i++) {
                //生成15个点，并随机生成他们的Y坐标，并将他们连成一条path
                path.lineTo(i * 20, (float) Math.random() * 60);

            }
            //初始化七个颜色
            colors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN,
                    Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //背景填充为白色
            canvas.drawColor(Color.WHITE);
            //-------小面开始初始化七种路径效果--------
            //不使用路径效果
            effect[0] = null;
            //使用CornerPathEffect效果
            effect[1] = new CornerPathEffect(10);
            //初始化DiscretePathEffect
            effect[2] = new DiscretePathEffect(3.0f, 5.0f);
            //初始化DashPathEffect
            effect[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, phase);
            //初始化PathDashPathEffect
            Path path1 = new Path();
            path1.addRect(0, 0, 8, 8, Path.Direction.CCW);
            effect[4] = new PathDashPathEffect(path1, 12, phase,
                    PathDashPathEffect.Style.ROTATE);
            //初始化ComposePathEffect
            effect[5] = new ComposePathEffect(effect[2], effect[4]);
            effect[6] = new SumPathEffect(effect[4], effect[3]);
            //将画布移动到（8,8）开始绘制
            canvas.translate(8, 8);
            //依次使用七种不同的效果、七种不同的颜色绘制路径
            for (int i = 0; i < effect.length; i++) {
                paint.setPathEffect(effect[i]);
                paint.setColor(colors[i]);
                canvas.drawPath(path, paint);
                canvas.translate(0,60);
            }
            //改变phase值，形成动画效果
            phase += 1;
            invalidate();
        }
    }
}
