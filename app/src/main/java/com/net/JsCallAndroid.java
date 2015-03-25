package com.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/3/25.
 */
public class JsCallAndroid extends Activity {
    WebView myWebview;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_html);
        myWebview = (WebView) findViewById(R.id.show);
        //此处为了简化编程，使用file协议加载本地assets目录下的HTML页面
        //如果需要，也可以使用http协议加载远程网站的HTML页面
        myWebview.loadUrl("file:///android_asset/test.html");
        //获取webView的设置对象
        WebSettings settings = myWebview.getSettings();
        //开启javaScript调用
        settings.setJavaScriptEnabled(true);
        //将myObject对象暴露给JavaScript脚本
        //这样test.html页面中的JavaScript可以通过myObj来调用myObject的方法
        myWebview.addJavascriptInterface(new MyObject(this),"myObj");

    }
}
