package com.com.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Created by ysy on 2015/3/26.
 */
public class Simple3D extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建一个GLSurfaceView，用于显示OPenGL绘制的图形
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        //创建GLSurfaceView的内容绘制器
        MyRenderer3D myRenderer = new MyRenderer3D();
        //为GLSurfaceView设置绘制器
        glSurfaceView.setRenderer(myRenderer);
        setContentView(glSurfaceView);
    }
}
