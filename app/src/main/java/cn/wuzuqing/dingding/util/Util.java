package cn.wuzuqing.dingding.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import cn.wuzuqing.dingding.BaseApplication;
import cn.wuzuqing.dingding.constant.AppConstant;
import cn.wuzuqing.dingding.model.PointModel;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 14:41
 * @Description:
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 14:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Util implements AppConstant {


    private static Map<String, PointModel> pointModelMap;
    private static final String POINT_KEY = "POINT_KEY";

    public static boolean isInit = false;

    public static void init() {
        String points = SPUtils.getInstance().getString(POINT_KEY);
        if (TextUtils.isEmpty(points)) {
            initDefaultPoints();
        } else {
            pointModelMap = JsonUtils.getPointForJson(points);
        }
        LogUtils.logd("init:" + pointModelMap);
    }

    private static void initDefaultPoints() {
        pointModelMap = new HashMap<>(9);
        String pointsJson = JsonUtils.getJson("point.json", BaseApplication.getContext());
        if (TextUtils.isEmpty(pointsJson)) {
            return;
        }
        pointModelMap = JsonUtils.getPointForJson(pointsJson);
        saveNewJson();
    }

    public static void saveNewJson() {
        String mapStr = JsonUtils.toJson(pointModelMap);
        LogUtils.logd("points:" + mapStr);
        SPUtils.getInstance().put(POINT_KEY, mapStr);
    }

    public static synchronized PointModel get(String key) {
        if (pointModelMap==null){
            init();
        }
        return pointModelMap.get(key);
    }

    private static void put(String key, String name, String color, int x, int y) {
        pointModelMap.put(key, new PointModel(key, name, color, x, y));
    }

    public static Bitmap bitmap;

    public static String getColorHtml(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }


    public static String getColor(Bitmap bitmap, int x, int y) {
        if (bitmap == null) {
            return "";
        }
        int pixel = bitmap.getPixel(x, y);
        return getColorHtml(pixel);
    }


    public static boolean checkColor(Bitmap bitmap, PointModel pointModel) {
        if (bitmap == null || pointModel == null) {
            return false;
        }
        String color = getColor(bitmap, pointModel.getX(), pointModel.getY());
        LogUtils.logd("color:" + color + " pointModel:" + pointModel.toString());
        return color.equals(pointModel.getNormalColor()) || likeColor(color, pointModel.getNormalColor());
    }

    public static boolean likeColor(String old, String newColorStr) {
        return likeColor(Color.parseColor(old), Color.parseColor(newColorStr));
    }


    public static boolean likeColor(String old, String newColorStr, int offset, int count) {
        return likeColor(Color.parseColor(old), Color.parseColor(newColorStr), offset, count);
    }

    public static boolean likeColor(int old, int newColor) {
        return likeColor(old, newColor, 8, 1);
    }

    public static boolean likeColor(int old, int newColor, int offset, int xianXi) {
        int redO = Color.red(old);
        int greenO = Color.green(old);
        int blueO = Color.blue(old);
        int redN = Color.red(newColor);
        int greenN = Color.green(newColor);
        int blueN = Color.blue(newColor);
        int absR = Math.abs(redO - redN);
        int absG = Math.abs(greenO - greenN);
        int absB = Math.abs(blueO - blueN);
        int count = 0;
        if (absR < offset) {
            count++;
        }
        if (absG < offset) {
            count++;
        }
        if (absB < offset) {
            count++;
        }
        return count > xianXi;
    }

    public static boolean checkColor(PointModel pointModel) {
        if (bitmap == null || pointModel == null) {
            return false;
        }
        String color = getColor(bitmap, pointModel.getX(), pointModel.getY());
        LogUtils.logd("color:" + color + " pointModel:" + pointModel.toString());
        return color.equals(pointModel.getNormalColor()) || likeColor(color, pointModel.getNormalColor());
    }

    public static boolean checkColor(PointModel pointModel, int offset, int xiangXi) {
        if (bitmap == null || pointModel == null) {
            return false;
        }
        String color = getColor(bitmap, pointModel.getX(), pointModel.getY());
        LogUtils.logd("color:" + color + " pointModel:" + pointModel.toString());
        return color.equals(pointModel.getNormalColor()) || likeColor(color, pointModel.getNormalColor(), offset, xiangXi);
    }

}
