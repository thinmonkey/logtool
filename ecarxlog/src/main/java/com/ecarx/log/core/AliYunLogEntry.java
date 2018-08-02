package com.ecarx.log.core;

import android.content.Context;


import com.aliyun.sls.android.sdk.ClientConfiguration;
import com.aliyun.sls.android.sdk.LOGClient;
import com.aliyun.sls.android.sdk.LogException;
import com.aliyun.sls.android.sdk.SLSLog;
import com.aliyun.sls.android.sdk.core.auth.PlainTextAKSKCredentialProvider;
import com.aliyun.sls.android.sdk.core.callback.CompletedCallback;
import com.aliyun.sls.android.sdk.model.Log;
import com.aliyun.sls.android.sdk.model.LogGroup;
import com.aliyun.sls.android.sdk.request.PostLogRequest;
import com.aliyun.sls.android.sdk.result.PostLogResult;
import com.ecarx.log.BuildConfig;


public class AliYunLogEntry {

    private static LOGClient logClient;

    private static Context context;

    private static AliYunLogEntry aliYunLogUtil;

    private AliYunLogEntry() {

    }

    public static AliYunLogEntry getInstance() {
        if (context == null) {
            throw new RuntimeException("AliYunLogUtil must init with context");
        }
        if (aliYunLogUtil == null) {
            aliYunLogUtil = new AliYunLogEntry();
        }
        return aliYunLogUtil;
    }

    public static void init(Context context) {
        AliYunLogEntry.context = context.getApplicationContext();
        String AK = BuildConfig.ACCESSKEYID;
        String SK = BuildConfig.ACCESSKEYSECRET;
        PlainTextAKSKCredentialProvider credentialProvider =
                new PlainTextAKSKCredentialProvider(AK, SK);
//        STS使用方式
//        String STS_AK = "******";
//        String STS_SK = "******";
//        String STS_TOKEN = "******";
//        StsTokenCredentialProvider credentialProvider =
//                new StsTokenCredentialProvider(STS_AK, STS_SK, STS_TOKEN);


        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        conf.setCachable(true);
        conf.setConnectType(ClientConfiguration.NetworkPolicy.WWAN_OR_WIFI);
        SLSLog.enableLog(); // log打印在控制台

        logClient = new LOGClient(context.getApplicationContext(), BuildConfig.ENDPOINT, credentialProvider, conf);
    }

    public void addLog(String topic, Log log) {
        LogGroup logGroup = new LogGroup(topic, BuildConfig.SOURCE);
        logGroup.PutLog(log);
        PostLogRequest request = new PostLogRequest(BuildConfig.PROJECTNAME, BuildConfig.LOGSTORE, logGroup);
        try {
            logClient.asyncPostLog(request, new CompletedCallback<PostLogRequest, PostLogResult>() {
                @Override
                public void onSuccess(PostLogRequest request, PostLogResult result) {
                    System.gc();
                }

                @Override
                public void onFailure(PostLogRequest request, LogException exception) {
                    System.gc();
                }
            });
        } catch (LogException e) {
            e.printStackTrace();
        }

    }


}
