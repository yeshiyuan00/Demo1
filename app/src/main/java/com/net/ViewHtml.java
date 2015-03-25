package com.net;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.exp.ysy.demo.R;

public class ViewHtml extends Activity {
    WebView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_html);
        show = (WebView) findViewById(R.id.show);

        StringBuilder sb = new StringBuilder();
        //拼接一段HTML代码
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>欢迎您</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h2>欢迎您访问<a href\"http://www.crazyit.org\">" +
                "疯狂JAVA联盟</a></h2>");
        sb.append("</body>");
        sb.append("</html>");
        //使用简单的loadData方法会导致乱码
        //show.loadData(sb.toString(), "text/html", "utf-8");
        //加载，并显示Html代码
        show.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_html, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
