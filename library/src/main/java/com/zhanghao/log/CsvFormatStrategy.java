package com.zhanghao.log;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhanghao.
 * 2017/10/8-14:14
 */

public class CsvFormatStrategy implements FormatStrategy {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NEW_LINE_REPLACEMENT = " <br> ";
    private static final String SEPARATOR = ",";
    private final Date date;
    private final SimpleDateFormat dateFormat;
    private final String tag;

    private CsvFormatStrategy(Builder builder) {
        this.date = builder.date;
        this.dateFormat = builder.dateFormat;
        this.tag = builder.tag;
    }

    public static CsvFormatStrategy.Builder newBuilder() {
        return new CsvFormatStrategy.Builder();
    }

    private String formatTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return tag;
        }
        return this.tag;
    }

    public static final class Builder {
        Date date;
        SimpleDateFormat dateFormat;
        String tag;

        private Builder() {
            this.tag = "gnetlink_log";
        }

        public Builder date(Date val) {
            this.date = val;
            return this;
        }

        public Builder dateFormat(SimpleDateFormat val) {
            this.dateFormat = val;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public CsvFormatStrategy build() {
            if (this.date == null) {
                this.date = new Date();
            }

            if (this.dateFormat == null) {
                this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.CHINA);
            }

            return new CsvFormatStrategy(this);
        }
    }

    @Override
    public LogBean covertMessage(LogBean logBean) {
        String tag = this.formatTag(logBean.getTag());
        this.date.setTime(System.currentTimeMillis());
        String message = logBean.getContent();
        StringBuilder builder = new StringBuilder();
        builder.append(Long.toString(this.date.getTime()));
        builder.append(SEPARATOR);
        builder.append(this.dateFormat.format(this.date));
        builder.append(SEPARATOR);
        builder.append(logBean.getPriority());
        builder.append(SEPARATOR);
        builder.append(tag);
        if (message.contains(NEW_LINE)) {
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }

        builder.append(SEPARATOR);
        builder.append(message);
        builder.append(NEW_LINE);
        logBean.setContent(builder.toString());
        return logBean;
    }
}
