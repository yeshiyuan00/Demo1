package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ysy on 2015/3/23.
 */
public class MyReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "接收到的intent的action为：" + intent.getAction() +
                "\n消息内容为：" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        //创建一个bundle对象，并存入数据
        Bundle bundle = new Bundle();
        bundle.putString("first", "第一个BroadCastReceiver存入的消息");
        //将bundle放入结果中
        setResultExtras(bundle);
        //取消broadCas的继续传播
        //abortBroadcast();
    }
}
