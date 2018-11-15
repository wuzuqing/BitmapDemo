package cn.wuzuqing.dingding.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import cn.wuzuqing.dingding.BaseApplication;
import cn.wuzuqing.dingding.R;
import cn.wuzuqing.dingding.receiver.ScreenReceiver;
import cn.wuzuqing.dingding.util.LogUtils;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 9:42
 * @Description: java类作用描述
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 9:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StartService extends Service {

    private ScreenReceiver screenReceiver;
    private Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenReceiver = new ScreenReceiver();
        IntentFilter screenIntentFilter = new IntentFilter();
        screenIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver, screenIntentFilter);
        LogUtils.logd("StartService.onCreate");
        handler = new Handler(Looper.getMainLooper());
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LogUtils.logd("StartService isRunning");
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启用前台服务，主要是startForeground()
        startForeground(1, new Notification());
        BaseApplication.setAlarm();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 300000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(screenReceiver);
        handler.removeCallbacks(runnable);
        handler = null;
        LogUtils.logd("StartService.onDestroy");
    }

    public static void startServer(Context ctx) {
        ctx.startService(new Intent(ctx, StartService.class));
    }
}
