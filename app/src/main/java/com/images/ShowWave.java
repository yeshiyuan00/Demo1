package com.images;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/13.
 */
public class ShowWave extends Activity {

    private SurfaceHolder holder;
    private Paint paint;
    final int WIDTH = 1280;
    final int HEIGHT = 1800;
    final int X_OFFSET = 5;
    private int cx = X_OFFSET;
    private int oldX = X_OFFSET;
    private int oldY = HEIGHT / 2;
    //实际Y轴的位置
    int centerY = HEIGHT / 2;
    Timer timer = new Timer();
    TimerTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_surfaceview);
        final SurfaceView surfaceView = (SurfaceView)
                findViewById(R.id.surface);
        //初始化SurfaceHolder对象
        holder = surfaceView.getHolder();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        Button bn_sin = (Button) findViewById(R.id.bn_sin);
        Button bn_cos = (Button) findViewById(R.id.bn_cos);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DrawBack(holder);
                cx = X_OFFSET;
                if (task != null) {
                    task.cancel();
                }

                task = new TimerTask() {
                    @Override
                    public void run() {

                        int cy = (v.getId() == R.id.bn_sin ? (centerY -
                                (int) (200 * Math.sin((cx - 5) * 2 * Math.PI / 150)))
                                : (centerY - (int) (200 * Math.cos((cx - 5) * 2 * Math.PI / 150))));
                        Canvas canvas = holder.lockCanvas(new
                                Rect(cx, cy - 2, cx + 2, cy + 2));
                        canvas.drawPoint(cx, cy, paint);
                        cx++;
                        if (cx > WIDTH) {
                            task.cancel();
                            task = null;
                        }
                        holder.unlockCanvasAndPost(canvas);
                    }
                };
                timer.schedule(task, 0, 30);
            }
        };
        bn_cos.setOnClickListener(listener);
        bn_sin.setOnClickListener(listener);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                DrawBack(holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                timer.cancel();
            }
        });
    }

    private void DrawBack(SurfaceHolder holder) {

        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(2);
        //画坐标轴
        canvas.drawLine(X_OFFSET, centerY, WIDTH, centerY, paint1);
        canvas.drawLine(X_OFFSET, 40, X_OFFSET, HEIGHT, paint1);
        holder.unlockCanvasAndPost(canvas);
        holder.lockCanvas(new Rect(0, 0, 0, 0));
        holder.unlockCanvasAndPost(canvas);
    }
}
