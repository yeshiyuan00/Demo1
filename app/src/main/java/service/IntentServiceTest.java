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
public class IntentServiceTest extends Activity {
    Button bn_service, bn_intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_twobutton);

        bn_service = (Button) findViewById(R.id.bn_start);
        bn_intentService = (Button) findViewById(R.id.bn_stop);
        bn_service.setText("启动Service");
        bn_intentService.setText("启动IntentService");

        bn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srartService(v);
            }
        });

        bn_intentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srartIntentService(v);
            }
        });


    }

    public void srartService(View source) {

        Intent intent = new Intent(IntentServiceTest.this, MyService.class);
        startService(intent);


    }

    public void srartIntentService(View source) {

        Intent intent = new Intent(IntentServiceTest.this, MyIntentService.class);
        startService(intent);
    }
}
