package com.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientTets extends Activity {
    EditText response;
    Button get, login;

    HttpClient httpClient;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                response.append(msg.obj.toString() + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_client_tets);
        response = (EditText) findViewById(R.id.response);
        get = (Button) findViewById(R.id.get);
        login = (Button) findViewById(R.id.login);
        //创建DefaultHttpClient对象
        httpClient = new DefaultHttpClient();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessSecret(v);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogin(v);
            }
        });
    }

    public void accessSecret(View v) {
        response.setText("");
        new Thread() {

            @Override
            public void run() {
                //创建一个HttpGet对象
                HttpGet get = new HttpGet("http://i.baidu.com");

                try {

                    //发送Get请求
                    HttpResponse httpResponse = httpClient.execute(get);
                    HttpEntity entity = httpResponse.getEntity();
                    if (entity != null) {
                        //读取服务器响应
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(entity.getContent()));
                        String line = null;

                        while ((line = br.readLine()) != null) {
                            Message message = new Message();
                            message.what = 0x123;
                            message.obj = line;
                            handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void showLogin(View v) {
        //加载登陆界面
        final View LoginDialog = getLayoutInflater().inflate(R.layout.login, null);
        //使用对话框供用户登录系统
        new AlertDialog.Builder(HttpClientTets.this)
                .setTitle("登录系统")
                .setView(LoginDialog)
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //获取用户输入的用户名、密码
                        final String name = ((EditText) LoginDialog.findViewById(R.id.name))
                                .getText().toString();
                        final String pass = ((EditText) LoginDialog.findViewById(R.id.pass))
                                .getText().toString();
                        new Thread() {

                            @Override
                            public void run() {

                                try {
                                    HttpPost post = new HttpPost("http://192." +
                                            "168.1.88:8888/foo/login.jsp");
                                    //如果传递的参数个数过多，可以对传递的参数进行封装
                                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("name", name));
                                    params.add(new BasicNameValuePair("pass", pass));
                                    //设置请求参数
                                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                    //发送Post请求
                                    HttpResponse httpResponse = httpClient.execute(post);
                                    //如果服务器成功的返回响应
                                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                        String msg = EntityUtils.toString(httpResponse.getEntity());
                                        Looper.prepare();
                                        //提示登录成功
                                        Toast.makeText(HttpClientTets.this, msg, Toast.LENGTH_LONG)
                                                .show();
                                        Looper.loop();
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (ClientProtocolException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }).setNegativeButton("取消", null).show();
    }

}
