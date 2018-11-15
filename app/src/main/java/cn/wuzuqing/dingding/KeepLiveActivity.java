package cn.wuzuqing.dingding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.wuzuqing.dingding.util.KeepLiveManager;

/**
 * @author: 吴祖清
 * @createDate: 2018/11/6 14:29
 * @description: java类作用描述
 * @updateUser: 吴祖清
 * @updateDate: 2018/11/6 14:29
 * @updateRemark: 更新说明
 * @version: 1.0
 */
public class KeepLiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ImageView(this));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = 1;
        params.height = 1;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        getWindow().setAttributes(params);
        KeepLiveManager.getInstance().setAct(this);
    }

}
