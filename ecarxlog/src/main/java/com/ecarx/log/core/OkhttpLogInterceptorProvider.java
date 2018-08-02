package com.ecarx.log.core;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;

/**
 * Created by zhanghao.
 * 2018/3/30-13:44
 */

public class OkhttpLogInterceptorProvider {

    private static ChuckInterceptor chuckInterceptor;

    public static void init(Context context) {
        chuckInterceptor = new ChuckInterceptor(context);
        chuckInterceptor.showNotification(false);
    }

    public static void setHttpLogReceiver(ChuckInterceptor.HttpLogReceiver httpLogReceiver) {
        chuckInterceptor.setHttpLogReceiver(httpLogReceiver);
    }

    public static ChuckInterceptor getChuckInterceptor() {
        return chuckInterceptor;
    }
}
