package com.images;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by ysy on 2015/3/12.
 */
public class ShapeHolder {


    private float x = 0, y = 0;
    private ShapeDrawable shape;
    private int color;
    private RadialGradient gradient;
    private float alpha = 1.0f;
    private Paint paint;

    public ShapeHolder() {

    }

    public ShapeHolder(ShapeDrawable drawable) {
        this.shape = drawable;

    }

    public float getWidth() {
        return shape.getShape().getWidth();
    }

    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    public float getHeight() {
        return shape.getShape().getHeight();
    }

    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }


    public ShapeDrawable getShape() {
        return shape;
    }

    public boolean setX(float x) {
        this.x = x;

        return true;
    }

    public boolean setY(float y) {
        this.y = y;

        return true;
    }

    public float getX() {

        return x;
    }

    public float getY() {

        return y;
    }

}
