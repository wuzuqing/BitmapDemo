package cn.wuzuqing.dingding.util.youtu;


import com.google.gson.Gson;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/1/18 14:53
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/1/18$
 * @updateDes ${TODO}
 */

public class StaticVal {
    public static final String APP_ID = "10153872";
    public static final String SECRET_ID = "AKIDVuqMrVxWkpjR2U4snIzVpT6RCEH5tJhv";
    public static final String SECRET_KEY = "U6GKbGbs9YMySLKJKPVXZKafUZAxlFft";
    public static final String USER_ID = "1052243597";
    public final static String API_YOUTU_END_POINT = "https://api.youtu.qq.com/youtu/";

    public static final String APP1_ID = "10115505";
    public static final String SECRET1_ID = "AKIDY1JPWWtHpIbjhTpz60iJl273oNn1x0mN";
    public static final String SECRET1_KEY = "a1GILJD7sSs6DAh3GI8mJjxzpstjSzMy";
    public static final String USER1_ID = "1052243597";

    private static Youtu sYoutu;
    private static Gson gson;

    public static void init() {
        sYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, API_YOUTU_END_POINT, USER_ID);
        gson = new Gson();
    }

    public static Youtu getYoutu() {
        return sYoutu;
    }

    public static void reset() {
        sYoutu.reset(APP1_ID, SECRET1_ID, SECRET1_KEY, USER1_ID);
    }

    public static Gson getGson() {
        return gson;
    }
}
