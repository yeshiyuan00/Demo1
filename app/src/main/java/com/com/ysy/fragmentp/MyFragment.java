package com.com.ysy.fragmentp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/2/3.
 */
public class MyFragment extends Fragment {

    private String aaa;

    private String getAaa() {
        return aaa;
    }

    private void setAaa(String aaa) {
        this.aaa = aaa;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //layout布局转化成View对象

        /**
         *resource：Fragment需要加载的布局文件
         * root：加载layout的父ViewGroup
         * attactToRoot：false，不返回ViewGroup
         * */
        View view = inflater.inflate(R.layout.fragment, container, false);

        TextView text = (TextView) view.findViewById(R.id.text);
        Button button = (Button) view.findViewById(R.id.button);

        text.setText("静态加载Fragment");
        button.setText("获取内容");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = getAaa();
                Toast.makeText(getActivity(), "value=" + value,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
