package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-13:52
 */

public class LogPrintImpl implements LogPrint {

    private boolean isLogToAndroid = true;
    private boolean isLogToDisk;
    private boolean isLogToNet;
    private boolean isLogToWindow;

    private OutputAndroidLogcat outputAndroidLogcat;
    private OutputDisk outputDisk;
    private OutputNet outputNet;
    private OutputWindow outputWindow;

    private String diskFilePath;
    private int diskFileMaxSize;

    private static LogPrintImpl logPrintImpl;
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    private LogPrintImpl() {
        outputAndroidLogcat = new OutputAndroidLogcat();
    }

    static LogPrintImpl getInstance() {
        if (logPrintImpl == null) {
            synchronized (LogPrintImpl.class) {
                if (logPrintImpl == null) {
                    logPrintImpl = new LogPrintImpl();
                }
            }
        }
        return logPrintImpl;
    }

    public void init(Builder builder) {
        this.isLogToAndroid = builder.isLogToAndroid;
        this.isLogToWindow = builder.isLogToWindow;
        this.isLogToNet = builder.isLogToNet;
        this.isLogToDisk = builder.isLogToDisk;
        this.diskFilePath = builder.diskFilePath;
        this.diskFileMaxSize = builder.diskFileMaxSize;

        outputDisk = new OutputDisk(diskFilePath, diskFileMaxSize);
        outputNet = new OutputNet();
        if (builder.netAdapter != null) {
            outputNet.setLogAdapter(builder.netAdapter);
        }
        outputWindow = new OutputWindow();
        if (builder.windowAdapter != null) {
            outputWindow.setLogAdapter(builder.windowAdapter);
        }
    }

    @Override
    public void d(String tag, String message, String keyword) {
        LogBean logBean = createLogBean(DEBUG, tag, message, keyword);
        printLog(logBean);
    }

    @Override
    public void e(String tag, String message, String keyword) {
        LogBean logBean = createLogBean(ERROR, tag, message, keyword);
        printLog(logBean);
    }

    @Override
    public void e(String tag, Throwable throwable) {
        e(tag, throwable, null);
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        e(tag, message, throwable, null);
    }


    @Override
    public void e(String tag, Throwable throwable, String keyword) {
        e(tag, null, throwable, keyword);
    }

    @Override
    public void e(String tag, String message, Throwable throwable, String keyword) {
        if (throwable != null && message != null) {
            message += " : " + Utils.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable);
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }
        LogBean logBean = createLogBean(ERROR, tag, message, keyword);
        printLog(logBean);
    }

    @Override
    public void i(String tag, String message, String keyword) {
        LogBean logBean = createLogBean(INFO, tag, message, keyword);
        printLog(logBean);
    }

    @Override
    public void w(String tag, String message, String keyword) {
        LogBean logBean = createLogBean(WARN, tag, message, keyword);
        printLog(logBean);
    }

    @Override
    public void v(String tag, String message, String keyword) {
        LogBean logBean = createLogBean(VERBOSE, tag, message, keyword);
        printLog(logBean);
    }

    @Override
    public void d(String tag, String message) {
        LogBean logBean = createLogBean(DEBUG, tag, message, null);
        printLog(logBean);
    }

    @Override
    public void e(String tag, String message) {
        LogBean logBean = createLogBean(ERROR, tag, message, null);
        printLog(logBean);
    }

    @Override
    public void i(String tag, String message) {
        LogBean logBean = createLogBean(INFO, tag, message, null);
        printLog(logBean);
    }

    @Override
    public void w(String tag, String message) {
        LogBean logBean = createLogBean(WARN, tag, message, null);
        printLog(logBean);
    }

    @Override
    public void v(String tag, String message) {
        LogBean logBean = createLogBean(VERBOSE, tag, message, null);
        printLog(logBean);
    }

    @Override
    public void w(String message) {
        w(null, message);
    }

    @Override
    public void v(String message) {
        v(null, message);
    }

    private LogBean createLogBean(int debug, String tag, String message, String keyword) {
        LogBean logBean = new LogBean();
        logBean.setPriority(debug);
        logBean.setTag(tag);
        logBean.setStackTraceElement(Thread.currentThread().getStackTrace());
        logBean.setTime(System.currentTimeMillis());
        logBean.setContent(message);
        logBean.setKeyword(keyword);
        return logBean;
    }

    public void setOutputAndroidLogcat(OutputAndroidLogcat outputAndroidLogcat) {
        this.outputAndroidLogcat = outputAndroidLogcat;
    }

    public void setOutputDisk(OutputDisk outputDisk) {
        this.outputDisk = outputDisk;
    }

    public void setOutputNet(OutputNet outputNet) {
        this.outputNet = outputNet;
    }

    public void setOutputWindow(OutputWindow outputWindow) {
        this.outputWindow = outputWindow;
    }

    public void setNetAdapter(LogAdapter logAdapter) {
        outputNet.setLogAdapter(logAdapter);
    }

    private void printLog(LogBean logBean) {
        try {
            if (isLogToAndroid) {
                outputAndroidLogcat.log((LogBean) logBean.clone());
            }
            if (isLogToDisk) {
                outputDisk.log((LogBean) logBean.clone());
            }
            if (isLogToNet) {
                outputNet.log((LogBean) logBean.clone());
            }
            if (isLogToWindow) {
                outputWindow.log((LogBean) logBean.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void d(String message) {
        d(null, message);
    }

    @Override
    public void e(String message) {
        e(null, message);
    }

    @Override
    public void i(String message) {
        i(null, message);
    }

    public static final class Builder {

        private String diskFilePath;
        private int diskFileMaxSize;
        private boolean isLogToAndroid = true;
        private boolean isLogToDisk;
        private boolean isLogToNet;
        private boolean isLogToWindow;

        private LogAdapter netAdapter;
        private LogAdapter windowAdapter;

        public Builder setDiskFilePath(String diskFilePath) {
            this.diskFilePath = diskFilePath;
            return this;
        }

        public Builder setLogOpen(boolean isOpen) {
            isLogToAndroid = isOpen;
            isLogToDisk = isOpen;
            isLogToWindow = isOpen;
            isLogToNet = isOpen;
            return this;
        }

        public Builder setDiskFileMaxSize(int diskFileMaxSize) {
            this.diskFileMaxSize = diskFileMaxSize;
            return this;
        }

        public Builder setLogToAndroid(boolean logToAndroid) {
            isLogToAndroid = logToAndroid;
            return this;
        }

        public Builder setLogToDisk(boolean logToDisk) {
            isLogToDisk = logToDisk;
            return this;
        }

        public Builder setLogToNet(boolean logToNet) {
            isLogToNet = logToNet;
            return this;
        }

        public Builder setLogToWindow(boolean logToWindow) {
            isLogToWindow = logToWindow;
            return this;
        }

        public Builder setNetAdapter(LogAdapter netAdapter) {
            this.netAdapter = netAdapter;
            return this;
        }

        public Builder setWindowAdapter(LogAdapter windowAdapter) {
            this.windowAdapter = windowAdapter;
            return this;
        }
    }
}
