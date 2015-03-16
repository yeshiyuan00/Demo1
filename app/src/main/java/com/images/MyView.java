package com.images;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/9.
 */
public class MyView extends View {

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    //重写该方法，进行绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把整张布绘制成白色
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        //去锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        //绘制圆形
        canvas.drawCircle(40, 40, 30, paint);
        //绘制正方形
        canvas.drawRect(10, 80, 70, 140, paint);
        //绘制矩形
        canvas.drawRect(10, 150, 70, 190, paint);
        RectF rel = new RectF(10, 200, 70, 230);
        //绘制圆角矩形
        canvas.drawRoundRect(rel, 15, 15, paint);
        RectF rell = new RectF(10, 240, 70, 270);
        //绘制椭圆
        canvas.drawOval(rell, paint);
        //定义一个path对象，封闭成一个三角形
        Path path1 = new Path();
        path1.moveTo(10, 340);
        path1.lineTo(70, 340);
        path1.lineTo(40, 290);
        path1.close();
        //根据path进行绘制，绘制三角形
        canvas.drawPath(path1, paint);
        //定义一个path对象，封闭成一个五角形
        Path path2 = new Path();
        path2.moveTo(20, 360);
        path2.lineTo(54, 360);
        path2.lineTo(70, 382);
        path2.lineTo(40, 420);
        path2.lineTo(10, 392);
        path2.close();
        //根据path进行绘制，绘制五角形]
        canvas.drawPath(path2, paint);
        //---------设置填充风格后绘制---------
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(120, 40, 30, paint);
        //绘制正方形
        canvas.drawRect(90, 80, 150, 140, paint);
        //绘制矩形
        canvas.drawRect(90, 150, 150, 190, paint);
        RectF rel2 = new RectF(90, 200, 150, 230);
        //绘制圆角矩形
        canvas.drawRoundRect(rel2, 15, 15, paint);
        RectF rell2 = new RectF(90, 240, 150, 270);
        //绘制椭圆
        canvas.drawOval(rell2, paint);
        //定义一个path对象，封闭成一个三角形
        Path path4 = new Path();
        path4.moveTo(90, 340);
        path4.lineTo(150, 340);
        path4.lineTo(120, 290);
        path4.close();
        //根据path进行绘制，绘制三角形
        canvas.drawPath(path4, paint);
        //定义一个path对象，封闭成一个五角形
        Path path3 = new Path();
        path3.moveTo(106, 360);
        path3.lineTo(134, 360);
        path3.lineTo(150, 382);
        path3.lineTo(120, 420);
        path3.lineTo(90, 392);
        path3.close();
        //根据path进行绘制，绘制五角形]
        canvas.drawPath(path3, paint);

        //---------设置渐变器后绘制---------
        //为paint设置渐变器
        Shader mshader = new LinearGradient(0, 0, 40, 60, new int[]{Color.RED,
                Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT);
        paint.setShader(mshader);
        //设置阴影
        paint.setShadowLayer(45, 10, 10, Color.GRAY);
        //绘制圆形
        canvas.drawCircle(200, 40, 30, paint);
        //绘制正方形
        canvas.drawRect(170, 80, 230, 140, paint);
        //绘制矩形
        canvas.drawRect(170, 150, 230, 190, paint);
        RectF re3 = new RectF(170, 200, 230, 230);
        //绘制圆角矩形
        canvas.drawRoundRect(re3, 15, 15, paint);
        RectF re3l = new RectF(170, 240, 230, 270);
        //绘制椭圆
        canvas.drawOval(re3l, paint);
        //定义一个path对象，封闭成一个三角形
        Path path5 = new Path();
        path5.moveTo(170, 340);
        path5.lineTo(230, 340);
        path5.lineTo(200, 290);
        path5.close();
        //根据path进行绘制，绘制三角形
        canvas.drawPath(path5, paint);
        //定义一个path对象，封闭成一个五角形
        Path path6 = new Path();
        path6.moveTo(186, 360);
        path6.lineTo(214, 360);
        path6.lineTo(230, 382);
        path6.lineTo(200, 420);
        path6.lineTo(170, 392);
        path6.close();
        //根据path进行绘制，绘制五角形]
        canvas.drawPath(path6, paint);

        //---------设置字符大小后绘制---------
        Paint paint1 = new Paint();
        paint1.setTextSize(24);
        paint1.setShader(null);
        paint1.setColor(Color.RED);
        //绘制七个字符串
        canvas.drawText(getResources().getString(R.string.circle), 240, 50, paint1);
        canvas.drawText(getResources().getString(R.string.square), 240, 120, paint1);
        canvas.drawText(getResources().getString(R.string.rect), 240, 175, paint1);
        canvas.drawText(getResources().getString(R.string.round_rect), 230, 220, paint1);
        canvas.drawText(getResources().getString(R.string.oval), 240, 260, paint1);
        canvas.drawText(getResources().getString(R.string.triangle), 240, 325, paint1);
        canvas.drawText(getResources().getString(R.string.pentagon), 240, 390, paint1);
    }
}
