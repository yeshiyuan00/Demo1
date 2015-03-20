package service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.android.internal.telephony.ITelephony;
import com.exp.ysy.demo.R;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ysy on 2015/3/20.
 */
public class BlockMain extends Activity {

    Button managerBlock;
    //记录黑名单的List
    ArrayList<String> blockList = new ArrayList<String>();
    TelephonyManager tmanger;
    //监听通话状态的监听器
    CustomPhoneCallListener cpListener;

    public class CustomPhoneCallListener extends PhoneStateListener {

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
                    //如果该号码属于黑名单
                    if (isBlock(incomingNumber)) {
                        try {
                            Method method = Class.forName("android.os.ServiceManager")
                                    .getMethod("getService", String.class);
                            //获取远程TELETHONY_SERVICE的IBinder对象代理
                            IBinder binder = (IBinder) method.invoke(null,
                                    new Object[]{TELEPHONY_SERVICE});
                            //将IBinder对象代理转换为ITelephony对象
                            ITelephony telephony = ITelephony.Stub.asInterface(binder);
                            //挂断电话
                            telephony.endCall();

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                default:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twobutton);
        managerBlock = (Button) findViewById(R.id.bn_start);


        tmanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //监听通话状态的改变
        tmanger.listen(cpListener, PhoneStateListener.LISTEN_CALL_STATE);

        managerBlock.setText("设置黑名单");
        managerBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询联系人的电话号码
                final Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, null, null, null);
                BaseAdapter adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return cursor.getCount();
                    }

                    @Override
                    public Object getItem(int position) {
                        return position;
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        cursor.moveToPosition(position);
                        CheckBox rb = new CheckBox(BlockMain.this);
                        //获取联系人的电话号码，并去掉中间的中画线
                        String number = cursor.getString(cursor.
                                getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                .replace("-", "");
                        rb.setText(number);
                        //如果该号码已被加入黑名单，默然勾选该号码
                        if (isBlock(number)) {
                            rb.setChecked(true);
                        }
                        return rb;
                    }
                };
                //加载listV.xml布局文件对应的View
                View SelectView = getLayoutInflater().inflate(R.layout.layout_listview, null);

                final ListView listView = (ListView) SelectView.findViewById(R.id.list);
                listView.setAdapter(adapter);

                new AlertDialog.Builder(BlockMain.this)
                        .setView(SelectView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //清空BlockList集合
                                blockList.clear();
                                //遍历listview组件的每个选项
                                int count = listView.getLastVisiblePosition() -
                                        listView.getFirstVisiblePosition();
                                for (int i = 0; i < count; i++) {
                                    CheckBox checkBox = (CheckBox) listView.getChildAt(i);
                                    //如果该表项被勾选
                                    if (checkBox.isChecked()) {
                                        //添加改电话号码
                                        blockList.add(checkBox.getText().toString());
                                    }
                                }
                                System.out.println(blockList);
                            }
                        }).show();
            }
        });
    }

    //判断某个电话号码是否在黑名单内
    public boolean isBlock(String phone) {

        for (String s1 : blockList) {
            if (s1.equals(phone)) {
                return true;
            }
        }
        return false;
    }
}
