package com.exp.ysy.demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.in.ActionData;
import com.in.ComponentAttr;
import com.in.SysAction;

import java.util.ArrayList;
import java.util.List;

import subclass.CustomImageView_Activity;
import subclass.CustomProgressbar_Activity;
import subclass.CustomSeekbar;
import subclass.CustomView;
import subclass.CustomVolumControlBar_Activity;
import subclass.Fragment_Activity;
import subclass.MyTab;
import subclass.Socket_Activity;
import subclass.URLTest_Activity;
import subclass.WebView_demo;


public class IntentActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView lv_intent;

    private List<String> list_intent;

    public final static String CRAZYIT_ACTION = "com.ysy.intent.action.CRAZYIT_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        lv_intent = (ListView) findViewById(R.id.lv_intent);

        list_intent = new ArrayList<String>();

        list_intent.add(0, "Component显式");
        list_intent.add(1, "Action启动");
        list_intent.add(2, "查看联系人");
        list_intent.add(3, "Action、Data启动");


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
                /**
                 * component显式启动一个Activity
                 * */
                ComponentName componentName = new ComponentName(IntentActivity.this,
                        ComponentAttr.class);
                Intent intent0 = new Intent();
                intent0.setComponent(componentName);
                startActivity(intent0);
                break;

            case 1:
                Intent intent1 = new Intent();

                intent1.setAction(IntentActivity.CRAZYIT_ACTION);
                startActivity(intent1);

                break;
            case 2:
                Intent intent2 = new Intent(IntentActivity.this, SysAction.class);
                startActivity(intent2);

                break;

            case 3:

                Intent intent3 = new Intent(IntentActivity.this, ActionData.class);
                startActivity(intent3);
                break;

        }
    }
}
