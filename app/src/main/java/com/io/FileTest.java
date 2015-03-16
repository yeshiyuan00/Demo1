package com.io;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exp.ysy.demo.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by yeshiyuan on 2015/3/14.
 */
public class FileTest extends Activity {
    private Button bn_write, bn_read;
    private EditText edt_write, edt_read;
    final String FILE_NAME = "crazyit.bin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filetest);
        bn_read = (Button) findViewById(R.id.bn_read);
        bn_write = (Button) findViewById(R.id.bn_write);
        edt_read = (EditText) findViewById(R.id.edt_read);
        edt_write = (EditText) findViewById(R.id.edt_write);

        System.out.print(new StringBuilder("a").append("b").append("c").toString());
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
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasread = 0;
            StringBuilder sb = new StringBuilder("");
            //读取文件内容
            while ((hasread = fis.read(buff)) > 0) {

                sb.append(new String(buff, 0, hasread));
            }
            //关闭文件输入流
            fis.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String s) {

        try {
            //以追加模式打开文件输出流
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            //将FileOutputStream包装成PrintStream
            PrintStream ps = new PrintStream(fos);
            //输出文件内容
            ps.println(s);
            //关闭文件输出流
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
