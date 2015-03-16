package com.images;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ysy on 2015/3/9.
 */
public class BitmapTest extends Activity {
    private ImageView imageView;
    private Button btn_next;

    String[] images = null;
    AssetManager assets = null;
    int currentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bitmaptest);

        imageView = (ImageView) findViewById(R.id.image);
        btn_next = (Button) findViewById(R.id.btn_next);


        try {

            assets = getAssets();
            //获取assets目录下所有文件
            images = assets.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果发生数组越界
                if (currentImage >= images.length) {
                    currentImage = 0;
                }

                //找到下一个图片文件
                while (!images[currentImage].endsWith(".png")
                        && !images[currentImage].endsWith(".jpg")
                        && !images[currentImage].endsWith(".gif")) {
                    currentImage++;
                    if (currentImage >=images.length) {
                        currentImage = 0;
                    }

                }

                InputStream assetsFile = null;

                //打开指定资源对应的输入流
                try {
                    assetsFile = assets.open(images[currentImage]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();

                //如果图片还未回收，先强制回收该图片
                if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled())

                {
                    bitmapDrawable.getBitmap().recycle();
                }
                //改变ImageView现实的图片
                imageView.setImageBitmap(BitmapFactory.decodeStream(assetsFile));
                currentImage++;
            }
        });

    }
}
