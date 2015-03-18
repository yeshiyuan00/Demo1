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

import com.io.AddGesture;
import com.io.DBTest;
import com.io.Dict;
import com.io.FileTest;
import com.io.GestureFlip;
import com.io.GestureTest;
import com.io.GestureZoom;
import com.io.RecogniseGesture;
import com.io.SDCardFileExplorer;
import com.io.SDCardTest;
import com.io.SharedPreferenceTest;
import com.io.Speech;
import com.provider.ContactProviderTest;
import com.provider.MediaProviderTest;
import com.provider.MonitorSms;

import java.util.ArrayList;
import java.util.List;


public class ProviderActivity extends Activity implements AdapterView.OnItemClickListener {

    private TextView textView;

    private ListView lv_intent;

    private List<String> list_intent;

    public final static String CRAZYIT_ACTION = "com.ysy.intent.action.CRAZYIT_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        textView = (TextView) findViewById(R.id.textview1);
        textView.setText("ContentProvider");

        lv_intent = (ListView) findViewById(R.id.lv_intent);

        list_intent = new ArrayList<String>();

        list_intent.add(0, "联系人");
        list_intent.add(1, "多媒体");
        list_intent.add(2, "短信监听");

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

                Intent intent0 = new Intent(ProviderActivity.this, ContactProviderTest.class);
                startActivity(intent0);
                break;

            case 1:

                Intent intent1 = new Intent(ProviderActivity.this, MediaProviderTest.class);
                startActivity(intent1);
                break;

            case 2:

                Intent intent2 = new Intent(ProviderActivity.this, MonitorSms.class);
                startActivity(intent2);

                break;
            case 3:

                Intent intent3 = new Intent(ProviderActivity.this, SDCardFileExplorer.class);
                startActivity(intent3);

                break;
            case 4:

                Intent intent4 = new Intent(ProviderActivity.this, DBTest.class);
                startActivity(intent4);

                break;

            case 5:

                Intent intent5 = new Intent(ProviderActivity.this, Dict.class);
                startActivity(intent5);

                break;
            case 6:

                Intent intent6 = new Intent(ProviderActivity.this, GestureTest.class);
                startActivity(intent6);

                break;
            case 7:

                Intent intent7 = new Intent(ProviderActivity.this, GestureZoom.class);
                startActivity(intent7);

                break;
            case 8:

                Intent intent8 = new Intent(ProviderActivity.this, GestureFlip.class);
                startActivity(intent8);

                break;
            case 9:

                Intent intent9 = new Intent(ProviderActivity.this, AddGesture.class);
                startActivity(intent9);

                break;
            case 10:

                Intent intent10 = new Intent(ProviderActivity.this, RecogniseGesture.class);
                startActivity(intent10);

                break;
            case 11:

                Intent intent11 = new Intent(ProviderActivity.this, Speech.class);
                startActivity(intent11);

                break;

        }
    }
}
