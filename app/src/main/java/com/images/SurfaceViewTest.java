package com.images;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

import java.util.EventListenerProxy;

/**
 * Created by ysy on 2015/3/13.
 */
public class SurfaceViewTest extends Activity {
    //SurfaceHolder负责维SurfaceView上绘制的内容
    private SurfaceHolder holder;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_surfaceview);
        Button bn_sin = (Button) findViewById(R.id.bn_sin);
        Button bn_cos = (Button) findViewById(R.id.bn_cos);
        bn_cos.setVisibility(View.INVISIBLE);
        bn_sin.setVisibility(View.INVISIBLE);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
        paint = new Paint();
        //初始化SurfaceHolder对象
        holder = surfaceView.getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //锁定整个SurfaceView
                Canvas canvas = holder.lockCanvas();
                //绘制背景
                Bitmap back = BitmapFactory.
                        decodeResource(SurfaceViewTest.this.getResources(), R.drawable.dengta);
                canvas.drawBitmap(back, 0, 0, null);
                //绘制完成，释放画布，提交修改
                holder.unlockCanvasAndPost(canvas);
                //重新锁一次，“持久化”上次绘制的内容
                holder.lockCanvas(new Rect(0, 0, 0, 0));
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        //为SurfaceView的触摸事件绑定监听器
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //只处理按下事件
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int cx = (int) event.getX();
                    int cy = (int) event.getY();
                    //重新锁一次，“持久化”上次绘制的内容
                    Canvas canvas1 = holder.lockCanvas(new Rect(0, 0, 0, 0));
                    holder.unlockCanvasAndPost(canvas1);
                    //锁定SurfaceView的部分区局，只更新局部内容
                    Canvas canvas = holder.lockCanvas(new
                            Rect(cx - 50, cy - 50, cx + 50, cy + 50));
                    //保存canvas的当前状态
                    canvas.save();
                    //旋转画布
                    canvas.rotate(30, cx, cy);
                    paint.setColor(Color.RED);
                    //绘制红色方块
                    canvas.drawRect(cx - 40, cy - 40, cx, cy, paint);
                    //恢复canvas之前保存的状态
                    canvas.restore();
                    paint.setColor(Color.GREEN);
                    //绘制绿色方块
                    canvas.drawRect(cx, cy, cx + 40, cy + 40, paint);
                    //绘制完成，释放画布，提交修改
                    holder.unlockCanvasAndPost(canvas);
                }
                return false;
            }
        });
    }
}
