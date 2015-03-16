package com.in;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/2/27.
 */
public class SysAction extends Activity {

    final int PICK_CONTACT = 0;
    private EditText edt_name, edt_phone;
    private Button bn;
    private ImageButton img_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contacts);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        bn = (Button) findViewById(R.id.bn);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);

                intent.setType("vnd.android.cursor.item/phone");

                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        img_home = (ImageButton) findViewById(R.id.img_home);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent();

                intent1.setAction(Intent.ACTION_MAIN);

                intent1.addCategory(Intent.CATEGORY_HOME);

                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_CONTACT:

                if (resultCode == Activity.RESULT_OK) {

                    /**获取返回的数据*/
                    Uri contactData = data.getData();
                    CursorLoader cursorLoader = new CursorLoader(this,
                            contactData, null, null, null, null);
                    Cursor cursor = cursorLoader.loadInBackground();

                    if (cursor.moveToFirst()) {
                        String contactId = cursor.getString(cursor.
                                getColumnIndex(ContactsContract.Contacts._ID));

                        String name = cursor.getString(cursor.
                                getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = "此联系人暂未输入电话号码";

                        Cursor phones = getContentResolver().query(ContactsContract.
                                        CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                                null, null);
                        if (phones.moveToFirst()) {

                            phoneNumber = phones.getString(phones.getColumnIndex
                                    (ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }

                        phones.close();

                        edt_name.setText(name);

                        edt_phone.setText(phoneNumber);
                    }

                    cursor.close();
                }
                break;
        }
    }
}
