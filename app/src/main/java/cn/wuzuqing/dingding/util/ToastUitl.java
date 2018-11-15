package cn.wuzuqing.dingding.util;

import android.widget.Toast;

import cn.wuzuqing.dingding.BaseApplication;

public class ToastUitl {

    public static void showShort(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
