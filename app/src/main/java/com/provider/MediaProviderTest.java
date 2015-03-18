package com.provider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/18.
 */
public class MediaProviderTest extends Activity {
    Button bn_add, bn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mediaprovider);
        bn_add = (Button) findViewById(R.id.add);
        bn_view = (Button) findViewById(R.id.view);

        bn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
