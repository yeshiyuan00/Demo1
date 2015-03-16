package com.images;

import android.app.Activity;
import android.os.Bundle;

import com.exp.ysy.demo.R;


/**
 * Created by ysy on 2015/3/10.
 * 还需加入手势识别
 */
public class Matrix_activity extends Activity {

    private MatrixView matrixView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_matrix);
        matrixView = (MatrixView) findViewById(R.id.matrix);

    }
}
