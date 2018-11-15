package cn.wuzuqing.dingding.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

import cn.wuzuqing.dingding.KeepLiveActivity;

/**
 * @author: 吴祖清
 * @createDate: 2018/11/6 14:32
 * @description: java类作用描述
 * @updateUser: 吴祖清
 * @updateDate: 2018/11/6 14:32
 * @updateRemark: 更新说明
 * @version: 1.0
 */
public class KeepLiveManager {
    private static KeepLiveManager instance = new KeepLiveManager();
    private WeakReference<Activity> weakReference;

    public static KeepLiveManager getInstance() {
        return instance;
    }

    public void setAct(Activity act) {
        if (act != null) {
            weakReference = new WeakReference<>(act);
        }
    }

    public void startKeepLiveAct(Context context) {
        Intent intent = new Intent(context, KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finish() {
        if (weakReference != null && weakReference.get() != null) {
            weakReference.get().finish();
            weakReference.clear();
            weakReference = null;
        }
    }
}
