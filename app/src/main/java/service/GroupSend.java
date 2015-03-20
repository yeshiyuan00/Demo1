package service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.util.ArrayList;

/**
 * Created by ysy on 2015/3/20.
 */
public class GroupSend extends Activity {
    EditText numbers, content;
    Button select, send;
    SmsManager sManager;
    //记录需要群发的号码列表
    ArrayList<String> sendList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_groupsend);
        numbers = (EditText) findViewById(R.id.number);
        content = (EditText) findViewById(R.id.content);
        send = (Button) findViewById(R.id.send);
        select = (Button) findViewById(R.id.select);

        sManager = SmsManager.getDefault();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String number : sendList) {
                    //创建一个pendingIntent对象
                    PendingIntent pi = PendingIntent.getActivity(GroupSend.this, 0, new Intent(), 0);
                    //发送短信
                    sManager.sendTextMessage(numbers.getText().toString(),
                            null, content.getText().toString(), pi, null);
                }
                Toast.makeText(GroupSend.this, "短信群发完成", Toast.LENGTH_SHORT).show();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询联系人号码
                final Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

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
                        CheckBox rb = new CheckBox(GroupSend.this);
                        //获取联系人的电话号码，并删除中画线、空格
                        String number = cursor.getString(
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                .replace("-", "")
                                .replace(" ", "");
                        rb.setText(number);
                        return rb;
                    }
                };

                //加载布局
                View selectView = getLayoutInflater().inflate(R.layout.layout_listview, null);
                final ListView listView = (ListView) selectView.findViewById(R.id.list);
                listView.setAdapter(adapter);

                new AlertDialog.Builder(GroupSend.this)
                        .setView(selectView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendList.clear();
                                int num = listView.getLastVisiblePosition() -
                                        listView.getFirstVisiblePosition();
                                for (int i = 0; i < num; i++) {
                                    CheckBox checkBox = (CheckBox) listView.getChildAt(i);
                                    if (checkBox.isChecked()) {
                                        sendList.add(checkBox.getText().toString());
                                    }
                                }

                                numbers.setText(sendList.toString());

                            }
                        }).show();
            }
        });
    }

    //判断某个号码是否已在群发名单内
    public boolean isChecked(String number) {
        for (String s1 : sendList) {

            if (s1.equals(number)) {
                return true;
            }
        }
        return false;

    }
}
