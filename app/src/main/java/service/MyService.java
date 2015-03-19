package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ysy on 2015/3/19.
 */
public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //该方法内执行耗时任务可能导致ANR(Application Not Responding)异常
        long endtime = System.currentTimeMillis() + 20 * 1000;
        System.out.println("onStart");
        while (System.currentTimeMillis() < endtime) {
            synchronized (this) {
                try {
                    wait(endtime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("---执行耗时任务完成---");

        return START_STICKY;
    }
}
