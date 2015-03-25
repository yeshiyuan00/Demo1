package com.desktop;

import android.app.Activity;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exp.ysy.demo.R;

import java.io.IOException;

/**
 * Created by ysy on 2015/3/25.
 */
public class WallPaperTest extends Activity {
    Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twobutton);
        change = (Button) findViewById(R.id.bn_start);
        change.setText("更换壁纸");
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallPaperTest.this);
                try {
                    wallpaperManager.setResource(R.drawable.bt);
                } catch (IOException e) {
                    System.out.println("更换壁纸失败" + e);
                    e.printStackTrace();
                }
            }
        });
    }
}
