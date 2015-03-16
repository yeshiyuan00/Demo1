package com.images;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ListView;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/12.
 */
public class ListviewTween extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview);
        ListView listView = (ListView) findViewById(R.id.list);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕的宽和高
        display.getMetrics(metrics);
        //设置listview组件应用动画
        listView.setAnimation(new MyAnimation(metrics.xdpi / 2, metrics.ydpi / 2, 3500));
    }
}
