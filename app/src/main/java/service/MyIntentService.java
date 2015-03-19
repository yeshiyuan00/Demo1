package service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ysy on 2015/3/19.
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }


    public MyIntentService() {
        super("MyIntentService");
    }

    //intentservice会使用单独的线程来执行该方法的代码
    @Override
    protected void onHandleIntent(Intent intent) {
        //该方法内可以执行任何耗时任务，比如下载文件等，此处只是让线程暂停20秒
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

    }
}
