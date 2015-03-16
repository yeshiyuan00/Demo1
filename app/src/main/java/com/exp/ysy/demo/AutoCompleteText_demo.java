package com.exp.ysy.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

/**
 * Created by jha on 2014/11/24.
 */
public class AutoCompleteText_demo extends Activity {

    private AutoCompleteTextView act_demo;
    private MultiAutoCompleteTextView mact_demo;

    private String[] items = {"guangzhou1", "guangzhou2", "guangzhou3", "guangzhou4",
            "beijing1" , "beijing2", "beijing3", "beijing4", "nanxiong1", "nanxiong2",
            "nanxiong3" ,"nanjin1","nanjin2","nanjin3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_audiocompletetext);

        act_demo = (AutoCompleteTextView) findViewById(R.id.act_demo);
        mact_demo=(MultiAutoCompleteTextView)findViewById(R.id.mact_demo);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, items);

        act_demo.setAdapter(adapter);
        mact_demo.setAdapter(adapter);

        mact_demo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());//设置分隔符
    }
}






