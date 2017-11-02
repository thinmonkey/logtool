package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/18-14:29
 */

public class LogTool {

    public void d(String tag, String message, String keyword) {
        LogPrintImpl.getInstance().d(tag, message, keyword);
    }

    public void e(String tag, String message, String keyword) {
        LogPrintImpl.getInstance().e(tag, message, keyword);
    }

    public void e(String tag, Throwable throwable, String keyword) {
        LogPrintImpl.getInstance().e(tag, throwable, keyword);
    }

    public void e(String tag, String message, Throwable throwable, String keyword) {
        LogPrintImpl.getInstance().e(tag, message, throwable, keyword);
    }

    public void i(String tag, String message, String keyword) {
        LogPrintImpl.getInstance().i(tag, message, keyword);
    }

    public void w(String tag, String message, String keyword) {
        LogPrintImpl.getInstance().w(tag, message, keyword);
    }

    public void v(String tag, String message, String keyword) {
        LogPrintImpl.getInstance().v(tag, message, keyword);
    }

    public static void d(String tag, String message) {
        LogPrintImpl.getInstance().d(tag, message);
    }

    public static void e(String tag, String message) {
        LogPrintImpl.getInstance().e(tag, message);
    }

    public static void e(String tag, Throwable throwable) {
        LogPrintImpl.getInstance().e(tag, throwable);
    }

    public static void e(String tag, String message, Throwable throwable) {
        LogPrintImpl.getInstance().e(tag, message);
    }

    public static void i(String tag, String message) {
        LogPrintImpl.getInstance().i(tag, message);
    }

    public static void w(String tag, String message) {
        LogPrintImpl.getInstance().w(tag, message);
    }

    public static void v(String tag, String message) {
        LogPrintImpl.getInstance().v(tag, message);
    }

    public static void d(String message) {
        LogPrintImpl.getInstance().d(message);
    }

    public static void e(String message) {
        LogPrintImpl.getInstance().e(message);
    }

    public void i(String message) {
        LogPrintImpl.getInstance().i(message);
    }

    public static void w(String message) {
        LogPrintImpl.getInstance().w(message);
    }

    public static void v(String message) {
        LogPrintImpl.getInstance().v(message);
    }

    public static void init(LogPrintImpl.Builder builder) {
        LogPrintImpl.getInstance().init(builder);
    }
}
