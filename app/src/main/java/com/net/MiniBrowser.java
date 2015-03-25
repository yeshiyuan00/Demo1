package com.net;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.exp.ysy.demo.R;

public class MiniBrowser extends Activity {
    EditText url;
    WebView show;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_browser);

        url = (EditText) findViewById(R.id.url);
        show = (WebView) findViewById(R.id.show);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlStr = url.getText().toString();
                //保证网页在浏览器中显示而不是调到系统浏览器
                show.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {

                        view.loadUrl(url);

                        return true;
                    }
                });
                //加载并显示urlStr对应的网页
                show.loadUrl(urlStr);

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            String urlStr = url.getText().toString();
            //加载并显示urlStr对应的网页
            show.loadUrl(urlStr);
            return true;
        }
        return false;
    }
}
