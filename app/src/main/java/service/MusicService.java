package service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by ysy on 2015/3/23.
 */
public class MusicService extends Service {
    MyReceiver serviceReceiver;

    MediaPlayer mPlayer;

    AssetManager am;

    //定义音乐的播放状态，0x11代表没有播放，0x12代表正在播放，0x13代表暂停。
    int status = 0x11;

    String[] musics = new String[]{"wish.mp3", "promise.mp3", "beautiful.mp3"};
    int current = 0;


    @Override
    public void onCreate() {
        am = getAssets();

        serviceReceiver = new MyReceiver();
        //创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicBox.CTL_ACTION);

        registerReceiver(serviceReceiver, filter);

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current >= 3) {
                    current = 0;
                }

                Intent sendIntent = new Intent(MusicBox.UPDATE_ACTION);
                sendIntent.putExtra("current", current);

                sendBroadcast(sendIntent);

                prepareAndPlay(musics[current]);
            }
        });
        super.onCreate();
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int control = intent.getIntExtra("control", -1);

            switch (control) {
                //播放或暂停
                case 1:

                    //原来没有处于播放状态
                    if (status == 0x11) {
                        //准备并播放音乐
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    //原来处于播放状态
                    else if (status == 0x12) {

                        //暂停
                        mPlayer.pause();
                        status = 0x13;
                    }
                    //原来处于暂停状态
                    else if (status == 0x13) {
                        //播放
                        mPlayer.start();
                        status = 0x12;
                    }
                    break;

                //停止声音
                case 2:
                    //如果原来正在播放或者暂停
                    if (status == 0x13 || status == 0x12) {
                        mPlayer.stop();
                        status = 0x11;
                        break;
                    }
            }

            //广播通知activity更改图标和文本框
            Intent sendIntent = new Intent(MusicBox.UPDATE_ACTION);
            sendIntent.putExtra("update", status);
            sendIntent.putExtra("current", current);
            sendBroadcast(sendIntent);
        }
    }


    private void prepareAndPlay(String music) {

        try {
            //打开指定音乐文件
            AssetFileDescriptor afd = am.openFd(music);
            mPlayer.reset();
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
