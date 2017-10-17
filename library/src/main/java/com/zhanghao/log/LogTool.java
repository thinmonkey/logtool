package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-13:52
 */

public class LogTool implements com.zhanghao.log.LogPrint {

    private boolean isLogToAndroid = true;
    private boolean isLogToDisk;
    private boolean isLogToNet;
    private boolean isLogToWindow;

    private com.zhanghao.log.OutputAndroidLogcat outputAndroidLogcat;
    private com.zhanghao.log.OutputDisk outputDisk;
    private com.zhanghao.log.OutputNet outputNet;
    private com.zhanghao.log.OutputWindow outputWindow;

    private String diskFilePath;
    private int diskFileMaxSize;

    private static LogTool logTool;
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

    private LogTool() {
        outputAndroidLogcat = new com.zhanghao.log.OutputAndroidLogcat();
    }

    public static LogTool getInstance() {
        if (logTool == null) {
            synchronized (LogTool.class) {
                if (logTool == null) {
                    logTool = new LogTool();
                }
            }
        }
        return logTool;
    }

    public void init(Builder builder) {
        this.isLogToAndroid = builder.isLogToAndroid;
        this.isLogToWindow = builder.isLogToWindow;
        this.isLogToNet = builder.isLogToNet;
        this.isLogToDisk = builder.isLogToDisk;
        this.diskFilePath = builder.diskFilePath;
        this.diskFileMaxSize = builder.diskFileMaxSize;

        outputDisk = new com.zhanghao.log.OutputDisk(diskFilePath, diskFileMaxSize);
        outputNet = new com.zhanghao.log.OutputNet();
        if (builder.netAdapter != null) {
            outputNet.setLogAdapter(builder.netAdapter);
        }
        outputWindow = new com.zhanghao.log.OutputWindow();
        if (builder.windowAdapter != null) {
            outputWindow.setLogAdapter(builder.windowAdapter);
        }
    }

    @Override
    public void d(String tag, String message) {
        com.zhanghao.log.LogBean logBean = createLogBean(DEBUG, tag, message);
        printLog(logBean);
    }

    @Override
    public void e(String tag, String message) {
        com.zhanghao.log.LogBean logBean = createLogBean(ERROR, tag, message);
        printLog(logBean);
    }

    @Override
    public void i(String tag, String message) {
        com.zhanghao.log.LogBean logBean = createLogBean(INFO, tag, message);
        printLog(logBean);
    }

    @Override
    public void e(String tag, Throwable throwable) {
        e(tag, null, throwable);
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        if (throwable != null && message != null) {
            message += " : " + com.zhanghao.log.Utils.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = com.zhanghao.log.Utils.getStackTraceString(throwable);
        }
        if (com.zhanghao.log.Utils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }
        com.zhanghao.log.LogBean logBean = createLogBean(ERROR, tag, message);
        printLog(logBean);
    }

    @Override
    public void w(String tag, String message) {
        com.zhanghao.log.LogBean logBean = createLogBean(WARN, tag, message);
        printLog(logBean);
    }

    @Override
    public void v(String tag, String message) {
        com.zhanghao.log.LogBean logBean = createLogBean(VERBOSE, tag, message);
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

    private com.zhanghao.log.LogBean createLogBean(int debug, String tag, Object message) {
        com.zhanghao.log.LogBean logBean = new com.zhanghao.log.LogBean();
        logBean.setPriority(debug);
        logBean.setTag(tag);
        logBean.setStackTraceElement(Thread.currentThread().getStackTrace());
        logBean.setTime(System.currentTimeMillis());
        logBean.setSourceContent(com.zhanghao.log.Utils.toString(message));
        return logBean;
    }

    public void setOutputAndroidLogcat(com.zhanghao.log.OutputAndroidLogcat outputAndroidLogcat) {
        this.outputAndroidLogcat = outputAndroidLogcat;
    }

    public void setOutputDisk(com.zhanghao.log.OutputDisk outputDisk) {
        this.outputDisk = outputDisk;
    }

    public void setOutputNet(com.zhanghao.log.OutputNet outputNet) {
        this.outputNet = outputNet;
    }

    public void setOutputWindow(com.zhanghao.log.OutputWindow outputWindow) {
        this.outputWindow = outputWindow;
    }

    public void setNetAdapter(com.zhanghao.log.LogAdapter logAdapter) {
        outputNet.setLogAdapter(logAdapter);
    }

    private void printLog(com.zhanghao.log.LogBean logBean) {
        try {
            if (isLogToAndroid) {
                outputAndroidLogcat.log((com.zhanghao.log.LogBean) logBean.clone());
            }
            if (isLogToDisk) {
                outputDisk.log((com.zhanghao.log.LogBean) logBean.clone());
            }
            if (isLogToNet) {
                outputNet.log((com.zhanghao.log.LogBean) logBean.clone());
            }
            if (isLogToWindow) {
                outputWindow.log((com.zhanghao.log.LogBean) logBean.clone());
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

        private com.zhanghao.log.LogAdapter netAdapter;
        private com.zhanghao.log.LogAdapter windowAdapter;

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

        public Builder setNetAdapter(com.zhanghao.log.LogAdapter netAdapter) {
            this.netAdapter = netAdapter;
            return this;
        }

        public Builder setWindowAdapter(com.zhanghao.log.LogAdapter windowAdapter) {
            this.windowAdapter = windowAdapter;
            return this;
        }
    }
}
