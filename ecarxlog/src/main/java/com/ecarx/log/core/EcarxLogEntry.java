package com.ecarx.log.core;

import android.content.Context;


import com.aliyun.sls.android.sdk.model.Log;
import com.ecarx.log.util.FileUtils;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.zhanghao.log.LogBean;
import com.zhanghao.log.LogPrintImpl;
import com.zhanghao.log.LogTool;
import com.zhanghao.log.OutputAnyAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * Created by zhanghao.
 * 2017/5/27-13:50
 */

public class EcarxLogEntry {

    private static String LOG_FOLDER;
    public static final long LOG_FILE_EXPIRE_TIME = 1000 * 60 * 24 * 2;//最多保留两天
    public static final int LOG_FILE_MAX_SIZE = 512000;//每个log文件最大的大小500KB

    private static ChuckInterceptor.HttpLogReceiver httpLogReceiver;
    private static LogConfig.LogEnvProvider registerLogEnv;
    private static List<String> topicUploadList;

    public static void init(Context context, boolean debuggable, LogConfig logConfig) {

        LOG_FOLDER = context.getExternalFilesDir("logger")
                .getPath() + File.separator;

        if (logConfig != null) {
            httpLogReceiver = logConfig.getHttpLogReceiver();
            registerLogEnv = logConfig.getLogEnvProvider();
            topicUploadList = logConfig.getUploadTopic();
        }

        PrefLogHelper.init(context);

        OkhttpLogInterceptorProvider.init(context);
        OkhttpLogInterceptorProvider.setHttpLogReceiver(httpLogReceiver);

        AliYunLogEntry.init(context);

        LogTool.init(new LogPrintImpl.Builder().setLogOpen(debuggable)
                .setDiskFileMaxSize(LOG_FILE_MAX_SIZE).setDiskFilePath(LOG_FOLDER)
                .setOutputAdapter(new OutputAnyAdapter() {
                    @Override
                    public void log(LogBean logBean) {
                        if (!topicUploadList.contains(logBean.getTag())) {
                            return;
                        }
                        if (getNetLogSwitch()) {
                            return;
                        }
                        Log log = new Log();
                        if (registerLogEnv != null) {
                            Map<String, String> mapEnv = registerLogEnv.getLogEnv();
                            if (mapEnv != null) {
                                for (Map.Entry<String, String> entry : mapEnv.entrySet()) {
                                    log.PutContent(entry.getKey(), entry.getValue());
                                }
                            }
                        }
                        log.PutContent("message", logBean.getContent());
                        AliYunLogEntry.getInstance().addLog(logBean.getTag(), log);
                    }
                }));

        deleteExpireLogFile();//删除过期的日志文件
    }

    public static void saveNetLogSwitch(boolean isOpen) {
        PrefLogHelper.getInstance().putBoolean("netLog", isOpen);
    }

    public static boolean getNetLogSwitch() {
        return PrefLogHelper.getInstance().getBoolean("netLog", true);
    }

    public static void saveLogSwitch(boolean isOpen) {
        PrefLogHelper.getInstance().putBoolean("log", isOpen);
        LogTool.setLogToAndroid(isOpen);
        LogTool.setLogToDisk(isOpen);
    }

    public static boolean getLogSwitch() {
        return PrefLogHelper.getInstance().getBoolean("log", true);
    }

    public static void saveWindowLogShowStatus(boolean isOpen) {
        PrefLogHelper.getInstance().putBoolean("window_log", isOpen);
    }

    public static boolean getWindowLogShowStatus() {
        return PrefLogHelper.getInstance().getBoolean("window_log", false);
    }

    public static void setLogEntryShowSwitch(boolean isVisiable) {
        PrefLogHelper.getInstance().putBoolean("window_entry", isVisiable);
        saveLogSwitch(isVisiable);
        saveNetLogSwitch(isVisiable);
    }

    public static boolean getLogEntryShowStatus() {
        return PrefLogHelper.getInstance().getBoolean("window_entry", false);
    }


    public static List<File> getLogfileNameList() {
        List<File> fileList = FileUtils.listFilesInDir(LOG_FOLDER);
        return fileList;
    }


    //删除过期的log文件，最多保留三天
    public static void deleteExpireLogFile() {
        List<File> fileList = getLogfileNameList();
        long currentMis = System.currentTimeMillis();
        for (File file : fileList) {
            if (currentMis - file.lastModified() >= LOG_FILE_EXPIRE_TIME) {
                file.delete();
            }
        }
    }
}
