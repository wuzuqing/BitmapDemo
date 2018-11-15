package cn.wuzuqing.dingding;

import java.util.List;

import cn.wuzuqing.dingding.constant.AppConstant;
import cn.wuzuqing.dingding.model.PointModel;
import cn.wuzuqing.dingding.model.Result;
import cn.wuzuqing.dingding.util.Util;
import cn.wuzuqing.dingding.util.youtu.ImageParse;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 13:13
 * @Description: java类作用描述
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 13:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class InitPointRunnable implements Runnable, AppConstant {

    private CapGrayBitmap grayBitmap = new CapGrayBitmap();

    private void setData(String key, Result.ItemsBean.ItemcoordBean bean) {
        PointModel model = null;
        if ("请输入手机号码".equals(key)) {
            model = Util.get(LOGIN_PHONE);
            model.reset(bean, false);
        } else if ("请输入密码".equals(key)) {
            model = Util.get(LOGIN_PWD);
            model.reset(bean, false);
        } else if ("登录".equals(key)) {
            model = Util.get(LOGIN_LOGIN);
            model.reset(bean, true);
        } else if ("工作".equals(key)) {
            if (bean.getY() > 1600) {
                model = Util.get(HOME_WORK);
                model.reset(bean, true);
            }
        } else if ("我的".equals(key)) {
            if (bean.getY() > 1600) {
                model = Util.get(AppConstant.HOME_ME);
                model.reset(bean, true);
            }
        } else if ("考勤打卡".equals(key)) {
            model = Util.get(AppConstant.HOME_WORK_KAOQIN);
            model.reset(bean, true);
        } else if ("设置".equals(key)) {
            if (bean.getY() > 800) {
                model = Util.get(AppConstant.HOME_ME_SETTING);
                model.reset(bean, true);
            }
        } else if ("退出登录".equals(key)) {
            if (bean.getY() > 800) {
                model = Util.get(AppConstant.HOME_ME_SETTING_LOGOUT);
                model.reset(bean, true);
            }
        }
        if (model != null) {
            model.setNormalColor(Util.getColor(Util.bitmap, model.getX(), model.getY()));
        }
    }

    @Override
    public void run() {
        //截屏
        grayBitmap.execute(false, null);
        //获取数据
        List<Result.ItemsBean> result = ImageParse.getSyncData();
        String itemstring;
        for (Result.ItemsBean bean : result) {
            itemstring = bean.getItemstring();
            //设置新的数据
            setData(itemstring, bean.getItemcoord());
        }
        //保持数据
        Util.saveNewJson();
        Util.isInit = false;
    }
}
