package subclass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by ysy on 2015/2/6.
 */
public class URLTest_Activity extends Activity {

    private ImageView image_url;

    private Bitmap bitmap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {

                image_url.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layoyt_url);

        image_url = (ImageView) findViewById(R.id.image_url);

        new Thread() {

            @Override
            public void run() {

                try {
                    URL url = new URL("http://www.crazyit.org/attachments/month_1008/" +
                            "20100812_7763e970f822325bfb019ELQVym8tW3A.png");

                    InputStream is = url.openStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    handler.sendEmptyMessage(0x123);
                    is.close();
                    is = url.openStream();
                    OutputStream os = openFileOutput("crazyit.png", MODE_WORLD_READABLE);

                    byte[] buff = new byte[1024];
                    int hasRead = 0;

                    while ((hasRead = is.read(buff)) > 0) {

                        os.write(buff, 0, hasRead);
                    }

                    is.close();
                    os.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
