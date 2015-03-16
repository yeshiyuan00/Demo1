package subclass;

import android.app.Activity;
import android.os.Bundle;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/1/20.
 */
public class CustomView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_customtitleview);
    }
}
