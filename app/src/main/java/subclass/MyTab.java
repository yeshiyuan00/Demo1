package subclass;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TabHost;

import com.exp.ysy.demo.R;

import java.util.zip.Inflater;

/**
 * Created by ysy on 2015/1/13.
 */
public class MyTab extends TabActivity {


    private TabHost MyTabhost;
    protected Menu myMenu;
    protected int myMenuSettingTag = 0;

    private static final int myMenuResources[] = {R.menu.menu_a,
            R.menu.menu_b, R.menu.menu_c};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MyTabhost = this.getTabHost();



        /*
        * from(this)从这个TabActivity获取LayoutInflater

        R.layout.main 存放Tab布局

        通过TabHost获得存放Tab标签页内容的FrameLayout

        是否将inflate 拴系到根布局元素上
        * */
        LayoutInflater.from(this).inflate(R.layout.layout_tabhost,
                MyTabhost.getTabContentView(), true);

        MyTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));


        MyTabhost.addTab(MyTabhost.newTabSpec("aa")//制造一个新的标签TT
                .setIndicator("aa", getResources()
                        .getDrawable(R.drawable.ic_launcher))
                        // 设置一下显示的标题为aa，设置一下标签图标为
                .setContent(R.id.widget_layout_red));
        //设置一下该标签页的布局内容为R.id.widget_layout_red，这是FrameLayout中的一
        // 个子Layout复制代码

        MyTabhost.addTab(MyTabhost.newTabSpec("bb")//制造一个新的标签TT
                .setIndicator("bb", getResources()
                        .getDrawable(R.drawable.ic_launcher))
                        // 设置一下显示的标题为KK，设置一下标签图标为
                .setContent(R.id.widget_layout_Blue));

        MyTabhost.addTab(MyTabhost.newTabSpec("cc")//制造一个新的标签TT
                .setIndicator("cc", getResources()
                        .getDrawable(R.drawable.ic_launcher))
                        // 设置一下显示的标题为KK，设置一下标签图标为
                .setContent(R.id.widget_layout_green));

        //标签切换事件处理，setOnTabChangedListener
        MyTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tagString) {
                // TODO Auto-generated method stub
                if (tagString.equals("aa")) {
                    myMenuSettingTag = 1;
                }
                if (tagString.equals("bb")) {
                    myMenuSettingTag = 2;
                }
                if (tagString.equals("cc")) {
                    myMenuSettingTag = 3;
                }
                if (myMenu != null) {
                    onCreateOptionsMenu(myMenu);
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        myMenu = menu;
        myMenu.clear();

        MenuInflater inflater=getMenuInflater();
        switch (myMenuSettingTag) {
            case 1:
                inflater.inflate(myMenuResources[0],menu);
                break;
            case 2:
                inflater.inflate(myMenuResources[1],menu);
                break;
            case 3:
                inflater.inflate(myMenuResources[2],menu);
                break;
            default:
                inflater.inflate(myMenuResources[0],menu);
                break;


        }
        return super.onCreateOptionsMenu(menu);
    }
}
