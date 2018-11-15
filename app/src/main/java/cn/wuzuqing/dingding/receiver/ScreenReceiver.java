package cn.wuzuqing.dingding.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.wuzuqing.dingding.BaseApplication;
import cn.wuzuqing.dingding.MainActivity;
import cn.wuzuqing.dingding.service.AlarmService;
import cn.wuzuqing.dingding.util.KeepLiveManager;
import cn.wuzuqing.dingding.util.LogUtils;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 9:37
 * @Description: java类作用描述
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 9:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                BaseApplication.setAlarm();
                KeepLiveManager.getInstance().finish();
                break;
            case Intent.ACTION_SCREEN_OFF:
                KeepLiveManager.getInstance().startKeepLiveAct(context);
                break;
            default:
        }
        LogUtils.logd(intent.getAction());
    }
}
