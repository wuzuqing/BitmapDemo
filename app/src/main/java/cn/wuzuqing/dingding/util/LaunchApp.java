package cn.wuzuqing.dingding.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2017/12/28 10:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2017/12/28$
 * @updateDes ${TODO}
 */

public class LaunchApp {
    //首先我们必须要知道要跳转的app的包名，每一个APP的包名都是独立的，纵使是马甲包和主包的包名也是不一样的。
    //我们将要跳转的包名填在以下位置。


    public static final String DING_DING = "com.alibaba.android.rimet";

    public static String APP_PACKAGE_NAME = DING_DING;

    /**
     * 跳转页面的方法
     * @param context
     */
    public static void launchapp(Context context) {
        launchapp(context, APP_PACKAGE_NAME);
    }

    /**
     * 跳转页面的方法
     * @param context
     * @param packageName
     */
    public static void launchapp(Context context, String packageName) {
        APP_PACKAGE_NAME = packageName;
        //判断当前手机是否有要跳入的app
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            //如果有根据包名跳转
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            //如果没有，走进入系统商店找到这款APP，提示你去下载这款APP的程序
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 这里是进入应用商店，下载指定APP的方法。
     * @param context
     * @param packageName
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里是判断APP中是否有相应APP的方法
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
