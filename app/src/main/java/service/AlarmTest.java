package service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.util.Calendar;

/**
 * Created by ysy on 2015/3/20.
 */
public class AlarmTest extends Activity {
    Button setTime;
    AlarmManager aManager;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twobutton);
        setTime = (Button) findViewById(R.id.bn_start);

        aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                //创建一个TimePickDialog实例，并把它显示出了
                new TimePickerDialog.OnTimeSetListener(AlarmTest.this, 0, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //指定启动AlarmActivity组件
                        Intent intent = new Intent(AlarmTest.this, AlarmActivity.class);
                        //创建PendingIntent对象
                        PendingIntent pi = PendingIntent.getActivity(AlarmTest.this, 0, intent, 0);

                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        //根据用户选择来设置Calendar对象
                        c.set(Calendar.HOUR, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        //设置AlarmManager将在Calendar对应的时间启动组件
                        aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);

                        Toast.makeText(AlarmTest.this, "闹铃设置成功了", Toast.LENGTH_SHORT).show();
                    }
                },currentTime.get(Calendar.HOUR_OF_DAY),currentTime.get(Calendar.MINUTE),false,)

            }
        });
    }
}
