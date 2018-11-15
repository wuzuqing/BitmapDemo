package cn.wuzuqing.dingding.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cn.wuzuqing.dingding.service.DingDingService;
import cn.wuzuqing.dingding.service.StartService;
import cn.wuzuqing.dingding.util.LogUtils;


/**
 * @author: 吴祖清
 * @createDate: 2018/11/6 11:28
 * @description: java类作用描述
 * @updateUser: 吴祖清
 * @updateDate: 2018/11/6 11:28
 * @updateRemark: 更新说明
 * @version: 1.0
 */
public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        StartService.startServer(context);
    }
}
