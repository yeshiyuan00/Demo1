package com.io;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeshiyuan on 2015/3/15.
 */
public class SDCardFileExplorer extends Activity {
    private ListView listView;
    private TextView textView;
    private Button bn_back;
    //记录当前的父文件夹
    File currentParent;
    //记录当前路径下的所有文件an的文件数组
    File[] currentFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sdexplorer);
        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.path);
        bn_back = (Button) findViewById(R.id.parent);
        //获取系统的SD卡目录
        File root = new File("/mnt/sdcard/");
        //如果SD卡存在
        if (root.exists()) {
            currentParent = root;
            currentFiles = root.listFiles();
            //使用当前目录下的全部文件、文件夹填充ListView;
            inflateListView(currentFiles);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view,
                                        int i, long l) {
                    //用户单击了文件，直接返回，不做任何处理
                    if (currentFiles[i].isFile()) return;
                    //获取用户单机的文件夹下的所有文件
                    File[] tmp = currentFiles[i].listFiles();
                    if (tmp == null | tmp.length == 0) {
                        Toast.makeText(SDCardFileExplorer.this,
                                "当前路径下没有文件或当前路径不可访问",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        //用户单机的文件夹设为父文件夹
                        currentParent = currentFiles[i];
                        //保存当前的父文件夹内的全部文件和文件夹
                        currentFiles = tmp;
                        //再次更新ListView
                        inflateListView(currentFiles);
                    }
                }
            });
            //获取上一级目录的按钮
            bn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        if (!currentParent.getCanonicalPath().equals("/")) {
                            //获取上一级目录
                            currentParent = currentParent.getParentFile();
                            //列出当前目录下的所有文件
                            currentFiles = currentParent.listFiles();
                            //再次更新ListView
                            inflateListView(currentFiles);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void inflateListView(File[] currentFiles) {
        //创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < currentFiles.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            //如果当前file是文件夹，使用folder图标；否则使用file图标
            if (currentFiles[i].isDirectory()) {

                listItem.put("icon", R.drawable.folder);
            } else {
                listItem.put("icon", R.drawable.file);
            }
            listItem.put("fileName", currentFiles[i].getName());
            listItems.add(listItem);
        }
        //创建一个simpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.layout_line, new String[]{"icon", "fileName"},
                new int[]{R.id.icon, R.id.file_name});
        //为listview设置adapter
        listView.setAdapter(simpleAdapter);

        try {
            textView.setText("当前路径为：" + currentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
