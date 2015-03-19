package service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/19.
 */
public class BindServiceTest extends Activity {

    Button bind, unbind, getServiceStatus;
    //保持启动的Service的Ibind对象
    BindService.MyBinder binder;
    //定义一个ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection() {
        //该Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("--Service Connected--");
            //获取Service的onBind方法所返回的MyBinder对象
            binder = (BindService.MyBinder) service;

        }

        //该Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("--Service DisConnected--");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_threebutton);
        bind = (Button) findViewById(R.id.bn_bind);
        unbind = (Button) findViewById(R.id.bn_unbind);
        getServiceStatus = (Button) findViewById(R.id.bn_status);
        //创建启动Service的Intent
        final Intent intent = new Intent();
        //为Intent设置Action属性
        intent.setAction("com.exp.ysy.demo.service.BIND_SERVICE");

        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定指定的Service
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });

        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解出绑定的Service
                unbindService(conn);
            }
        });

        getServiceStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取、并显示Service的count值
                Toast.makeText(BindServiceTest.this,
                        "Service的count值为：" + binder.getCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
