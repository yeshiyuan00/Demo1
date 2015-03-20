package service;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.exp.ysy.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ysy on 2015/3/20.
 */
public class TelephoneStatus extends Activity {

    private ListView listView;
    //声明代表状态名的数组
    String[] statusNames;
    //声明代表手机状态的集合
    ArrayList<String> statusValues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview);
        listView = (ListView) findViewById(R.id.list);

        //获取系统的TelephoneManager对象
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //获取各种状态名称的数组
        statusNames = getResources().getStringArray(R.array.statusNames);
        //获取代表SIM卡状态的数组
        String[] simState = getResources().getStringArray(R.array.simState);
        //获取代表电话网络类型的数组
        String[] phoneType = getResources().getStringArray(R.array.phoneType);
        //获取设备编号
        statusValues.add(tManager.getDeviceId());
        //获取系统平台的版本
        statusValues.add(tManager.getDeviceSoftwareVersion()
                != null ? tManager.getDeviceSoftwareVersion() : "未知");
        //获取网络运营商代号
        statusValues.add(tManager.getNetworkOperator());
        //获取网络运营商的名称
        statusValues.add(tManager.getNetworkOperatorName());
        //获取手机网络的类型
        statusValues.add(phoneType[tManager.getPhoneType()]);
        //获取设备所在位置
        statusValues.add(tManager.getCellLocation() != null ?
                tManager.getCellLocation().toString() : "未知位置");
        //获取SIM卡的国别
        statusValues.add(tManager.getSimCountryIso());
        //获取SIM卡序列号
        statusValues.add(tManager.getSimSerialNumber());
        //获取SIM卡的状态
        statusValues.add(simState[tManager.getSimState()]);

        ArrayList<Map<String, String>> status = new ArrayList<Map<String, String>>();
        //遍历statusValues集合，将statusNames、statusValues封装到List<Map<String, String>>
        //集合中
        for (int i = 0; i < statusValues.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", statusNames[i]);
            map.put("values", statusValues.get(i));
            status.add(map);
        }

        //使用simpleAdapter封装List数据
        SimpleAdapter adapter = new SimpleAdapter(this, status, R.layout.line,
                new String[]{"name", "values"}, new int[]{R.id.my_title, R.id.my_content});

        listView.setAdapter(adapter);
    }
}
