package service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/20.
 */
public class SendSms extends Activity {
    EditText number, content;
    Button send;
    SmsManager sManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sendsms);
        number = (EditText) findViewById(R.id.number);
        content = (EditText) findViewById(R.id.content);
        send = (Button) findViewById(R.id.send);

        //获取SmsManager
        sManager = SmsManager.getDefault();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个pendingIntent对象
                PendingIntent pi = PendingIntent.getActivity(SendSms.this, 0, new Intent(), 0);
                //发送短信
                sManager.sendTextMessage(number.getText().toString(),
                        null, content.getText().toString(), pi, null);
                Toast.makeText(SendSms.this, "短信发送完成", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
