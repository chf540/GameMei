package gamemei.qiyun.com.gamemei.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;

/**
 * 游戏下载界面
 * Created by hfcui on 2016/2/17
 */
public class GameDownLoadActivity extends BaseActivity {
    /**
     * 日志标记
     */
    private String TAG = "GameDownLoadActivity";
    /**
     * 顶部标题
     */
    private TextView tv_title;
    /**
     * 顶部后退按钮
     */
    private LinearLayout ll_top_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_download);
    }

    @Override
    protected void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("下载管理");
        ll_top_back = (LinearLayout) findViewById(R.id.ll_top_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        //顶部后退按钮
        ll_top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

