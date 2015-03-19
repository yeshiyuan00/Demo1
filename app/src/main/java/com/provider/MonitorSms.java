package com.provider;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/18.
 */
public class MonitorSms extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_monitorsms);
        textView = (TextView) findViewById(R.id.text);
        //为"content://sms//outbox"数据改变注册监听器
        getContentResolver().registerContentObserver(
                Uri.parse("content://sms"), true, new SmsObserver(new Handler()));
    }

    //提供自定义的ContentObserver监听类
    private final class SmsObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public SmsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            //查询发送箱中的短信（处于正在发送中的短信存储于发送箱）
            Cursor cursor = getContentResolver().query(
                    Uri.parse("content://sms/outbox"), null, null, null, null);
            //遍历查询得到的结果集，即可获得用户正在发送的短信
            while (cursor.moveToNext()) {
                StringBuilder sb = new StringBuilder();
                //获取短信的发送地址
                sb.append("address=").append(cursor.getString(cursor.getColumnIndex("address")));
                //获取短信的标题
                sb.append(";\nsubject=").append(cursor.getString(cursor.getColumnIndex("subject")));
                //获取短信的内容
                sb.append(";\nbody=").append(cursor.getString(cursor.getColumnIndex("body")));
                //获取发送短信的时间
                sb.append(";\ntime=").append(cursor.getString(cursor.getColumnIndex("date")));

                textView.setText("Has Send SMS::\n" + sb.toString());
            }
        }
    }
}
