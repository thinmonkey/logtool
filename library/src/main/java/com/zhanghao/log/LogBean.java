package com.zhanghao.log;

import java.util.ArrayList;

/**
 * Created by zhanghao.
 * 2017/10/8-13:46
 */

public class LogBean implements Cloneable {

    private long time;
    private String tag;
    private int priority;
    private String sourceContent;
    private ArrayList<String> outputContent = new ArrayList<>();
    private StackTraceElement[] stackTraceElement;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    protected String getSourceContent() {
        return sourceContent;
    }

    protected void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
        if (!outputContent.contains(sourceContent)) {
            outputContent.add(sourceContent);
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    public void setStackTraceElement(StackTraceElement[] stackTraceElement) {
        this.stackTraceElement = stackTraceElement;
    }

    public ArrayList<String> getOutputContent() {
        return outputContent;
    }

    public void setOutputContent(ArrayList<String> outputContent) {
        this.outputContent = outputContent;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            LogBean logBean = (LogBean) super.clone();
            logBean.outputContent = (ArrayList<String>) outputContent.clone();
            if (this.stackTraceElement != null) {
                logBean.stackTraceElement = stackTraceElement.clone();
            }
            return logBean;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return super.clone();
    }
}


