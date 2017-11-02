package com.zhanghao.log;

import android.util.Log;

/**
 * Created by zhanghao.
 * 2017/10/8-14:16
 */

public class OutputAndroidLogcat extends com.zhanghao.log.OutputStrategyImp {

    public OutputAndroidLogcat() {
        formatStrategy = new com.zhanghao.log.PretteyFormatStrategy.Builder().build();
    }

    @Override
    public void log(com.zhanghao.log.LogBean logBean) {
        com.zhanghao.log.LogBean output = formatStrategy.covertMessage(logBean);
        switch (output.getPriority()) {
            case LogPrintImpl.DEBUG:
                for (String content : output.getWrapContent()) {
                    Log.d(output.getTag(), content);
                }
                break;
            case LogPrintImpl.VERBOSE:
                for (String content : output.getWrapContent()) {
                    Log.v(output.getTag(), content);
                }
                break;
            case LogPrintImpl.ERROR:
                for (String content : output.getWrapContent()) {
                    Log.e(output.getTag(), content);
                }
                break;
            case LogPrintImpl.INFO:
                for (String content : output.getWrapContent()) {
                    Log.i(output.getTag(), content);
                }
                break;
            case LogPrintImpl.WARN:
                for (String content : output.getWrapContent()) {
                    Log.w(output.getTag(), content);
                }
                break;
        }
    }
}
