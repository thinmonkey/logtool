package zhanghao.com.logtool;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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

    public FloatView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        layoutInflater = LayoutInflater.from(context);

        initLogLayoutParams(context);

        initButtonLayoutParams(context);

        logView = layoutInflater.inflate(R.layout.layout_log, null);
        listView = (ListView) logView.findViewById(R.id.listview);
        button = new Button(getContext());
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = TYPE_SHOW_DETAIL;
                updateView();
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

    private void initButtonLayoutParams(Context context) {
        smallWindowParams = new WindowManager.LayoutParams();
        // 设置显示类型为phone
        smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 显示图片格式
        smallWindowParams.format = PixelFormat.RGBA_8888;
        // 设置交互模式
        smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        // 设置对齐方式为左上
        smallWindowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        smallWindowParams.width = FloatView.getScreenWidth(context) / 10;
        smallWindowParams.height = FloatView.getScreenWidth(context) / 10;
        smallWindowParams.x = 0;
        smallWindowParams.y = 0;
    }

    private void initLogLayoutParams(Context context) {
        logWindowParams = new WindowManager.LayoutParams();
        // 设置显示的位置，默认的是屏幕中心
        logWindowParams.x = 0;
        logWindowParams.y = 0;
        logWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        logWindowParams.format = PixelFormat.RGBA_8888;

        // 设置交互模式
        logWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        logWindowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        logWindowParams.width = getScreenWidth(context);
        logWindowParams.height = getScreenHeight(context);
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (currentStatus == TYPE_SHOW_DETAIL) {
                currentStatus = TYPE_SHOW_BUTTON;
                updateView();
                return true;
            }
            return super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
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


}
