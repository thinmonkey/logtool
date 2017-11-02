package com.zhanghao.log;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao.
 * 2017/10/8-14:15
 */
public class PretteyFormatStrategy implements FormatStrategy {

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 1000;

    /**
     * Drawing toolbox
     */

    private static final char MIDDLE_CORNER = '|';
    private static final char HORIZONTAL_LINE = '|';
    private static final String DOUBLE_DIVIDER =
            "-------------------------------------------------------------------------------------------";
    private static final String SINGLE_DIVIDER = "-------------------------------------------------------------------------------------------";
    private static final String TOP_BORDER = DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private final int methodCount;
    private final boolean showThreadInfo;
    private final String tag;

    private PretteyFormatStrategy(Builder builder) {
        methodCount = builder.methodCount;
        showThreadInfo = builder.showThreadInfo;
        tag = builder.tag;
    }

    @Override
    public LogBean covertMessage(LogBean logBean) {
        if (TextUtils.isEmpty(logBean.getTag())) {
            logBean.setTag(tag);
        }
        logBean.setWrapContent(createOutputContent(logBean));
        return logBean;
    }


    private ArrayList<String> createOutputContent(LogBean logBean) {
        List<String> outPutLogList = new ArrayList<>();
        outPutLogList.add(TOP_BORDER);
        //?????????
        if (showThreadInfo) {
            outPutLogList.add(HORIZONTAL_LINE + " Thread: " + Thread.currentThread().getName());
            outPutLogList.add(MIDDLE_BORDER);
        }
        String level = "";

        //corresponding method count with the current stack may exceeds the stack trace. Trims
        // the count
        //????????
        if (methodCount > 0) {
            StackTraceElement[] trace = logBean.getStackTraceElement();
            for (int i = methodCount; i > 0; i--) {
                int stackIndex = i + 3;
                if (stackIndex >= trace.length) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                builder.append(HORIZONTAL_LINE)
                        .append(' ')
                        .append(level)
                        .append(getSimpleClassName(trace[stackIndex].getClassName()))
                        .append(".")
                        .append(trace[stackIndex].getMethodName())
                        .append(" ")
                        .append(" (")
                        .append(trace[stackIndex].getFileName())
                        .append(":")
                        .append(trace[stackIndex].getLineNumber())
                        .append(")");
                level += "   ";
                outPutLogList.add(builder.toString());
            }
            outPutLogList.add(MIDDLE_BORDER);
        }
        //get bytes of message with system's default charset (which is UTF-8 for Android)
        //???????
        String sourceContent = logBean.getContent();
        byte[] bytes = sourceContent.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            generateMessage(sourceContent, outPutLogList);
        } else {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                //create a new String with system's default charset (which is UTF-8 for Android)
                generateMessage(new String(bytes, i, count), outPutLogList);
            }
        }
        outPutLogList.add(BOTTOM_BORDER);
        return (ArrayList<String>) outPutLogList;
    }

    private void generateMessage(String message, List<String> outPutLog) {
        String[] lines = message.split(System.getProperty("line.separator"));
        for (String line : lines) {
            outPutLog.add(HORIZONTAL_LINE + " " + line);
        }
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    public static class Builder {
        int methodCount = 2;
        boolean showThreadInfo = true;
        String tag = "PRETTY_LOGGER";

        public Builder() {
        }

        public Builder methodCount(int val) {
            methodCount = val;
            return this;
        }

        public Builder showThreadInfo(boolean val) {
            showThreadInfo = val;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public PretteyFormatStrategy build() {
            return new PretteyFormatStrategy(this);
        }
    }

}
