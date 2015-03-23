package service;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.IBinder;

import com.exp.ysy.demo.R;

import java.io.IOException;

/**
 * Created by ysy on 2015/3/23.
 */
public class ChangeService extends Service {

    //定义定时更换的壁纸资源
    int[] wallPapers = new int[]{R.drawable.dengta, R.drawable.tulips, R.drawable.jellyfish};
    //定义系统的壁纸管理服务
    WallpaperManager wManager;
    //定义当前所显示的壁纸
    int current = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //如果到了最后一张，重新开始
        if (current >= 3) {
            current = 0;
        }

        try {
            //改变壁纸
            wManager.setResource(wallPapers[current++]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化WallpaperManager
        wManager = WallpaperManager.getInstance(this);
    }
}
