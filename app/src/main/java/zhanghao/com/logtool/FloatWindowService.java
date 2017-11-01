package zhanghao.com.logtool;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zhanghao.log.LogBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhanghao.
 * 2017/11/1-10:05
 */

public class FloatWindowService extends Service {

    // 用于在线程中创建/移除/更新悬浮窗
    private Handler handler = new Handler();
    private Context context;
    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        context = this;
        if (timer == null) {
            timer = new Timer();
            // 每500毫秒就执行一次刷新任务
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
    }

    private class RefreshTask extends TimerTask {

        int time;

        @Override
        public void run() {
            // 当前界面没有悬浮窗显示，则创建悬浮
            if (!WindowManagerUtil.getInstance(context).isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        WindowManagerUtil.getInstance(context)
                                .createSmallWindow(context);
                    }
                });
            } else {
                if (WindowManagerUtil.getInstance(context).isFloatViewShowing()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            time++;
                            List<LogBean> logBeanList = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                LogBean logBean = new LogBean();
                                logBean.setTag("the log is show " + i * time);
                                logBeanList.add(logBean);
                            }
//                            WindowManagerUtil.getInstance(context).updateFloatView(logBeanList);
                        }
                    });
                } else {
                    time = 0;
                }
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
