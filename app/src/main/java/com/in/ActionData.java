package com.in;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/2/27.
 */
public class ActionData extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actiondata);

        Button btn_viewWeb = (Button) findViewById(R.id.btn_viewWeb);
        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        Button btn_call = (Button) findViewById(R.id.btn_call);

        btn_viewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String data = "http://www.crazyit.org";
                //根据指定字符串解析出Uri对象
                Uri uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_EDIT);

                String data="content://com.android.contacts/contacts/1";
                Uri uri=Uri.parse(data);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_DIAL);

                String data="tel:13800138000";
                Uri uri=Uri.parse(data);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }
}
