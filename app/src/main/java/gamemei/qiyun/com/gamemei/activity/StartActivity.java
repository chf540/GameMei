package gamemei.qiyun.com.gamemei.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import gamemei.qiyun.com.gamemei.R;

/**
 * 首页面
 *
 * @author hfcui
 */
public class StartActivity extends FragmentActivity {
    /**
     * 日志标记
     */
    private final String TAG = "StartActivity";
    /**
     * 延迟2秒后进入引导界面
     */
    private final int SPLASH_DISPLAY_LENGHT = 2000;
    /**
     * SharedPreferences操作,功能判断是否是首次运行
     */
    private SharedPreferences preferences;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Log.i(TAG, "hfcui-----onCreate");
        initPushAgent();
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.getBoolean("firststart", true)) {
                    editor = preferences.edit();
                    // 将登录标志位设置为false，下次登录时不在显示首次登录界面
                    editor.putBoolean("firststart", false);
                    editor.commit();
                    Intent intent = new Intent();
                    //第一次登陆应用则进入SplashViewPagerActivity（引导页面）
                    startActivity(new Intent(getApplicationContext(), SplashViewPagerActivity.class));
                    finish();
                } else {
                    //如果不是第一次登陆直接进入HomeActivity（主页面）
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGHT);// 延迟2秒后进入引导界面
    }

    /**
     * 初始化友盟第三方推送
     */
    private void initPushAgent() {
        PushAgent.getInstance(getApplicationContext()).onAppStart();
        PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
        //启动推送
        mPushAgent.enable();
        //友盟推送获取设备token
        getDeviceToken();
    }

    /**
     * 友盟推送获取设备token
     */
    private void getDeviceToken() {
        String device_token = UmengRegistrar.getRegistrationId(this);
        System.out.println("getDeviceToken-----" + device_token);
    }
}
