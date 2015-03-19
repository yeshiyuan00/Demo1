package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/19.
 */
public class AidlService extends Service {
    private CatBinder catBinder;
    Timer timer = new Timer();
    String[] colors = new String[]{"红色", "黄色", "黑色"};
    double[] weights = new double[]{2.3, 3.1, 1.58};

    private String color;
    private double weight;

    //继承Stub，也就是实现了ICat接口，并实现了IBinder接口
    public class CatBinder extends ICat.Stub {

        @Override
        public String getCorlor() throws RemoteException {
            return color;
        }

        @Override
        public double getWeight() throws RemoteException {
            return weight;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        catBinder = new CatBinder();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //随机改变Service数组内color、weight属性值
                int rand = (int) (Math.random() * 3);
                color = colors[rand];
                weight = weights[rand];
                System.out.println("--------" + rand);
            }
        }, 0, 800);
    }

    @Override
    public IBinder onBind(Intent intent) {

        /**
         * 返回CatBinder对象，
         * 在绑定本地Service的情况下，该CatBinder对象会直接传给客户端的
         * ServiceConnection对象的onServiceConnected方法的第二个参数
         * 在绑定远程Service的情况下，只将CatBinder对象的代理传给客户端的
         * ServiceConnection对象的onServiceConnected方法的第二个参数
         * */
        return catBinder;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
    }
}
