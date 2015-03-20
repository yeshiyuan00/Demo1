package service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.exp.ysy.demo.R;

import java.io.IOException;

/**
 * Created by ysy on 2015/3/20.
 */
public class AudioTest extends Activity {
    Button play, up, down;
    ToggleButton mute;
    AudioManager aManager;
    boolean isplaying = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sudiotest);
        play = (Button) findViewById(R.id.play);
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        mute = (ToggleButton) findViewById(R.id.mute);
        //获取系统的音频服务
        aManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isplaying) {
                    mediaPlayer = MediaPlayer.create(AudioTest.this, R.raw.test);
                    //设置循环播放
                    mediaPlayer.setLooping(true);
                    //开始播放
                    mediaPlayer.start();
                    isplaying = true;
                }

            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增大音量，显示音量条
                aManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC
                        , AudioManager.ADJUST_RAISE
                        , AudioManager.FLAG_SHOW_UI);

            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }
        });

        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //是否静音
                aManager.setStreamMute(AudioManager.STREAM_MUSIC, isChecked);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
