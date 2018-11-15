package cn.wuzuqing.dingding.service;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

import java.util.Calendar;
import java.util.List;

import cn.wuzuqing.dingding.CapGrayBitmap;
import cn.wuzuqing.dingding.MainActivity;
import cn.wuzuqing.dingding.ThreadManager;
import cn.wuzuqing.dingding.constant.AppConstant;
import cn.wuzuqing.dingding.model.PointModel;
import cn.wuzuqing.dingding.model.Result;
import cn.wuzuqing.dingding.util.AutoTool;
import cn.wuzuqing.dingding.util.LaunchApp;
import cn.wuzuqing.dingding.util.LogUtils;
import cn.wuzuqing.dingding.util.SuUtil;
import cn.wuzuqing.dingding.util.Util;
import cn.wuzuqing.dingding.util.youtu.ImageParse;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/4/23 23:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/4/23$
 * @updateDes ${TODO}
 */

public class DingDingService extends Service {
    private CapGrayBitmap grayBitmap;

    @Override
    public void onCreate() {
        super.onCreate();
        grayBitmap = new CapGrayBitmap();
        workPointModel = Util.get(AppConstant.HOME_WORK);
        kaoQinPointModel = Util.get(AppConstant.HOME_WORK_KAOQIN);
        loginPwdPointModel = Util.get(AppConstant.LOGIN_PWD);
        loginBtnPointModel = Util.get(AppConstant.LOGIN_LOGIN);
        midWidth = getResources().getDisplayMetrics().widthPixels / 2;
    }

    private float midWidth;
    private Calendar mCalendar;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //唤醒屏幕
        wakeUpAndUnlock(getApplicationContext());
        if (intent != null) {
            mCalendar = (Calendar) intent.getSerializableExtra("calendar");
        }
        //执行登录打卡
        ThreadManager.get().execute(dakaRunnable);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 唤醒屏幕并解锁权限
     * <uses-permission android:name="android.permission.WAKE_LOCK" />
     */
    @SuppressLint("Wakelock")
    @SuppressWarnings("deprecation")
    public static void wakeUpAndUnlock(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        // 点亮屏幕
        wl.acquire();
        // 释放
        wl.release();
        // 得到键盘锁管理器对象
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        // 解锁
        kl.disableKeyguard();
    }


    private PointModel workPointModel;
    private PointModel kaoQinPointModel;
    private PointModel loginPwdPointModel;
    private PointModel loginBtnPointModel;
    private int hour = 0;
    private Runnable dakaRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                int count = 0;
                SuUtil.kill();
                Thread.sleep(1000L);
                LaunchApp.launchapp(getApplicationContext());
                Thread.sleep(8000L);
                // 是否需要登录
                final String pwd = "wzq,./1991";

                grayBitmap.execute(false, null);
                List<Result.ItemsBean> result = ImageParse.getSyncData();
                Result.ItemsBean bean;

                if (mCalendar != null) {
                    hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                }
                boolean needCheckWork = false;
                for (int i = 0; i < result.size(); i++) {
                    bean = result.get(i);
                    if ("工作".equals(bean.getItemstring()) && bean.getItemcoord().getX() > 500 && bean.getItemcoord().getX() < 515) {
                        AutoTool.execShellCmd(workPointModel);
                    } else if (bean.getItemstring().contains("输入密码")) {
                        loginPwdPointModel = new PointModel("密码", "#060606", bean.getItemcoord().getX() + 10, bean.getItemcoord().getY() + 10);
                        if (i + 1 < result.size()) {
                            bean = result.get(i + 1);
                            if (bean.getItemstring().contains("登陆")) {
                                loginBtnPointModel = new PointModel("登陆", "#2B2825", bean.getItemcoord().getX() + 10, bean.getItemcoord().getY() + 10);
                            }
                        }
                        doLogin(pwd);
                        needCheckWork = true;
                        break;
                    }
                }

                while (needCheckWork) {
                    grayBitmap.execute(false, null);
                    if (Util.checkColor(workPointModel)) {
                        AutoTool.execShellCmd(workPointModel);
                        break;
                    } else if (count >= 20) {
                        SuUtil.kill(LaunchApp.DING_DING);
                        return;
                    }
                    count++;
                }
                count = 0;
                Thread.sleep(2000L);
                while (true) {
                    grayBitmap.execute(false, null);
                    if (Util.checkColor(kaoQinPointModel)) {
                        AutoTool.execShellCmd(kaoQinPointModel);
                        break;
                    } else if (count >= 20) {
                        SuUtil.kill(LaunchApp.DING_DING);
                        return;
                    }
                    count++;
                }
                Thread.sleep(8000L);
                capCount = 0;
                if (hour > 12) {
                    AutoTool.execShellSwipe(1200, 600, false);
                    Thread.sleep(800L);
                }
                cap(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void doLogin(String pwd) throws InterruptedException {
        AutoTool.execShellCmd(loginPwdPointModel);
        Thread.sleep(2000);
        AutoTool.execShellInput(pwd);
        Thread.sleep(2000);
        AutoTool.execShellCmd(1, 4);
        Thread.sleep(2000);
        AutoTool.execShellCmd(loginBtnPointModel);
        Thread.sleep(3000L);
    }

    private int capCount;

    private boolean isDkPos(Result.ItemsBean bean) {
        return (bean.getItemcoord().getX() + bean.getItemcoord().getWidth()) > midWidth;
    }

    private void cap(final boolean reStart) {

        grayBitmap.execute(false, null);
        List<Result.ItemsBean> result = ImageParse.getSyncData();
        if (result != null && result.size() > 0) {
            for (Result.ItemsBean bean : result) {
                if (bean.getItemstring().contains("上班打卡")) {
                    if (isDkPos(bean)) {
                        if (hour == 9) {
                            click(bean);
                        } else {
                            LogUtils.logd("还没到上班打卡时间");
                            exit();
                        }
                        return;
                    }
                } else if (bean.getItemstring().contains("下班打卡")) {
                    if (isDkPos(bean)) {
                        if (hour >= 19) {
                            click(bean);
                        } else {
                            LogUtils.logd("还没到下班打卡时间");
                            exit();
                        }
                        return;
                    }
                }
            }
        }
        capCount++;
        if (reStart) {
            cap(capCount < 3);
        }
    }

    private void exit() {
        try {
            Thread.sleep(5000L);
            SuUtil.kill(LaunchApp.DING_DING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean click(Result.ItemsBean bean) {
        LogUtils.logd("bean:" + bean.toString());
        //打卡成功
        AutoTool.execShellTapXy(bean.getItemcoord().getX(), bean.getItemcoord().getY());
        AlarmService.set(getApplicationContext());
        exit();
        return true;
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DingDingService.class);
        intent.putExtra("calendar", Calendar.getInstance());
        context.startService(intent);
    }
}