package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-14:17
 */

public class OutputNet implements OutputStrategy {

    protected LogAdapter logAdapter;

    public void setLogAdapter(LogAdapter logAdapter) {
        this.logAdapter = logAdapter;
    }

    @Override
    public void log(LogBean logBean) {
        if (logAdapter != null) {
            logAdapter.log(logBean);
        }
    }
}
