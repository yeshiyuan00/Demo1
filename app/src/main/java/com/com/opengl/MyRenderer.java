package com.com.opengl;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ysy on 2015/3/26.
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    float[] triangleDate = new float[]{
            0.1f, 0.6f, 0.0f,  //上顶点
            -0.3f, 0.0f, 0.0f, //左顶点
            0.3f, 0.1f, 0.0f  //右顶点
    };

    int[] traingleColor = new int[]{
            65535, 0, 0, 0, //上顶点红色
            0, 65535, 0, 0, //左顶点绿色
            0, 0, 65535, 0 //右顶点蓝色
    };

    float[] rectleDate = new float[]{
            0.4f, 0.4f, 0.0f,    //右上顶点
            0.4f, -0.4f, 0.0f,   //右下顶点
            -0.4f, 0.4f, 0.0f    //左上顶点
            - 0.4f, -0.4f, 0.0f  //左下顶点
    };

    int[] rectColor = new int[]{
            0, 65535, 0, 0, //右上顶点绿色
            0, 0, 65535, 0,//右下顶点蓝色
            65535, 0, 0, 0, //左上顶点红色
            65535, 65535, 0, 0 //左下顶点黄色
    };

    //依然是四方形的四个顶点，只是顺序交换了一下
    float[] rectleDate2 = new float[]{
            -0.4f, 0.4f, 0.0f,   //右上顶点
            0.4f, 0.4f, 0.0f,   //右下顶点
            0.4f, -0.4f, 0.0f,   //左上顶点
            -0.4f, -0.4f, 0.0f  //左下顶点
    };

    float[] partacle = new float[]{
            0.4f, 0.4f, 0.0f,
            -.02f, 0.3f, 0.0f,
            0.5f, 0.0f, 0.0f,
            -0.4f, 0.0f, 0.0f,
            -0.1f, -0.3f, 0.0f
    };

    FloatBuffer triangleDateBuffer, rectDateBuffer, rectDateBuffer2, partacleBuffer;
    IntBuffer triangleColorBuffer, rectColorBuffer;

    //控制旋转角度
    private float rotate;

    public MyRenderer() {

        triangleDateBuffer = floatBUfferUtil(triangleDate);
        rectDateBuffer = floatBUfferUtil(rectleDate);
        rectDateBuffer2 = floatBUfferUtil(rectleDate2);
        partacleBuffer = floatBUfferUtil(partacle);

        triangleColorBuffer = intBUfferUtil(traingleColor);
        rectColorBuffer = intBUfferUtil(rectColor);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        //设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        //设置阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
        //将当期那矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵
        gl.glLoadIdentity();
        //计算透视窗的宽度、高度比
        float ratio = (float) width / height;
        //调用此方法设置透视窗的空间的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

    }

    //绘制图形的方法
    @Override
    public void onDrawFrame(GL10 gl) {

        //清楚屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启用顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //设置当前矩阵堆栈为模型堆栈
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        //-------绘制第一个图形--------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.32f, 0.35f, -1f);
        //设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDateBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
        //根据定点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        //-------绘制第二个图形--------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(0.6f, 0.8f, -1.5f);
        gl.glRotatef(rotate, 0f, 0f, 0.1f);
        //设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDateBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
        //根据定点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        //-------绘制第三个图形--------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.4f, -0.5f, -1.5f);
        gl.glRotatef(rotate, 0f, 0.2f, 0f);
        //设置顶点的位置数据(仍然使用之前的顶点颜色)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDateBuffer2);

        //根据定点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        //-------绘制第四个图形--------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(0.4f, -0.5f, -1.5f);
        //设置使用纯色填充
        gl.glColor4f(1.0f, 0.2f, 0.2f, 0.0f);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        //设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, partacleBuffer);

        //根据定点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 5);

        //会直接输
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        rotate += 1;
    }

    //定义一个工具方法，将int[]数组转换为OpenGL ES所需要的IntBuffer
    private IntBuffer intBUfferUtil(int[] arr) {
        IntBuffer mbuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeorder
        qbb.order(ByteOrder.nativeOrder());
        mbuffer = qbb.asIntBuffer();
        mbuffer.put(arr);
        mbuffer.position(0);
        return mbuffer;
    }

    //定义一个工具方法，将float[]数组转换为OpenGL ES所需要的FloatBuffer
    private FloatBuffer floatBUfferUtil(float[] arr) {
        FloatBuffer mbuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeorder
        qbb.order(ByteOrder.nativeOrder());
        mbuffer = qbb.asFloatBuffer();
        mbuffer.put(arr);
        mbuffer.position(0);
        return mbuffer;
    }
}
