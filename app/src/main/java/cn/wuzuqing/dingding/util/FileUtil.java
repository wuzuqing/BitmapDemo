package cn.wuzuqing.dingding.util;

import android.os.Environment;

import java.io.File;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 12:46
 * @Description: java类作用描述
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 12:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FileUtil {
    public static void delete(String path,boolean is){
        new File(path).delete();

    }

    public static boolean isExistSDCard(){
        return Environment.getExternalStorageState() .equals(Environment.MEDIA_MOUNTED);
    }
}
