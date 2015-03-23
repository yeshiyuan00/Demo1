package service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/20.
 */
public class AlarmActivity extends Activity {

    MediaPlayer alarmMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载指定音乐，并为其创建MediaPlayer对象
        alarmMusic = MediaPlayer.create(AlarmActivity.this, R.raw.boom);
        alarmMusic.setLooping(true);
        //播放音乐
        alarmMusic.start();
        //创建一个对话框
        new AlertDialog.Builder(AlarmActivity.this)
                .setTitle("闹钟")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //停止音乐
                        alarmMusic.stop();
                        //结束Activity
                        AlarmActivity.this.finish();
                    }
                }).show();
    }
}
