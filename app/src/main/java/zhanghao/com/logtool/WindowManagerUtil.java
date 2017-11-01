package zhanghao.com.logtool;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.zhanghao.log.LogBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao.
 * 2017/11/1-10:05
 */

public class WindowManagerUtil {

    private Button smallWindow;
    private WindowManager.LayoutParams smallWindowParams;
    // 大悬浮窗对象
    private FloatView bigWindow;
    // 用于控制在屏幕上添加或移除悬浮窗
    private WindowManager mWindowManager;
    // WindowManagerUtil的单例
    private static WindowManagerUtil WindowManagerUtil;
    // 上下文对象
    private Context context;

    private BaseAdapter baseAdapter;
    private ArrayList<LogBean> logBeans = new ArrayList<>();

    private WindowManagerUtil(Context context) {
        this.context = context;

        smallWindowParams = new WindowManager.LayoutParams();
        // 设置显示类型为phone
        smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 显示图片格式
        smallWindowParams.format = PixelFormat.RGBA_8888;
        // 设置交互模式
        smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置对齐方式为左上
        smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
        smallWindowParams.width = FloatView.getScreenWidth(context) / 10;
        smallWindowParams.height = FloatView.getScreenWidth(context) / 10;
        smallWindowParams.x = FloatView.getScreenWidth(context);
        smallWindowParams.y = FloatView.getScreenHeight(context) / 2;


        for (int i = 0; i < 20; i++) {
            LogBean logBean = new LogBean();
            logBean.setTag("the log is show " + i);
            logBeans.add(logBean);
        }

    }

    public static WindowManagerUtil getInstance(Context context) {

        if (WindowManagerUtil == null) {
            WindowManagerUtil = new WindowManagerUtil(context);
        }
        return WindowManagerUtil;
    }

    /**
     * 创建小悬浮窗
     *
     * @param context 必须为应用程序的Context.
     */
    public void createSmallWindow(final Context context) {
       /*

        if (smallWindow == null) {
            smallWindow = new Button(context);
            smallWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeSmallWindow();
                    createBigWindow(context);
                }
            });
            windowManager.addView(smallWindow, smallWindowParams);
        }*/
        if (bigWindow == null) {
            bigWindow = new FloatView(context);
            baseAdapter = new LogAdapter(context, logBeans);
            bigWindow.setAdapter(baseAdapter);
            WindowManager windowManager = getWindowManager();
            windowManager.addView(bigWindow, bigWindow.getCurrentWindowParam());
        }

    }

    /**
     * 将小悬浮窗从屏幕上移除
     *
     * @param
     */
    public void removeSmallWindow() {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager();
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 创建大悬浮窗
     *
     * @param context 必须为应用程序的Context.
     */
    public void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager();
        if (bigWindow == null) {
            bigWindow = new FloatView(context);
            baseAdapter = new LogAdapter(context, logBeans);
            bigWindow.setAdapter(baseAdapter);
//            windowManager.addView(bigWindow, bigWindow.getLogWindowParams());
        }
    }

    public void updateFloatView(List<LogBean> logBeanList) {
        if (logBeans.size() > 10) {
            logBeans.clear();
        }
        logBeans.addAll(logBeanList);
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 将大悬浮窗从屏幕上移除
     *
     * @param
     */
    public void removeBigWindow() {
        if (bigWindow != null) {
            WindowManager windowManager = getWindowManager();
            windowManager.removeView(bigWindow);
            bigWindow = null;
        }
    }

    public void removeAll() {
        context.stopService(new Intent(context, FloatWindowService.class));
        removeSmallWindow();
        removeBigWindow();
    }

    /**
     * 是否有悬浮窗显示(包括小悬浮窗和大悬浮)
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false
     */
    public boolean isWindowShowing() {
        return smallWindow != null || bigWindow != null;
    }

    public boolean isFloatViewShowing() {
        return bigWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建新的WindowManager返回。否则返回当前已创建的WindowManager
     *
     * @param
     * @return
     */
    private WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
