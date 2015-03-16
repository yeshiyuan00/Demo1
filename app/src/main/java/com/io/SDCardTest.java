package com.io;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exp.ysy.demo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * Created by yeshiyuan on 2015/3/14.
 */
public class SDCardTest extends Activity {

    private Button bn_write, bn_read;
    private EditText edt_write, edt_read;
    final String FILE_NAME = "/crazyit.bin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filetest);
        bn_read = (Button) findViewById(R.id.bn_read);
        bn_write = (Button) findViewById(R.id.bn_write);
        edt_read = (EditText) findViewById(R.id.edt_read);
        edt_write = (EditText) findViewById(R.id.edt_write);

        bn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write(edt_write.getText().toString());
                edt_write.setText("");
            }
        });
        bn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_read.setText(read());
            }
        });
    }

    private String read() {

        try {
            //如果手机插入了SD卡，且应用程序有访问SD卡的权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //获取SD卡对应的存储目录
                File SDCardDir = Environment.getExternalStorageDirectory();
                //获取指定文件对应的输入流
                FileInputStream fis = new FileInputStream(
                        SDCardDir.getCanonicalFile() + FILE_NAME);
                //将指定的输入流包装成BufferedRead
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder("");
                String line = null;
                //循环读取文件内容
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                //关闭资源
                br.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void write(String s) {
        try {
            //如果手机插入了SD卡，且应用程序有访问SD卡的权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //获取SD卡对应的存储目录
                File SDCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(SDCardDir.getCanonicalPath() + FILE_NAME);
                //以指定文件创建RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
                //将文件记录指针移到文件最后
                raf.seek(targetFile.length());
                //输出文件内容
                raf.write(s.getBytes());
                raf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
