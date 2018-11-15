package cn.wuzuqing.dingding.util.youtu;


import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import cn.wuzuqing.dingding.model.Result;
import cn.wuzuqing.dingding.util.LogUtils;
import cn.wuzuqing.dingding.util.Util;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/1/17 21:35
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/1/17$
 * @updateDes ${TODO}
 */

public class ImageParse {


    private static byte[] getDefaultData() {
        return getBitmapByte(Util.bitmap);
    }

    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static List<Result.ItemsBean> getSyncData() {
        LogUtils.logd("start");
        JSONObject respose = null;
        try {
            respose = StaticVal.getYoutu().GeneralOcr(getDefaultData());
            Result res = StaticVal.getGson().fromJson(respose.toString(), Result.class);
            LogUtils.logd("result:"+res.getItems());
            return res.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void getSyncData(Call call) {
        call.call(getSyncData());
    }


    public interface Call {
        void call(List<Result.ItemsBean> result);
    }

}
