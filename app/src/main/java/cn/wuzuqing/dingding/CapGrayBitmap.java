package cn.wuzuqing.dingding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;

import cn.wuzuqing.dingding.util.AutoTool;
import cn.wuzuqing.dingding.util.LogUtils;
import cn.wuzuqing.dingding.util.Util;

/**
 * @author Administrator
 */
public class CapGrayBitmap {

    public static String screenCapFilePath = new File(Environment.getExternalStorageDirectory(), "/cap/22.jpg").getAbsolutePath();

    public static String screenCap = "screencap -p " + screenCapFilePath;

    private Bitmap bitmapOrig = null;
    private Bitmap bitmapGray = null;
    BitmapFactory.Options options;
    private File file;
    public CapGrayBitmap() {
        options = new BitmapFactory.Options();
        // Make sure it is 24 bit color as our image processing algorithm
        // expects this format
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        file = new File(screenCapFilePath);
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native void convertBmp(Bitmap bitmapIn, Bitmap bitmapOut);


    public void execute(boolean isGray, CallBack callBack) {
        try {
            long start = System.currentTimeMillis();
            //删除源文件
            file.delete();
            //截屏
            AutoTool.execShellCmd(screenCap);
            int count = 0;
            Thread.sleep(1100L);
            while (true) {
                if (file.length() > 0) {
                    LogUtils.logd("成功" + count);
                    break;
                } else if (count > 15) {
                    LogUtils.logd("失败");
                    return;
                }
                Thread.sleep(100);
                count++;
            }
            //创建bitmap对象

            bitmapOrig = BitmapFactory.decodeFile(screenCapFilePath, options);
            if (bitmapOrig != null) {
                if (isGray) {
                    LogUtils.logd("used tran after time = " + (System.currentTimeMillis() - start));
                    bitmapGray = Bitmap.createBitmap(bitmapOrig.getWidth(),
                            bitmapOrig.getHeight(), Bitmap.Config.ARGB_8888);

                    convertBmp(bitmapOrig, bitmapGray);
                } else {
                    bitmapGray = bitmapOrig;
                }
                if (callBack != null) {
                    callBack.call(bitmapGray);
                } else {
                    Util.bitmap = bitmapGray;
                }
            }
            LogUtils.logd("used  end time = " + (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {
        /**
         * 回调
         * @param outputBitmap 返回
         */
        void call(Bitmap outputBitmap);
    }

}
