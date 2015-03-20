package service;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.exp.ysy.demo.R;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Created by ysy on 2015/3/20.
 */
public class MonitorPhone extends Activity {
    TelephonyManager tManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line);

        tManger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //创建一个通话状态监听器
        PhoneStateListener listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    //无任何状态
                    case TelephonyManager.CALL_STATE_IDLE:
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    //来电铃响时
                    case TelephonyManager.CALL_STATE_RINGING:
                        OutputStream os = null;

                        try {
                            os = openFileOutput("phoneList", MODE_APPEND);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        PrintStream ps = new PrintStream(os);
                        //将来电号码记录到文件中
                        ps.println(new Date() + "来电：" + incomingNumber);
                        ps.close();
                        break;
                    default:
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        //监听电话通话状态的改变
        tManger.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
