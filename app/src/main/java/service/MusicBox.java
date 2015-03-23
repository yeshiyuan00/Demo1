package service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exp.ysy.demo.R;

import java.security.PublicKey;

/**
 * Created by ysy on 2015/3/23.
 */
public class MusicBox extends Activity implements View.OnClickListener {

    TextView title, author;
    ImageView play, stop;

    ActivityReceiver activityReceiver;

    public static final String CTL_ACTION = "com.exp.ysy.demo.action.CTL_ACTION";
    public static final String UPDATE_ACTION = "com.exp.ysy.demo.action.UPDATE_ACTION";

    //定义音乐的播放状态，0x11代表没有播放，0x12代表正在播放，0x13代表暂停。
    int status = 0x11;

    String[] titleStrs = new String[]{"心愿", "约定", "美丽新世界"};
    String[] authorStrs = new String[]{"未知艺术家", "周蕙", "伍佰"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_musicbox);

        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        play = (ImageView) findViewById(R.id.play);
        stop = (ImageView) findViewById(R.id.stop);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        activityReceiver = new ActivityReceiver();
        //创建intentFilter
        IntentFilter filter = new IntentFilter();
        //指定BroadCastReceiver监听的action
        filter.addAction(UPDATE_ACTION);
        //注册BroadCastReceiver
        registerReceiver(activityReceiver, filter);

        Intent intent = new Intent(this, MusicService.class);
        //启动后台Service
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        //创建intent
        Intent intent = new Intent("com.exp.ysy.demo.action.CTL_ACTION");

        switch (v.getId()) {

            case R.id.play:
                intent.putExtra("control", 1);
                break;
            case R.id.stop:
                intent.putExtra("control", 2);
                break;
        }
        //发送广播，将被Service组件中的BroadCastReceiver接收到
        sendBroadcast(intent);

    }

    //自定义BroadCastReceive，负责监听从Service传回来的广播
    public class ActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取intent中的update消息，update代表播放状态
            int update = intent.getIntExtra("update", -1);
            //获取intent中的current消息，current代表当前正在播放的歌曲
            int current = intent.getIntExtra("current", -1);
            if (current >= 0) {
                title.setText(titleStrs[current]);
                author.setText(authorStrs[current]);
            }
            switch (update) {
                case 0x11:
                    play.setBackgroundResource(R.drawable.play_button);
                    status = 0x11;
                    break;
                case 0x12:
                    play.setBackgroundResource(R.drawable.play_button_pressed);
                    status = 0x12;
                    break;
                case 0x13:
                    play.setBackgroundResource(R.drawable.play_button);
                    status = 0x13;
                    break;
            }
        }
    }
}
