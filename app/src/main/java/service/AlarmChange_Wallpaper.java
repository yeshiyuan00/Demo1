package service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/23.
 */
public class AlarmChange_Wallpaper extends Activity {

    Button start, stop;
    AlarmManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twobutton);
        start = (Button) findViewById(R.id.bn_start);
        stop = (Button) findViewById(R.id.bn_stop);
        start.setText("启动更换壁纸");
        stop.setText("停止更换壁纸");

        aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        //指定启动ChangeService的组件
        Intent intent = new Intent(AlarmChange_Wallpaper.this, ChangeService.class);
        //创建PendingIntent对象
        final PendingIntent pi = PendingIntent.getService(AlarmChange_Wallpaper.this, 0, intent, 0);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置每隔5秒执行pi代表的组件一次
                aManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 5000, pi);
                start.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(AlarmChange_Wallpaper.this, "壁纸定时更换启动成功了",
                        Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(true);
                stop.setEnabled(false);
                //取消对pi的调度
                aManager.cancel(pi);
            }
        });
    }
}
