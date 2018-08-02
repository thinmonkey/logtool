package com.ecarx.log.util.floatwindow;

/**
 * Created by zhanghao.
 * 2017/11/2-13:38
 */

public class LogShowModel {

    private String tag;
    private String title;
    private String detail;
    private long time;
    private boolean isShowDetail;
    private boolean isAlreadyRead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isShowDetail() {
        return isShowDetail;
    }

    public void setShowDetail(boolean showDetail) {
        isShowDetail = showDetail;
    }

    public boolean isAlreadyRead() {
        return isAlreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        isAlreadyRead = alreadyRead;
    }
}
