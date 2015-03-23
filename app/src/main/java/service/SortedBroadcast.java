package service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/23.
 */
public class SortedBroadcast extends Activity {
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twobutton);
        send = (Button) findViewById(R.id.bn_start);

        send.setText("发送广播");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.exp.ysy.demo.action.YSY_BROADCAST");
                intent.putExtra("msg", "简单的消息");
                //发送广播
                sendOrderedBroadcast(intent,null);
            }
        });
    }
}
