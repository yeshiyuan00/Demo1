package com.resources;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exp.ysy.demo.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by ysy on 2015/3/9.
 */
public class XmlResTest extends Activity {

    private Button btn_xml;
    private EditText edt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xmlres);
        btn_xml = (Button) findViewById(R.id.btn_xml);
        edt_show = (EditText) findViewById(R.id.edt_show);

        btn_xml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据xml资源ID获取解析该资源的解析器
                //XmlResourceParser是XmlPullParser的子类
                XmlResourceParser xrp = getResources().getXml(R.xml.books);

                try {
                    StringBuilder sb = new StringBuilder();
                    //还没到xml文档的结尾处
                    while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                        //如果遇到开始标签
                        if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                            //获取该标签的签名
                            String tagName = xrp.getName();
                            if (tagName.equals("book")) {
                                //根据属性名来获取属性值
                                String bookName = xrp.getAttributeValue(null, "price");
                                sb.append("价格： " + bookName);

                                String bookPrice = xrp.getAttributeValue(1);
                                sb.append("    出版日期： " + bookPrice);

                                sb.append(" 书名： ");
                                //获取文本节点的值
                                sb.append(xrp.nextText());
                            }
                            sb.append("\n");
                        }
                        xrp.next();
                    }

                    edt_show.setText(sb.toString());

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
