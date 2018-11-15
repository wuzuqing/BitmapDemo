package cn.wuzuqing.dingding.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import cn.wuzuqing.dingding.InitPointRunnable;
import cn.wuzuqing.dingding.R;
import cn.wuzuqing.dingding.ThreadManager;
import cn.wuzuqing.dingding.util.Util;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 13:36
 * @Description: java类作用描述
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 13:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class InitService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initPointRunnable = new InitPointRunnable();
        createTouchView();
    }

    private int halfViewSize;
    private View toucherLayout;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    //状态栏高度.
    private int statusBarHeight = -1;

    private static final String TAG = "InitService";
    private InitPointRunnable initPointRunnable;

    private void createTouchView() {
        //赋值WindowManager&LayoutParam.
        halfViewSize = 120 / 2;
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        //设置悬浮窗口长宽数据.
        params.width = -2;
        params.height = -2;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局.
        toucherLayout = inflater.inflate(R.layout.window_init, null);
        //添加toucherlayout
        windowManager.addView(toucherLayout, params);


        //主动计算出当前View的宽高信息.
        toucherLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        toucherLayout.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isInit) {
                    Util.isInit = true;
                    ThreadManager.get().execute(initPointRunnable);
                }
            }
        });

        toucherLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        params.x = (int) event.getRawX() - halfViewSize;
                        params.y = (int) event.getRawY() - halfViewSize - statusBarHeight;
                        windowManager.updateViewLayout(toucherLayout, params);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    default:
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(toucherLayout);
        windowManager = null;
    }
}
