package com.exp.ysy.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.provider.ContactProviderTest;
import com.provider.MediaProviderTest;
import com.provider.MonitorSms;

import java.util.ArrayList;
import java.util.List;

import service.BindServiceTest;
import service.IntentServiceTest;
import service.StartServiceTest;


public class ServiceActivity extends Activity implements AdapterView.OnItemClickListener {

    private TextView textView;

    private ListView lv_intent;

    private List<String> list_intent;

    public final static String CRAZYIT_ACTION = "com.ysy.intent.action.CRAZYIT_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        textView = (TextView) findViewById(R.id.textview1);
        textView.setText("Service");

        lv_intent = (ListView) findViewById(R.id.lv_intent);

        list_intent = new ArrayList<String>();

        list_intent.add(0, "启动、停止Service");
        list_intent.add(1, "绑定Service");
        list_intent.add(2, "IntentService");


        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list_intent);

        lv_intent.setAdapter(adapter);

        lv_intent.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                Intent intent0 = new Intent(ServiceActivity.this, StartServiceTest.class);
                startActivity(intent0);
                break;

            case 1:

                Intent intent1 = new Intent(ServiceActivity.this, BindServiceTest.class);
                startActivity(intent1);
                break;

            case 2:

                Intent intent2 = new Intent(ServiceActivity.this, IntentServiceTest.class);
                startActivity(intent2);

                break;


        }
    }
}
