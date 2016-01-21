package gamemei.qiyun.com.gamemei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.utils.SharedPreferencesUitl;

/**
 * Created by hfcui on 2016/1/5
 */
public class TelRegisterActivity extends BaseActivity {
    /**
     * 顶部标题
     */
    private TextView tv_title;

    /**
     * 顶部后退按钮
     */
    private ImageView title_bar_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tel_register_activity);
    }

    @Override
    protected void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("手机号注册");
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        //顶部后退按钮
        title_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

