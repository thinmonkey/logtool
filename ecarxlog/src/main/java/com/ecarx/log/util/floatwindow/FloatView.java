package com.ecarx.log.util.floatwindow;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ecarx.log.R;
import com.readystatesoftware.chuck.Chuck;

/**
 * Created by zhanghao.
 * 2017/11/1-10:21
 */

public class FloatView extends LinearLayout {

    public static final int TYPE_SHOW_BUTTON = 0;
    public static final int TYPE_SHOW_DETAIL = 1;
    private WindowManager.LayoutParams logWindowParams;
    private WindowManager.LayoutParams smallWindowParams;
    private ListView listView;

    private int currentStatus = TYPE_SHOW_BUTTON;
    private LayoutInflater layoutInflater;
    private WindowManager.LayoutParams currentWindowParam;

    private Button button;
    private View logView;
    private ImageView clearView;
    private OnRefreshDataListener onRefreshDataListener;

    public FloatView(Context context) {
        super(context);
        initView(context);
        setFitsSystemWindows(true);
    }

    private void initView(final Context context) {
        layoutInflater = LayoutInflater.from(context);

        initLogLayoutParams(context);
        initButtonLayoutParams(context);

        logView = layoutInflater.inflate(R.layout.float_layout_log, null);
        listView = (ListView) logView.findViewById(R.id.listview);
        clearView = (ImageView) logView.findViewById(R.id.iv_clear);
        clearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().clearShowLogModel();
            }
        });
        button = new Button(getContext());
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = TYPE_SHOW_DETAIL;
//                updateView();
//                if (onRefreshDataListener != null) {
//                    onRefreshDataListener.refreshData();
//                }
                context.startActivity(Chuck.getLaunchIntent(context));
            }
        });
        logView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = TYPE_SHOW_BUTTON;
                updateView();
            }
        });
        currentWindowParam = smallWindowParams;
        currentStatus = TYPE_SHOW_BUTTON;
        addButtonView();
    }

    public void setRefreshListener(OnRefreshDataListener onRefreshDataListener) {
        this.onRefreshDataListener = onRefreshDataListener;
    }

    private void initButtonLayoutParams(Context context) {
        smallWindowParams = new WindowManager.LayoutParams();
        // 设置显示类型为phone
        smallWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 显示图片格式
        smallWindowParams.format = PixelFormat.TRANSPARENT;
        // 设置交互模式
        smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置对齐方式为左上
        smallWindowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        smallWindowParams.width = FloatView.getScreenWidth(context) / 9;
        smallWindowParams.height = FloatView.getScreenWidth(context) / 9;
        smallWindowParams.x = 0;
        smallWindowParams.y = getNavegateBatHeight(context);
    }

    private void initLogLayoutParams(Context context) {
        logWindowParams = new WindowManager.LayoutParams();
        // 设置显示的位置，默认的是屏幕中心
        logWindowParams.x = 0;
        logWindowParams.y = 0;
        logWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        logWindowParams.format = PixelFormat.TRANSPARENT;

        // 设置交互模式
        logWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE;
        logWindowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        logWindowParams.width = getScreenWidth(context);
        logWindowParams.height = getScreenHeight(context) - getNavegateBatHeight(context);
    }

    private void addButtonView() {
        addView(button);
    }

    public void setAdapter(BaseAdapter baseAdapter) {
        listView.setAdapter(baseAdapter);
    }

    public WindowManager.LayoutParams getCurrentWindowParam() {
        return currentWindowParam;
    }


    public void updateView() {
        if (currentStatus == TYPE_SHOW_BUTTON) {
            currentWindowParam = smallWindowParams;
            removeView(logView);
            addButtonView();
        } else {
            removeView(button);
            currentWindowParam = logWindowParams;
            addView(logView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.MATCH_PARENT));
        }
        ((WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE)).updateViewLayout(this,
                currentWindowParam);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public interface OnRefreshDataListener {
        void refreshData();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getWidth();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        return ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getHeight();
    }

    public static int getNavegateBatHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


}
