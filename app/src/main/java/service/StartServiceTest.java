package service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/19.
 */
public class StartServiceTest extends Activity {

    Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twobutton);
        start = (Button) findViewById(R.id.bn_start);
        stop = (Button) findViewById(R.id.bn_stop);

        //创建启动Service的Intent
        final Intent intent = new Intent();
        //为Intent设置Action属性
        intent.setAction("com.exp.ysy.demo.service.FIRST_SERVICE");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动指定的Service
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止指定的Service
                stopService(intent);
            }
        });
    }
}
