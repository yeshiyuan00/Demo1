package com.net;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.exp.ysy.demo.R;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class MyWeather extends Activity {

    private Spinner provinceSpinner, citySpinner;
    private ImageView todayIcon1, todayIcon2, tomorrowIcon1, tomorrowIcon2,
            afterdayIcon1, afterdayIcon2;
    private TextView textWeatherToday, textWeatherTomorrow, textWeatherAfterday;
    private TextView textWeatherCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_weather);
        FindViewByID();

        List<String> province = WebServiceUtil.getProvinceList();
        com.net.ListAdapter adapter = new com.net.ListAdapter(this, province);
        provinceSpinner.setAdapter(adapter);

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> cities = WebServiceUtil.getCityListByProvince(
                        provinceSpinner.getSelectedItem().toString());
                com.net.ListAdapter cityAdapter = new com.net.ListAdapter(MyWeather.this, cities);
                citySpinner.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                showWeather(citySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void showWeather(String city) {

        String weatherTody = null;
        String weatherTomorrow = null;
        String weatherAfterday = null;
        String weatherCurrent = null;

        int iconToday[] = new int[2];
        int iconTomorrow[] = new int[2];
        int iconAfterday[] = new int[2];

        //获取远程WebService返回的对象
        SoapObject detail = WebServiceUtil.getWeatherByCity(city);
        //获取天气实况
        weatherCurrent = detail.getProperty(4).toString();
        //解析今天的天气情况
        String data = detail.getProperty(7).toString();
        weatherTody = "今天：" + data.split(" ")[0];
        weatherTody = weatherTody + "\n天气：" + data.split(" ")[1];
        weatherTody = weatherTody + "\n气温：" + detail.getProperty(8).toString();
        weatherTody = weatherTody + "\n风力：" + detail.getProperty(9).toString() + "\n";
        iconToday[0] = parseIcon(detail.getProperty(10).toString());
        iconToday[1] = parseIcon(detail.getProperty(11).toString());

        //解析明天的天气情况
        data = detail.getProperty(12).toString();
        weatherTomorrow = "今天：" + data.split(" ")[0];
        weatherTomorrow = weatherTomorrow + "\n天气：" + data.split(" ")[1];
        weatherTomorrow = weatherTomorrow + "\n气温：" + detail.getProperty(13).toString();
        weatherTomorrow = weatherTomorrow + "\n风力：" + detail.getProperty(14).toString() + "\n";
        iconTomorrow[0] = parseIcon(detail.getProperty(15).toString());
        iconTomorrow[1] = parseIcon(detail.getProperty(16).toString());

        //解析后天的天气情况
        data = detail.getProperty(17).toString();
        weatherAfterday = "今天：" + data.split(" ")[0];
        weatherAfterday = weatherAfterday + "\n天气：" + data.split(" ")[1];
        weatherAfterday = weatherAfterday + "\n气温：" + detail.getProperty(18).toString();
        weatherAfterday = weatherAfterday + "\n风力：" + detail.getProperty(19).toString() + "\n";
        iconAfterday[0] = parseIcon(detail.getProperty(20).toString());
        iconAfterday[1] = parseIcon(detail.getProperty(21).toString());

        //更新当天的天气实况
        textWeatherCurrent.setText(weatherCurrent);
        //更新显示今天的天气图标和文本框
        textWeatherToday.setText(weatherTody);
        todayIcon1.setImageResource(iconToday[0]);
        todayIcon2.setImageResource(iconToday[1]);
        //更新显示明天的天气图标和文本框
        textWeatherTomorrow.setText(weatherTomorrow);
        tomorrowIcon1.setImageResource(iconTomorrow[0]);
        tomorrowIcon2.setImageResource(iconTomorrow[1]);
        //更新显示后天的天气图标和文本框
        textWeatherAfterday.setText(weatherAfterday);
        afterdayIcon1.setImageResource(iconAfterday[0]);
        afterdayIcon2.setImageResource(iconAfterday[1]);
    }

    // 工具方法，该方法负责把返回的天气图标字符串，转换为程序的图片资源ID。
    private int parseIcon(String strIcon)
    {
        if (strIcon == null)
            return -1;
        if ("0.gif".equals(strIcon))
            return R.drawable.a_0;
        if ("1.gif".equals(strIcon))
            return R.drawable.a_1;
        if ("2.gif".equals(strIcon))
            return R.drawable.a_2;
        if ("3.gif".equals(strIcon))
            return R.drawable.a_3;
        if ("4.gif".equals(strIcon))
            return R.drawable.a_4;
        if ("5.gif".equals(strIcon))
            return R.drawable.a_5;
        if ("6.gif".equals(strIcon))
            return R.drawable.a_6;
        if ("7.gif".equals(strIcon))
            return R.drawable.a_7;
        if ("8.gif".equals(strIcon))
            return R.drawable.a_8;
        if ("9.gif".equals(strIcon))
            return R.drawable.a_9;
        if ("10.gif".equals(strIcon))
            return R.drawable.a_10;
        if ("11.gif".equals(strIcon))
            return R.drawable.a_11;
        if ("12.gif".equals(strIcon))
            return R.drawable.a_12;
        if ("13.gif".equals(strIcon))
            return R.drawable.a_13;
        if ("14.gif".equals(strIcon))
            return R.drawable.a_14;
        if ("15.gif".equals(strIcon))
            return R.drawable.a_15;
        if ("16.gif".equals(strIcon))
            return R.drawable.a_16;
        if ("17.gif".equals(strIcon))
            return R.drawable.a_17;
        if ("18.gif".equals(strIcon))
            return R.drawable.a_18;
        if ("19.gif".equals(strIcon))
            return R.drawable.a_19;
        if ("20.gif".equals(strIcon))
            return R.drawable.a_20;
        if ("21.gif".equals(strIcon))
            return R.drawable.a_21;
        if ("22.gif".equals(strIcon))
            return R.drawable.a_22;
        if ("23.gif".equals(strIcon))
            return R.drawable.a_23;
        if ("24.gif".equals(strIcon))
            return R.drawable.a_24;
        if ("25.gif".equals(strIcon))
            return R.drawable.a_25;
        if ("26.gif".equals(strIcon))
            return R.drawable.a_26;
        if ("27.gif".equals(strIcon))
            return R.drawable.a_27;
        if ("28.gif".equals(strIcon))
            return R.drawable.a_28;
        if ("29.gif".equals(strIcon))
            return R.drawable.a_29;
        if ("30.gif".equals(strIcon))
            return R.drawable.a_30;
        if ("31.gif".equals(strIcon))
            return R.drawable.a_31;
        return 0;
    }

    private void FindViewByID() {
        provinceSpinner = (Spinner) findViewById(R.id.province);
        citySpinner = (Spinner) findViewById(R.id.city);
        todayIcon1 = (ImageView) findViewById(R.id.todayWhIcon1);
        todayIcon2 = (ImageView) findViewById(R.id.todayWhIcon2);
        tomorrowIcon1 = (ImageView) findViewById(R.id.tomorrowWhIcon1);
        tomorrowIcon2 = (ImageView) findViewById(R.id.tomorrowWhIcon1);
        afterdayIcon1 = (ImageView) findViewById(R.id.afterdayWhIcon1);
        afterdayIcon2 = (ImageView) findViewById(R.id.afterdayWhIcon1);
        textWeatherToday = (TextView) findViewById(R.id.weatherToday);
        textWeatherTomorrow = (TextView) findViewById(R.id.weatherTomorrow);
        textWeatherAfterday = (TextView) findViewById(R.id.weatherAfterday);
        textWeatherCurrent = (TextView) findViewById(R.id.weatherCurrent);

    }


}
