package cn.wuzuqing.dingding.accessiblity;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import cn.wuzuqing.dingding.util.LogUtils;


/**
 * Created by popfisher on 2017/7/6.
 */

@TargetApi(16)
public class AccessibilitySampleService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // 通过代码可以动态配置，但是可配置项少一点
//        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
//        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
//                | AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
//        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
//        accessibilityServiceInfo.notificationTimeout = 0;
//        accessibilityServiceInfo.flags = AccessibilityServiceInfo.DEFAULT;
//        setServiceInfo(accessibilityServiceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的
        // 获取包名
        String pkgName = event.getPackageName().toString();
        AccessibilityOperator.getInstance().updateEvent(this, event);
        int eventType = event.getEventType();
        switch (eventType) {
            //收到通知栏消息
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            //界面状态改变
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
            //点击事件
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                break;
            //文本改变
            case AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT:
                break;
            //省略其他的一堆可以监听的事件
            default:
        }

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }
}
