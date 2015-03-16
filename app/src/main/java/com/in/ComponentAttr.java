package com.in;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.widget.EditText;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/2/27.
 */
public class ComponentAttr extends Activity {

    private EditText edt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_component);

        edt_show = (EditText) findViewById(R.id.edt_show);

        ComponentName componentName = getIntent().getComponent();
        //显式该Component对象的包名、类名
        edt_show.setText("组件包名为：" + componentName.getPackageName() +
                "\n组件类名为：" + componentName.getClassName());
    }
}
