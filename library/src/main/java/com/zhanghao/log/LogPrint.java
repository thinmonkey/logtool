package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-13:44
 */

public interface LogPrint {
    void d(String tag, String message);

    void e(String tag, String message);

    void e(String tag, Throwable throwable);

    void e(String tag, String message, Throwable throwable);

    void i(String tag, String message);

    void w(String tag, String message);

    void v(String tag, String message);

    void d(String message);

    void e(String message);

    void i(String message);

    void w(String message);

    void v(String message);
}
