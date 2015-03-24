package com.net;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLTest extends Activity {
    ImageView show;
    //代表从网络上下载得到的图片
    Bitmap bitmap;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                show.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urltest);

        show = (ImageView) findViewById(R.id.show);

        new Thread() {

            @Override
            public void run() {
                //定义一个URL对象
                try {
                    URL url = new URL("http://youimg1.c-ctrip.com/target/tg/066/" +
                            "741/886/f8b252560876464f9b2feb742fbb7417.jpg");
                    //打开URL对应的输入流
                    InputStream is = url.openStream();
                    //从InputStream解析出图片
                    bitmap = BitmapFactory.decodeStream(is);

                    handler.sendEmptyMessage(0x123);
                    is.close();
                    //再次打开URL对应的输入流
                    is = url.openStream();
                    //打开手机文件对应的输出流
                    OutputStream os = openFileOutput("crazyit.jgp", MODE_WORLD_READABLE);
                    byte[] buff = new byte[1024];
                    int hasRead = 0;
                    //将URL对应的资源下载到本地
                    while ((hasRead = is.read(buff)) > 0) {
                        os.write(buff, 0, hasRead);
                    }
                    is.close();
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
