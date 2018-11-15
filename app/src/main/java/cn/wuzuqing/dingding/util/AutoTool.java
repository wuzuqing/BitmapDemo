package cn.wuzuqing.dingding.util;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Locale;

import cn.wuzuqing.dingding.model.PointModel;

public class AutoTool {


    public static void execShellTapXy(int x, int y) {
        execShellCmd(String.format(Locale.getDefault(), "input tap %d %d", x, y));
    }

    public static void execShellSwipe(int start, int end, boolean isX) {
        if (isX) {
            execShellCmd(swipeX(start, end));
        } else {
            execShellCmd(swipeY(start, end));
        }

    }

    public static void execShellInput(String input) {
        execShellCmd(String.format(Locale.getDefault(), "input text %s", input));
    }

    public static void execShellCmd(PointModel model) {
        execShellTapXy(model.getX(), model.getY());
    }

    public static String swipeY(int fy, int ty) {
        return swipe(400, fy, 400, ty);
    }

    public static String swipeX(int fx, int tx) {
        return swipe(fx, 400, tx, 400);
    }

    public static String swipe(int fx, int fy, int tx, int ty) {
        return String.format(Locale.getDefault(), "input swipe %d %d %d %d", fx, fy, tx, ty);
    }

    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd + "\n");
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            LogUtils.logd("cmd:" + cmd);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public static void execShellCmd(int length, int key) throws InterruptedException {
        for (int i = 0; i < length; i++) {
            Thread.sleep(120);
            AutoTool.execShellCmd("input keyevent " + key);
        }
    }
}
