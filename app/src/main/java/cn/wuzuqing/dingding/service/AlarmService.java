package cn.wuzuqing.dingding.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.wuzuqing.dingding.util.LogUtils;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/4/23 23:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/4/23$
 * @updateDes ${TODO}
 */

public class AlarmService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar mCalendar = (Calendar) intent.getSerializableExtra("calendar");
        int noteId = intent.getIntExtra("noteId", 0);

        //设置广播
        Intent intent2 = new Intent();
        intent2.setAction("com.g.android.RING");
        intent2.putExtra("calendar",mCalendar);
        intent2.putExtra("noteId", noteId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent2, 0);

        //根据不同的版本使用不同的设置方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
        } else {
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
        }
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(mCalendar.getTimeInMillis()));
        LogUtils.logd("闹钟设置成功:" + format);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void set(Context context) {
        Calendar newCal = getNewCalendar();
        if (newCal != null) {
            Intent intent = new Intent(context, AlarmService.class);
            intent.putExtra("calendar", newCal);
            intent.putExtra("noteId", 10);
            context.startService(intent);
        }
    }

    public static Calendar getNewCalendar() {
        Calendar calendar = Calendar.getInstance();

        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        //中下班午的时间
        int newHour = 0, newMinute = 0, newDay = 0;
        if (nowHour < 9) {
            newHour = 9;
            newMinute = 2;
        } else if (nowHour >= 10 && nowHour < 19 ) {
            newHour = 19;
            newMinute = 02;

        } else if (nowHour >= 19) {
            newHour = 9;
            newMinute = 20;
            newDay = 1;
        }

        calendar.set(Calendar.HOUR_OF_DAY, newHour);
        calendar.set(Calendar.MINUTE, newMinute);
        if (newDay > 0) {
            return null;
//            calendar.add(Calendar.DAY_OF_MONTH, newDay);
        }
//        calendar.add(Calendar.SECOND,15);
        LogUtils.logd("newHour:" + newHour + " newMinute:" + newMinute + " newDay:" + newDay);
        return calendar;
    }
}