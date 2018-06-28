package com.zhanghao.log;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhanghao.
 * 2017/10/8-17:42
 */

public class DiskWriteUtil {

    private final Handler handler;

    public DiskWriteUtil(String folder, int maxFileSize) {
        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        this.handler = new WriteHandler(ht.getLooper(), folder, maxFileSize);
    }

    public void log(int level, String message) {
        this.handler.sendMessage(this.handler.obtainMessage(level, message));
    }

    public static class WriteHandler extends Handler {
        private String folder;
        private int maxFileSize;

        public WriteHandler(Looper looper, String folder, int maxFileSize) {
            super(looper);
            this.folder = folder;
            this.maxFileSize = maxFileSize;
        }

        public void handleMessage(Message msg) {
            String content = (String) msg.obj;
            FileWriter fileWriter = null;
            File logFile = getWritableLogFile();
            if (logFile == null) {
                Log.e(DiskWriteUtil.class.getName(), "file path not found");
                return;
            }
            try {
                fileWriter = new FileWriter(logFile, true);
                this.writeLog(fileWriter, content);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException var8) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException var7) {
                        var7.printStackTrace();
                    }
                }
            }
        }

        private void writeLog(FileWriter fileWriter, String content) throws IOException {
            fileWriter.append(content);
        }

        public File getWritableLogFile() {
            if (folder == null) {
                Log.e(DiskWriteUtil.class.getName(), "folder is null");
                return null;
            }
            List<File> fileList = listFilesInDir(new File(folder));
            if (fileList == null) {
                Log.e(DiskWriteUtil.class.getName(), "folder is not Directory");
                return null;
            }
            for (File file : fileList) {
                if (file.length() < maxFileSize) {
                    return file;
                }
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
            File newFile = new File(folder, "log_" + dateFormat.format(new Date()) + ".csv");
            return newFile;
        }
    }

    public static List<File> listFilesInDir(File file) {
        if (!file.isDirectory()) {
            return null;
        } else {
            ArrayList var1 = new ArrayList();
            File[] var2 = file.listFiles();
            if (var2 != null && var2.length != 0) {
                File[] var3 = var2;
                int var4 = var2.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    File var6 = var3[var5];
                    var1.add(var6);
                    if (var6.isDirectory()) {
                        List var7 = listFilesInDir(var6);
                        if (var7 != null) {
                            var1.addAll(var7);
                        }
                    }
                }
            }

            return var1;
        }
    }
}
