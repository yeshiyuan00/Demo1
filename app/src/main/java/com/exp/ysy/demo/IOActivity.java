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

import com.images.BitmapTest;
import com.images.Blast;
import com.images.BouncingBalls;
import com.images.Butterfly;
import com.images.FatPo;
import com.images.HandDraw;
import com.images.ListviewTween;
import com.images.Matrix_activity;
import com.images.MoveBacK;
import com.images.MyView_Activity;
import com.images.PathTest;
import com.images.PathText;
import com.images.PinBall;
import com.images.ShaderView;
import com.images.ShowWave;
import com.images.SurfaceViewTest;
import com.images.TweenAnim;
import com.images.WarpTest;
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

import java.util.ArrayList;
import java.util.List;


public class IOActivity extends Activity implements AdapterView.OnItemClickListener {

    private TextView textView;

    private ListView lv_intent;

    private List<String> list_intent;

    public final static String CRAZYIT_ACTION = "com.ysy.intent.action.CRAZYIT_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        textView = (TextView) findViewById(R.id.textview1);
        textView.setText("数据存储与IO");

        lv_intent = (ListView) findViewById(R.id.lv_intent);

        list_intent = new ArrayList<String>();

        list_intent.add(0, "SharedPreferencesTest");
        list_intent.add(1, "读写程序内部文件");
        list_intent.add(2, "读写SD卡文件");
        list_intent.add(3, "SD卡资源管理器");
        list_intent.add(4, "SQLite插入测试");
        list_intent.add(5, "SQLiteOpenHelper测试");
        list_intent.add(6, "手势检测");
        list_intent.add(7, "手势缩放图片");
        list_intent.add(8, "手势翻页");
        list_intent.add(9, "增加手势");
        list_intent.add(10, "识别手势");
        list_intent.add(11, "自动朗读");


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
                Intent intent0 = new Intent(IOActivity.this, SharedPreferenceTest.class);
                startActivity(intent0);
                break;

            case 1:

                Intent intent1 = new Intent(IOActivity.this, FileTest.class);
                startActivity(intent1);
                break;

            case 2:

                Intent intent2 = new Intent(IOActivity.this, SDCardTest.class);
                startActivity(intent2);

                break;
            case 3:

                Intent intent3 = new Intent(IOActivity.this, SDCardFileExplorer.class);
                startActivity(intent3);

                break;
            case 4:

                Intent intent4 = new Intent(IOActivity.this, DBTest.class);
                startActivity(intent4);

                break;

            case 5:

                Intent intent5 = new Intent(IOActivity.this, Dict.class);
                startActivity(intent5);

                break;
            case 6:

                Intent intent6 = new Intent(IOActivity.this, GestureTest.class);
                startActivity(intent6);

                break;
            case 7:

                Intent intent7 = new Intent(IOActivity.this, GestureZoom.class);
                startActivity(intent7);

                break;
            case 8:

                Intent intent8 = new Intent(IOActivity.this, GestureFlip.class);
                startActivity(intent8);

                break;
            case 9:

                Intent intent9 = new Intent(IOActivity.this, AddGesture.class);
                startActivity(intent9);

                break;
            case 10:

                Intent intent10 = new Intent(IOActivity.this, RecogniseGesture.class);
                startActivity(intent10);

                break;
            case 11:

                Intent intent11 = new Intent(IOActivity.this, Speech.class);
                startActivity(intent11);

                break;

        }
    }
}
