package com.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/11.
 */
public class WarpTest extends Activity {

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this, R.drawable.bt));
    }

    class MyView extends View {
        //定义两个常量，这两个常量指定将图片横向、纵向都划分为20格
        private final int WIDTH = 20;
        private final int HEIGHT = 20;
        //记录该图片上包含441个顶点
        private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
        //定义一个数组，保存Bitmap上21*21个点的坐标
        private  float[] verts = new float[COUNT * 2];
        //定义一个数组，保存Bitmap上21*21个点经过扭曲后的坐标
        //对图片进行扭曲关键是修改数组里面的值
        private  float[] orig = new float[COUNT * 2];

        public MyView(Context context, int drawableID) {
            super(context);
            setFocusable(true);
            //根据置顶资源加载图片
            bitmap = BitmapFactory.decodeResource(context.getResources(), drawableID);
            //获取图片的宽度、高度
            float bitmapWidth = bitmap.getWidth();
            float bitmapHeight = bitmap.getHeight();
            int index = 0;
            for (int y = 0; y <= HEIGHT; y++) {

                float fy = bitmapHeight * y / HEIGHT;

                for (int x = 0; x <= WIDTH; x++) {
                    float fx = bitmapWidth * x / WIDTH;
                    //初始化orig、verts数组，初始化后，orig、verts均保存了
                    //21*21个点的x、y坐标
                    orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                    orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                    index += 1;
                }
            }
            //设置背景颜色
            setBackgroundColor(Color.WHITE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //对bitmap按verts数组进行扭曲
            //从第一个点（点五个参数0控制）开始扭曲
            canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        }

        //工具方法，用于根据触摸事件的位置计算verts数组里面的值

        private void warp(float cx, float cy) {

            for (int i = 0; i < COUNT * 2; i += 2) {

                float dx = cx - orig[i + 0];
                float dy = cy - orig[i + 1];
                float dd = dx * dx + dy * dy;
                //计算没个坐标点与当前点（cx,cy)的距离
                float d = (float) Math.sqrt(dd);
                //计算扭曲度，距离当前点越远，扭曲度越小
                float pull = 80000 / (float) (dd * d);
                //对verts数组（保存bitmap上21*21个点扭曲后的坐标值）重新赋值
                if (pull >= 1) {
                    verts[i + 0] = cx;
                    verts[i + 1] = cy;
                } else {
                    //控制个顶点向触摸事件发生点偏移
                    verts[i + 0] = orig[i + 0] + dx * pull;
                    verts[i + 1] = orig[i + 1] + dy * pull;
                }
            }
            //通知View重绘事件
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //调用warp方法根据触摸事件的坐标点来扭曲verts数组
            warp(event.getX(), event.getY());
            return true;
        }
    }
}
