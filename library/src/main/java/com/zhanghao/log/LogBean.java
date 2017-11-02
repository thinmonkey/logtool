package com.zhanghao.log;

import java.util.ArrayList;

/**
 * Created by zhanghao.
 * 2017/10/8-13:46
 */

public class LogBean implements Cloneable {

    private long time;
    private String tag;
    private String keyword;
    private int priority;
    private String content;
    private ArrayList<String> wrapContent;
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

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public ArrayList<String> getWrapContent() {
        return wrapContent;
    }

    public void setWrapContent(ArrayList<String> wrapContent) {
        this.wrapContent = wrapContent;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            LogBean logBean = (LogBean) super.clone();
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


