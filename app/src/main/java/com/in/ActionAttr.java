package com.in;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.exp.ysy.demo.R;

import java.util.Set;

/**
 * Created by ysy on 2015/2/27.
 */
public class ActionAttr extends Activity {

    private EditText edt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_component);

        edt_show = (EditText) findViewById(R.id.edt_show);

        String action = getIntent().getAction();

        edt_show.setText("Action为：" + action);
    }
}
