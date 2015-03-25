package com.net;


import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/25.
 */
public class MyObject {

    Context mContext;

    MyObject(Context context) {

        mContext = context;
    }

    //该方法会暴露给JavaScript脚本调用
    public void showToast(String name) {
        Toast.makeText(mContext, name + ",您好！", Toast.LENGTH_LONG).show();
    }

    //该方法会暴露给JavaScript脚本调用
    public void showList() {
        //显示一个普通的列表对话框
        new AlertDialog.Builder(mContext)
                .setTitle("图书列表")
                .setIcon(R.drawable.bt)
                .setItems(new String[]{"疯狂Java讲义", "十万个为什么", "格林童话"}, null)
                .setPositiveButton("确定", null)
                .create().show();
    }
}