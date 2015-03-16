package com.exp.ysy.demo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class Bluetooth_demo extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button btn_search;
    Button btn_find1;
    Button btn_finddevices;
    Button btn_ediscover;
    Button btn_localinfo, btn_accept, btn_send;
    EditText edt_message, edt_rmessage, edt_tmessage;
    ListView list_pair = null;
    ListView list_devices = null;
    List<String> list1 = new ArrayList<String>();

    List<BluetoothDevice> list1_device = new ArrayList<BluetoothDevice>();

    BluetoothAdapter bluetoothAdapter = null;

    private final int MESSAGE_READ = 1;

    private boolean isConnected = false;

    private final static String BlueName = "HostName";
    private final String ConnectedDeviceName = "";
    /*private final static UUID uuid =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");*/
    private final static UUID uuid =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //00001101-0000-1000-8000-00805F9B34FB
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 2:

                    btn_accept.setText("已作为服务器");

                    break;

                case 3:

                    btn_accept.setText("已连接");

                    break;
            }
        }
    };

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            byte[] test = (byte[]) msg.obj;
            String test1 = EncodingUtils.getAsciiString(test);
            try {
                String test2 = new String(test, "utf-8");
                btn_accept.setText(msg.arg1 + "");
                edt_tmessage.setText(edt_rmessage.getText());
                edt_rmessage.setText("他：" + test2 + "");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("fail" + e);
            }


        }
    };

    private final BroadcastReceiver mreceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {


                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.
                        EXTRA_DEVICE);
                list1_device.add(device);
                list1.add(device.getName() + "\n" + device.getAddress());


            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                list1.add("搜索完成");

            }
            ArrayAdapter adapter1 = new ArrayAdapter<String>(Bluetooth_demo.this,
                    android.R.layout.simple_expandable_list_item_1, list1);
            list_devices.setAdapter(adapter1);


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bluetooth);

        FindViewById();


        SetListener();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取蓝牙适配器

        //注册，当一个设备被发现时调用onReceive
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mreceiver, filter);

        //当搜索结束后调用onReceive
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mreceiver, filter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_search:
                search_local();
                break;

            case R.id.button_find1:
                find_pair();
                break;

            case R.id.button_finddevices:
                find_devices();
                break;
            case R.id.btn_localinfo:
                list_local();
                break;
            case R.id.btn_ediscover:

                enable_discover();
                break;
            case R.id.btn_accept:

                new AcceptThread().start();
                break;

            case R.id.btn_send:
                sendmessage();
                break;
        }
    }

    void FindViewById() {

        btn_search = (Button) findViewById(R.id.button_search);
        btn_find1 = (Button) findViewById(R.id.button_find1);
        btn_finddevices = (Button) findViewById(R.id.button_finddevices);
        list_pair = (ListView) findViewById(R.id.list_bluetooth);
        list_devices = (ListView) findViewById(R.id.list_bluetoothdevices);
        btn_ediscover = (Button) findViewById(R.id.btn_ediscover);
        btn_localinfo = (Button) findViewById(R.id.btn_localinfo);
        btn_accept = (Button) findViewById(R.id.btn_accept);
        btn_send = (Button) findViewById(R.id.btn_send);
        edt_message = (EditText) findViewById(R.id.edt_message);
        edt_rmessage = (EditText) findViewById(R.id.edt_rmessage);
        edt_tmessage = (EditText) findViewById(R.id.edt_tmessage);
    }

    void SetListener() {
        btn_search.setOnClickListener(this);
        btn_find1.setOnClickListener(this);
        btn_finddevices.setOnClickListener(this);
        btn_ediscover.setOnClickListener(this);
        btn_localinfo.setOnClickListener(this);
        btn_accept.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        list_devices.setOnItemClickListener(this);
    }

    /**
     * 打开蓝牙
     */
    void search_local() {

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "本地设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {

            Intent enablebtintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enablebtintent, 1);
        } else Toast.makeText(this, "蓝牙已经打开了..", Toast.LENGTH_SHORT).show();

        new AcceptThread().start();
    }


    /**
     * 列出已配对蓝牙
     */
    void find_pair() {
        List<String> list = new ArrayList<String>();
        Set<BluetoothDevice> PairedDevices = bluetoothAdapter.getBondedDevices();
        if (PairedDevices.size() > 0) {
            for (BluetoothDevice device : PairedDevices) {

                list.add(device.getName() + "\n" + device.getAddress());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(Bluetooth_demo.this,
                android.R.layout.simple_expandable_list_item_1, list);

        list_pair.setAdapter(adapter);
    }


    /**
     * 搜索蓝牙设备
     */
    void find_devices() {

        // Don't forget to unregister during onDestroy
        list1_device.clear();
        list1.clear();
        bluetoothAdapter.startDiscovery();

    }


    /**
     * 使本地蓝牙可见
     */
    void enable_discover() {

        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.
                SCAN_MODE_CONNECTABLE_DISCOVERABLE) {

            Intent discoverintent = new Intent(BluetoothAdapter.
                    ACTION_REQUEST_DISCOVERABLE);
            //300秒内可见
            discoverintent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverintent);
        }
    }

    /**
     * 本机信息
     */
    void list_local() {

        List<String> list_local = new ArrayList<String>();
        list_local.add(bluetoothAdapter.getName() + "\n" + bluetoothAdapter.getAddress());
        ArrayAdapter adapter = new ArrayAdapter(Bluetooth_demo.this,
                android.R.layout.simple_expandable_list_item_1, list_local);
        list_pair.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        bluetoothAdapter.cancelDiscovery();
        new ConnectThread(list1_device.get(position)).start();
    }

    /**
     * Connecting as a server服务器线程
     */
    private class AcceptThread extends Thread {

        private final BluetoothServerSocket mmServerSocket;

        AcceptThread() {

            BluetoothServerSocket tmp = null;


            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(BlueName, uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmServerSocket = tmp;
        }

        @Override
        public void run() {
            BluetoothSocket socket = null;

            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);

            while (true) {

                try {
                    socket = mmServerSocket.accept();
                    isConnected = true;
                } catch (IOException e) {
                    break;
                }

                if (socket != null) {

                    Message message1 = new Message();
                    message.what = 3;
                    handler.sendMessage(message1);

                    new ConnectedThread(socket).start();
                    //manageConnectedSocket(socket);
                    //btn_find1.setText("haha");
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        /**
         * Will cancel the listening socket, and cause the thread to finish
         */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Connecting as a client
     */
    private class ConnectThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;


        public ConnectThread(BluetoothDevice device) {


            BluetoothSocket tmp = null;
            mmDevice = device;

           /* try {
                tmp = device.createRfcommSocketToServiceRecord(uuid);

            } catch (IOException e) {
                e.printStackTrace();
            }*/

            /**
             * 上面的是官方给出的连接方法，需要给定相同的uuid
             * 下面的方法不需要相同的uuid
             * */

            Method m = null;
            try {

                m = device.getClass().getMethod(
                        "createRfcommSocket", new Class[]{int.class});
                tmp = (BluetoothSocket) m.invoke(device, 1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            mmSocket = tmp;
        }

        @Override
        public void run() {

            bluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
                isConnected = true;
                new ConnectedThread(mmSocket).start();
                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = edt_message.getText().toString();
                        byte[] test = message.getBytes();
                        new ConnectedThread(mmSocket).write(test);
                        edt_message.setText("");
                        edt_tmessage.setText(edt_rmessage.getText());
                        edt_rmessage.setText("我：" + message);
                        //隐藏软键盘
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edt_message.getWindowToken(), 0);
                    }
                });

            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            //manageConnectedSocket(mmSocket);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private class ConnectedThread extends Thread {

        private final BluetoothSocket mmSocket;
        private OutputStream mmOutStream;
        private InputStream mmInStream;

        public ConnectedThread(BluetoothSocket socket) {

            mmSocket = socket;
            OutputStream tmpOut = null;
            InputStream tmpIn = null;


            try {
                tmpOut = mmSocket.getOutputStream();
                tmpIn = mmSocket.getInputStream();
            } catch (IOException e) {

            }

            mmOutStream = tmpOut;
            mmInStream = tmpIn;
        }

        @Override
        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = edt_message.getText().toString();
                    byte[] test = message.getBytes();
                    new ConnectedThread(mmSocket).write(test);
                    edt_message.setText("");
                    edt_tmessage.setText(edt_rmessage.getText());
                    edt_rmessage.setText("我：" + message);
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_message.getWindowToken(), 0);
                }
            });

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);

                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /**
         * Call this from the main activity to send data to the remote device
         */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /**
         * Call this from the main activity to shutdown the connection
         */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }


    /**
     * 发送消息
     */
    void sendmessage() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mreceiver);
    }
}

