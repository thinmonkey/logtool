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
            case LogTool.DEBUG:
                for (String content : output.getOutputContent()) {
                    Log.d(output.getTag(), content);
                }
                break;
            case LogTool.VERBOSE:
                for (String content : output.getOutputContent()) {
                    Log.v(output.getTag(), content);
                }
                break;
            case LogTool.ERROR:
                for (String content : output.getOutputContent()) {
                    Log.e(output.getTag(), content);
                }
                break;
            case LogTool.INFO:
                for (String content : output.getOutputContent()) {
                    Log.i(output.getTag(), content);
                }
                break;
            case LogTool.WARN:
                for (String content : output.getOutputContent()) {
                    Log.w(output.getTag(), content);
                }
                break;
        }
    }
}
