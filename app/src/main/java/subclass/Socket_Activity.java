package subclass;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exp.ysy.demo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * Created by ysy on 2015/2/5.
 */
public class Socket_Activity extends Activity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    String line;

    /**
     * 2
     */

    private EditText input;
    private Button btn_send;
    private TextView show;
    ClientThread clientThread;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:

                    textView.setText("来自服务器的数据:" + line);
                    break;

                case 0x123:

                    show.append("\n" + msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_socket);

        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

        input = (EditText) findViewById(R.id.input);
        btn_send = (Button) findViewById(R.id.btn_send);
        show = (TextView) findViewById(R.id.show);

        button.setOnClickListener(this);

        show.setMovementMethod(ScrollingMovementMethod.getInstance());

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    //按下发送按钮，将输入的数据封装成Message
                    //然后发送给子线程的Handler

                    Message msg = new Message();
                    msg.what = 0x345;
                    msg.obj = input.getText().toString();

                    clientThread.revHandler.sendMessage(msg);
                    input.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        clientThread = new ClientThread(handler);
        //客户端启动clientThread线程创建网络连接，读取来自服务器的数据
        new Thread(clientThread).start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:

                new Thread() {


                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("192.168.9.46", 3000);

                            //15秒为响应即为超时
                            socket.setSoTimeout(15000);

                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            line = br.readLine();

                            Message message = new Message();

                            message.what = 1;

                            handler.sendMessage(message);

                            br.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();

                break;


        }

    }


}
