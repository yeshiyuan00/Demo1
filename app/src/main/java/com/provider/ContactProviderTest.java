package com.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.util.ArrayList;

/**
 * Created by ysy on 2015/3/18.
 */
public class ContactProviderTest extends Activity {
    Button bn_search, bn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contactprovider);
        bn_search = (Button) findViewById(R.id.bn_search);
        bn_add = (Button) findViewById(R.id.bn_add);

        bn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义两个List来封装系统的联系人信息、指定联系人的电话号码、Email等详情
                final ArrayList<String> names = new ArrayList<String>();
                final ArrayList<ArrayList<String>> details = new ArrayList<ArrayList<String>>();
                //使用ContentResolver查找联系人信息
                Cursor cursor = getContentResolver()
                        .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                //遍历查询联系人，获取系统中所有联系人
                while (cursor.moveToNext()) {
                    //获取联系人Id
                    String contactID = cursor.getString
                            (cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    //获取联系人的名字
                    String name = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    names.add(name);
                    //使用ContentResolver查找联系人的电话号码
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                            null, null);
                    ArrayList<String> detail = new ArrayList<String>();
                    //遍历查询结果，获取该联系人的多个电话号码
                    while (phones.moveToNext()) {
                        //获取查询结果中电话号码列的数据
                        String phoneNumber = phones.getString(
                                phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        detail.add("电话号码:" + phoneNumber);
                    }
                    phones.close();

                    //使用ContentResolver查找联系人的邮箱
                    Cursor emails = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                            null, null);
                    //遍历查询结果，获取该联系人的多个Email
                    while (emails.moveToNext()) {
                        //获取查询结果中Email列的数据
                        String emailAddress = emails.getString(
                                emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        detail.add("邮件地址:" + emailAddress);
                    }
                    emails.close();

                    details.add(detail);
                }
                //加载result.xml界面布局代表的视图
                View resultDialog = getLayoutInflater().inflate(R.layout.layout_result, null);
                //获取resultDialog中ID为list的ExpendableListView
                ExpandableListView listView = (ExpandableListView) resultDialog.findViewById(R.id.list);
                //创建一个ExpandableListAdapter对象
                ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
                    @Override
                    public int getGroupCount() {
                        return names.size();
                    }

                    @Override
                    public int getChildrenCount(int groupPosition) {
                        return details.get(groupPosition).size();
                    }

                    //获取指定组位置处的组数据
                    @Override
                    public Object getGroup(int groupPosition) {
                        return names.get(groupPosition);
                    }

                    //获取指定组位置、指定子列表项处的子列表数据
                    @Override
                    public Object getChild(int groupPosition, int childPosition) {
                        return details.get(groupPosition).get(childPosition);
                    }

                    @Override
                    public long getGroupId(int groupPosition) {
                        return groupPosition;
                    }

                    @Override
                    public long getChildId(int groupPosition, int childPosition) {
                        return childPosition;
                    }

                    @Override
                    public boolean hasStableIds() {
                        return true;
                    }

                    //该方法决定每个组选项的外观
                    @Override
                    public View getGroupView(int groupPosition, boolean isExpanded,
                                             View convertView, ViewGroup parent) {
                        TextView textView = getTextView();
                        textView.setText(getGroup(groupPosition).toString());
                        return textView;
                    }

                    //该方法决定每个子选项的外观
                    @Override
                    public View getChildView(int groupPosition, int childPosition,
                                             boolean isLastChild, View convertView,
                                             ViewGroup parent) {
                        TextView textView = getTextView();
                        textView.setText(getChild(groupPosition, childPosition).toString());
                        return textView;
                    }

                    @Override
                    public boolean isChildSelectable(int groupPosition, int childPosition) {
                        return true;
                    }

                    private TextView getTextView() {
                        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, 64);
                        TextView textView = new TextView(ContactProviderTest.this);
                        textView.setLayoutParams(lp);
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        textView.setPadding(36, 0, 0, 0);
                        textView.setTextSize(20);
                        return textView;
                    }
                };
                //为ExpandableListView设置adapter对象
                listView.setAdapter(adapter);
                //使用对话框来显示查询结果
                new AlertDialog.Builder(ContactProviderTest.this)
                        .setView(resultDialog)
                        .show();
            }
        });

        bn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.edt_name)).getText().toString();
                String phone = ((EditText) findViewById(R.id.edt_phone)).getText().toString();
                String email = ((EditText) findViewById(R.id.edt_mail)).getText().toString();

                //创建一个空的ContentValues
                ContentValues values = new ContentValues();
                //想RawsContacts.CONTENT_URI执行一个控制插入
                //目的是获取系统返回的RawContactId
                Uri rawContactUri = getContentResolver().insert(
                        ContactsContract.RawContacts.CONTENT_URI, values);
                long rawContactId = ContentUris.parseId(rawContactUri);
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                //设置内容类型
                values.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                //设置联系人名字
                values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
                //向联系人uri添加名字
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                //设置联系人的电话号码
                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
                //设置电话的类型
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                //向联系人uri添加电话号码
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                values.clear();

                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
                //设置联系人的Email地址
                values.put(ContactsContract.CommonDataKinds.Email.DATA, email);
                //设置电子邮件的类型
                values.put(ContactsContract.CommonDataKinds.Email.TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK);
                //向联系人Email uri添加Email数据
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                Toast.makeText(ContactProviderTest.this, "联系人添加成功", Toast.LENGTH_LONG).show();

            }
        });
    }
}
