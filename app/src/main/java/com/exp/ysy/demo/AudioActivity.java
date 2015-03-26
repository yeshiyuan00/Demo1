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

import com.audio.MediaPlayerTest;
import com.desktop.WallPaperTest;
import com.net.GetPostMain;
import com.net.HttpClientTets;
import com.net.JsCallAndroid;
import com.net.MiniBrowser;
import com.net.MultiThreadClient;
import com.net.MultiThreadDown;
import com.net.MyWeather;
import com.net.URLTest;
import com.net.ViewHtml;

import java.util.ArrayList;
import java.util.List;

import service.AlarmChange_Wallpaper;
import service.AlarmTest;
import service.BroadcastMain;
import service.MusicBox;
import service.SortedBroadcast;


public class AudioActivity extends Activity implements AdapterView.OnItemClickListener {

    private TextView textView;

    private ListView lv_intent;

    private List<String> list_intent;

    public final static String CRAZYIT_ACTION = "com.ysy.intent.action.CRAZYIT_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        textView = (TextView) findViewById(R.id.textview1);
        textView.setText("音频管理");

        lv_intent = (ListView) findViewById(R.id.lv_intent);

        list_intent = new ArrayList<String>();

        list_intent.add(0, "音乐特效");


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

                Intent intent0 = new Intent(AudioActivity.this, MediaPlayerTest.class);
                startActivity(intent0);
                break;

            case 1:

                Intent intent1 = new Intent(AudioActivity.this, MultiThreadClient.class);
                startActivity(intent1);
                break;

            case 2:

                Intent intent2 = new Intent(AudioActivity.this, URLTest.class);
                startActivity(intent2);

                break;
            case 3:

                Intent intent3 = new Intent(AudioActivity.this, GetPostMain.class);
                startActivity(intent3);

                break;
            case 4:

                Intent intent4 = new Intent(AudioActivity.this, MultiThreadDown.class);
                startActivity(intent4);

                break;
            case 5:

                Intent intent5 = new Intent(AudioActivity.this, HttpClientTets.class);
                startActivity(intent5);

                break;

            case 6:

                Intent intent6 = new Intent(AudioActivity.this, MiniBrowser.class);
                startActivity(intent6);

                break;
            case 7:

                Intent intent7 = new Intent(AudioActivity.this, ViewHtml.class);
                startActivity(intent7);

                break;

            case 8:

                Intent intent8 = new Intent(AudioActivity.this, JsCallAndroid.class);
                startActivity(intent8);

                break;
            case 9:

                Intent intent9 = new Intent(AudioActivity.this, MyWeather.class);
                startActivity(intent9);

                break;
            case 10:

                Intent intent10 = new Intent(AudioActivity.this, AlarmTest.class);
                startActivity(intent10);

                break;
            case 11:

                Intent intent11 = new Intent(AudioActivity.this, AlarmChange_Wallpaper.class);
                startActivity(intent11);

                break;
            case 12:

                Intent intent12 = new Intent(AudioActivity.this, BroadcastMain.class);
                startActivity(intent12);

                break;
            case 13:

                Intent intent13 = new Intent(AudioActivity.this, SortedBroadcast.class);
                startActivity(intent13);

                break;
            case 14:

                Intent intent14 = new Intent(AudioActivity.this, MusicBox.class);
                startActivity(intent14);

                break;
            case 15:

                Intent intent15 = new Intent(AudioActivity.this, MusicBox.class);
                startActivity(intent15);

                break;
        }
    }
}
