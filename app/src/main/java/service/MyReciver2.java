package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ysy on 2015/3/23.
 */
public class MyReciver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = getResultExtras(true);
        //解析前一个BroadCastReceiver所存入的key为first的消息
        String first = bundle.getString("firdt");

        Toast.makeText(context, "第一个BroadCast存入的消息为：" + first
                , Toast.LENGTH_SHORT).show();

    }
}
