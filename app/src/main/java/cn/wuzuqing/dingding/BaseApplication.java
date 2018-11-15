package cn.wuzuqing.dingding;

import android.app.Application;
import android.content.Context;

import cn.wuzuqing.dingding.accessiblity.AccessibilityOperator;
import cn.wuzuqing.dingding.service.AlarmService;
import cn.wuzuqing.dingding.util.Util;
import cn.wuzuqing.dingding.util.youtu.StaticVal;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 10:01
 * @Description:
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 10:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseApplication extends Application {

    private static Context context;
    private static boolean needSetAlarm;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AccessibilityOperator.getInstance().init(this);
        Util.init();
        StaticVal.init();
        needSetAlarm = true;
    }

    public static Context getContext() {
        return context;
    }

    public static void setAlarm() {
        if (needSetAlarm) {
            needSetAlarm = false;
            AlarmService.set(context);
        }
    }
}
