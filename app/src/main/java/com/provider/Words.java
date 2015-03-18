package com.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ysy on 2015/3/18.
 */
public final class Words {
    //定义该contentProvider的Authority
    public static final String AUTHORITY = "com.exp.ysy.demo.providers.dictprovider";

    //定义一个静态内部类，定义该contentProvider所包含的数据列的列名
    public static final class Word implements BaseColumns {

        //定义contentProvider所允许操作的三个数据列
        public static final String _ID = "_id";
        public static final String WORD = "word";
        public static final String DETAIL = "detail";

        //定义该contentProvider提供服务的两个Uri
        public final static Uri DICT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/words");
        public final static Uri WORD_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/word");
    }

}

