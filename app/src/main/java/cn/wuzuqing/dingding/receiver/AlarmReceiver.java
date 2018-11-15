package cn.wuzuqing.dingding.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.wuzuqing.dingding.service.DingDingService;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/4/23 23:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/4/23$
 * @updateDes ${TODO}
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String action = "com.g.android.RING";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (action.equals(intent.getAction())) {
            Intent intent1 = new Intent(context, DingDingService.class);
            context.startService(intent1);
        }
    }
}
