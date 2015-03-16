package subclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.exp.ysy.demo.R;

/**
 * Created by ysy on 2015/1/14.
 */
public class WebView_demo extends Activity {

    private WebView webView = null;
    private String url = "http://2014.qq.com";

    private ProgressDialog progressDialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);

        webView = (WebView) findViewById(R.id.webview);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用当前应用的webview显示网页
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            //进入页面过程中显示进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(100!=newProgress){
                    if(progressDialog==null){
                        progressDialog=new ProgressDialog(WebView_demo.this);
                       // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setProgress(newProgress);
                        progressDialog.show();
                    }
                    else {

                        progressDialog.setProgress(newProgress);
                    }
                }
                else {
                    if (progressDialog.isShowing()){

                        progressDialog.cancel();
                        progressDialog=null;

                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webSettings=webView.getSettings();

        //可以使用JavaScript脚本
        webSettings.setJavaScriptEnabled(true);

        //设置缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    //设置返回键返回的是页面而不是退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            Toast.makeText(WebView_demo.this,"按下了返回键",Toast.LENGTH_LONG).show();
            if(webView!=null& webView.canGoBack()){
                webView.goBack();

            }
            else{
                System.exit(0);

            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
