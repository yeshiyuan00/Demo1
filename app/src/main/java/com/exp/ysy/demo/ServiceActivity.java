package com.exp.ysy.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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

import service.AlarmChange_Wallpaper;
import service.AlarmTest;
import service.AudioTest;
import service.BindServiceTest;
import service.BlockMain;
import service.BroadcastMain;
import service.GroupSend;
import service.IntentServiceTest;
import service.MonitorPhone;
import service.MusicBox;
import service.SendSms;
import service.SortedBroadcast;
import service.StartServiceTest;
import service.TelephoneStatus;
import service.VibratorTest;


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
        list_intent.add(3, "获取网络和SIM卡信息");
        list_intent.add(4, "监听手机来电");
        list_intent.add(5, "来电拦截");
        list_intent.add(6, "发送短信");
        list_intent.add(7, "短信群发");
        list_intent.add(8, "音频管理");
        list_intent.add(9, "手机震动");
        list_intent.add(10, "闹钟");
        list_intent.add(11, "更换壁纸");
        list_intent.add(12, "发送广播");
        list_intent.add(13, "发送有序广播");
        list_intent.add(14, "service音乐");


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
            case 3:

                Intent intent3 = new Intent(ServiceActivity.this, TelephoneStatus.class);
                startActivity(intent3);

                break;
            case 4:

                Intent intent4 = new Intent(ServiceActivity.this, MonitorPhone.class);
                startActivity(intent4);

                break;
            case 5:

                Intent intent5 = new Intent(ServiceActivity.this, BlockMain.class);
                startActivity(intent5);

                break;

            case 6:

                Intent intent6 = new Intent(ServiceActivity.this, SendSms.class);
                startActivity(intent6);

                break;
            case 7:

                Intent intent7 = new Intent(ServiceActivity.this, GroupSend.class);
                startActivity(intent7);

                break;

            case 8:

                Intent intent8 = new Intent(ServiceActivity.this, AudioTest.class);
                startActivity(intent8);

                break;
            case 9:

                Intent intent9 = new Intent(ServiceActivity.this, VibratorTest.class);
                startActivity(intent9);

                break;
            case 10:

                Intent intent10 = new Intent(ServiceActivity.this, AlarmTest.class);
                startActivity(intent10);

                break;
            case 11:

                Intent intent11 = new Intent(ServiceActivity.this, AlarmChange_Wallpaper.class);
                startActivity(intent11);

                break;
            case 12:

                Intent intent12 = new Intent(ServiceActivity.this, BroadcastMain.class);
                startActivity(intent12);

                break;
            case 13:

                Intent intent13 = new Intent(ServiceActivity.this, SortedBroadcast.class);
                startActivity(intent13);

                break;
            case 14:

                Intent intent14 = new Intent(ServiceActivity.this, MusicBox.class);
                startActivity(intent14);

                break;
        }
    }
}
