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

import com.example.customview01.view.CustomImageView;

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


public class MyActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView lv_demo;

    private List<String> list_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        lv_demo = (ListView) findViewById(R.id.lv_demo);

        list_demo = new ArrayList<String>();

        list_demo.add(0, "AutoCompleteTextView");
        list_demo.add(1, "Bluetooth");
        list_demo.add(2, "MarqueeTextView");
        list_demo.add(3, "TabHost");
        list_demo.add(4, "WebView");
        list_demo.add(5, "CustomSeekBar");
        list_demo.add(6, "CustomView");
        list_demo.add(7, "CustomImageView");
        list_demo.add(8, "CustomProgressBar");
        list_demo.add(9, "CustomVolumControlBar");
        list_demo.add(10, "Fragment");
        list_demo.add(11, "Socket");
        list_demo.add(12, "URLTest");
        list_demo.add(13, "Intent");
        list_demo.add(14, "Resource");
        list_demo.add(15, "Image");
        list_demo.add(16, "IO");
        list_demo.add(17, "ContentProvider");


        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list_demo);

        lv_demo.setAdapter(adapter);

        lv_demo.setOnItemClickListener(this);
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
                Intent intent0 = new Intent(MyActivity.this, AutoCompleteText_demo.class);
                startActivity(intent0);
                break;
            case 1:
                Intent intent1 = new Intent(MyActivity.this, Bluetooth_demo.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(MyActivity.this, MarqueeTextview_demo.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(MyActivity.this, MyTab.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(MyActivity.this, WebView_demo.class);
                startActivity(intent4);
                break;

            case 5:
                Intent intent5 = new Intent(MyActivity.this, CustomSeekbar.class);
                startActivity(intent5);
                break;

            case 6:
                Intent intent6 = new Intent(MyActivity.this, CustomView.class);
                startActivity(intent6);
                break;
            case 7:
                Intent intent7 = new Intent(MyActivity.this, CustomImageView_Activity.class);
                startActivity(intent7);
                break;
            case 8:
                Intent intent8 = new Intent(MyActivity.this, CustomProgressbar_Activity.class);
                startActivity(intent8);
                break;

            case 9:
                Intent intent9 = new Intent(MyActivity.this, CustomVolumControlBar_Activity.class);
                startActivity(intent9);
                break;

            case 10:

                Intent intent10 = new Intent(MyActivity.this, Fragment_Activity.class);
                startActivity(intent10);
                break;

            case 11:

                Intent intent11 = new Intent(MyActivity.this, Socket_Activity.class);
                startActivity(intent11);
                break;
            case 12:

                Intent intent12 = new Intent(MyActivity.this, URLTest_Activity.class);
                startActivity(intent12);
                break;
            case 13:

                Intent intent13 = new Intent(MyActivity.this, IntentActivity.class);
                startActivity(intent13);
                break;
            case 14:

                Intent intent14 = new Intent(MyActivity.this, ResourceActivity.class);
                startActivity(intent14);
                break;
            case 15:

                Intent intent15 = new Intent(MyActivity.this, ImageActivity.class);
                startActivity(intent15);
                break;
            case 16:

                Intent intent16 = new Intent(MyActivity.this, IOActivity.class);
                startActivity(intent16);
                break;
            case 17:

                Intent intent17 = new Intent(MyActivity.this, ProviderActivity.class);
                startActivity(intent17);
                break;
        }
    }
}
