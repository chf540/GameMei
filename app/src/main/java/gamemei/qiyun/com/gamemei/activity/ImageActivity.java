package gamemei.qiyun.com.gamemei.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.HomeActivity;
import gamemei.qiyun.com.gamemei.activity.SplashViewPagerActivity;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;

/**
 * 游戏缩略图展示界面
 *
 * @author hfcui
 */
public class ImageActivity extends BaseActivity {
    /**
     * 日志标记
     */
    private final String TAG = "ImageActivity";

    private ImageView ll_game_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }

    @Override
    protected void initView() {
        ll_game_image = (ImageView) findViewById(R.id.iv_game_image);
        ll_game_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
