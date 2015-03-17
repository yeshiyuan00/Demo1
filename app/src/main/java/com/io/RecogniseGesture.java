package com.io;

import android.app.Activity;
import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.util.ArrayList;

/**
 * Created by ysy on 2015/3/17.
 */
public class RecogniseGesture extends Activity {
    //定义手势编辑组件
    GestureOverlayView gestureView;
    //记录手机上已有的手势库
    GestureLibrary gestureLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addgesture);
        gestureView = (GestureOverlayView) findViewById(R.id.gesture);
        //读取之前创建的手势库
        gestureLibrary = GestureLibraries.fromFile("/mnt/sdcard/mygestures");
        if (gestureLibrary.load()) {
            Toast.makeText(RecogniseGesture.this, "手势文件装载成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(RecogniseGesture.this, "手势文件装载失败", Toast.LENGTH_LONG).show();
        }
        //为手势监听组件绑定手势监听器
        gestureView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

                //识别用户刚刚绘制的手势
                ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
                ArrayList<String> result = new ArrayList<String>();
                //遍历所有找到的predictions对象
                for (Prediction prediction : predictions) {
                    //只有相似度大于2.0的手势才会被输出
                    if (prediction.score > 2.0) {
                        result.add("与手势【" + prediction.name + "【相似度为：" + prediction.score);
                    }

                }
                if (result.size() > 0) {
                    ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(RecogniseGesture.this,
                            android.R.layout.simple_dropdown_item_1line, result.toArray());
                    //使用一个带List的对话框来显示所有匹配的手势
                    new AlertDialog.Builder(RecogniseGesture.this)
                            .setAdapter(adapter, null)
                            .setPositiveButton("确定", null).show();
                } else {
                    Toast.makeText(RecogniseGesture.this, "无法找到能匹配的手势",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
