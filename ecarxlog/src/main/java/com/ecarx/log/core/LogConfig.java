package com.ecarx.log.core;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogConfig {

    private ChuckInterceptor.HttpLogReceiver httpLogReceiver;
    private LogEnvProvider logEnvProvider;
    private List<String> uploadTopic = new ArrayList<>();

    {
        uploadTopic.add(Tag.TAG_HTTP);
        uploadTopic.add(Tag.TAG_JPUSH);
    }

    private LogConfig(ChuckInterceptor.HttpLogReceiver httpLogReceiver, LogEnvProvider registerLogEnv) {
        this.httpLogReceiver = httpLogReceiver;
        this.logEnvProvider = registerLogEnv;
    }

    public ChuckInterceptor.HttpLogReceiver getHttpLogReceiver() {
        return httpLogReceiver;
    }

    public void setHttpLogReceiver(ChuckInterceptor.HttpLogReceiver httpLogReceiver) {
        this.httpLogReceiver = httpLogReceiver;
    }

    public LogEnvProvider getLogEnvProvider() {
        return logEnvProvider;
    }

    public void setLogEnvProvider(LogEnvProvider logEnvProvider) {
        this.logEnvProvider = logEnvProvider;
    }

    public List<String> getUploadTopic() {
        return uploadTopic;
    }

    public void setUploadTopic(List<String> uploadTopic) {
        this.uploadTopic = uploadTopic;
    }

    public static class Builder {

        private ChuckInterceptor.HttpLogReceiver httpLogReceiver;
        private LogEnvProvider logEnvProvider;
        private List<String> topicList;

        public Builder() {
        }

        public Builder setHttpLogReceiver(ChuckInterceptor.HttpLogReceiver httpLogReceiver) {
            this.httpLogReceiver = httpLogReceiver;
            return this;
        }

        public Builder setLogEnvProvider(LogEnvProvider logEnvProvider) {
            this.logEnvProvider = logEnvProvider;
            return this;
        }

        public Builder setUploadTopicList(List<String> topicList) {
            this.topicList = topicList;
            return this;
        }

        public LogConfig builder() {
            LogConfig logConfig = new LogConfig(httpLogReceiver, logEnvProvider);
            logConfig.setUploadTopic(this.topicList);
            return logConfig;
        }
    }

    public static interface LogEnvProvider {
        Map<String, String> getLogEnv();
    }

}
