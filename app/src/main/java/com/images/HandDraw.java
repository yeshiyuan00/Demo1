package com.images;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/10.
 */
public class HandDraw extends Activity {

    EmbossMaskFilter emboss;
    BlurMaskFilter blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_handdraw);

        emboss = new EmbossMaskFilter(new float[]{1.5f, 1.5f, 1.5f}, 0.6f, 6, 4.2f);
        blur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //菜单被点击后的回调方法

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        DrawView dv = (DrawView) findViewById(R.id.drawview);
        //判断单击的是哪个菜单项，并作出响应
        switch (item.getItemId()) {

            case R.id.red:

                dv.paint.setColor(Color.RED);
                item.setChecked(true);
                break;
            case R.id.green:
                dv.paint.setColor(Color.GREEN);
                item.setChecked(true);
                break;
            case R.id.blue:
                dv.paint.setColor(Color.BLUE);
                item.setChecked(true);
                break;
            case R.id.white:
                dv.paint.setColor(Color.WHITE);
                item.setChecked(true);
                break;
            case R.id.width_1:
                dv.paint.setStrokeWidth(1);
                break;
            case R.id.width_3:
                dv.paint.setStrokeWidth(3);
                break;
            case R.id.width_5:
                dv.paint.setStrokeWidth(5);
                break;
            case R.id.blur:
                dv.paint.setMaskFilter(blur);
                break;
            case R.id.emboss:
                dv.paint.setMaskFilter(emboss);
                break;
        }

        return true;
    }
}
