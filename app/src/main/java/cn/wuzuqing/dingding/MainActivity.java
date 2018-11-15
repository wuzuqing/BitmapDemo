package cn.wuzuqing.dingding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.wuzuqing.dingding.accessiblity.AccessibilityNormalSample;
import cn.wuzuqing.dingding.accessiblity.OpenAccessibilitySettingHelper;
import cn.wuzuqing.dingding.service.AlarmService;
import cn.wuzuqing.dingding.service.InitService;
import cn.wuzuqing.dingding.service.StartService;
import cn.wuzuqing.dingding.util.SPUtils;

/**
 * @Author: 吴祖清
 * @CreateDate: 2018/10/25 9:58
 * @Description:
 * @UpdateUser: 吴祖清
 * @UpdateDate: 2018/10/25 9:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainActivity extends AppCompatActivity {
    private String IS_INIT = "IS_INIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final boolean isInit = SPUtils.getInstance().getBoolean(IS_INIT,false);
        if (isInit) {
            BaseApplication.setAlarm();
            StartService.startServer(this);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            findViewById(R.id.setNClock).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmService.set(MainActivity.this);
                    finish();
                }
            });
            findViewById(R.id.startInit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startService(new Intent(getApplication(), InitService.class));
                    finish();
                }
            });
            findViewById(R.id.finishInit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPUtils.getInstance().put(IS_INIT, true);
                    stopService(new Intent(getApplication(), InitService.class));
                    finish();
                }
            });
        }

    }


    public void startHelper(View view) {
        OpenAccessibilitySettingHelper.jumpToSettingPage(this);
    }

    public void findAndClick(View view) {
        startActivity(new Intent(this, AccessibilityNormalSample.class));
    }
}
