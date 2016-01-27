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
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.utils.SharedPreferencesUitl;

/**
 * Created by hfcui on 2015/1/26
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 顶部标题
     */
    private TextView tv_title;

    /**
     * 顶部后退按钮
     */
    private ImageView title_bar_back;
    /**
     * 日志标记
     */
    private final String TAG = "RegisterActivity";
    /**
     * 用户名
     */
    private EditText edit_register_username;
    /**
     * 邮箱
     */
    private EditText edit_register_email;
    /**
     * 密码
     */
    private EditText edit_register_password;
    /**
     * 确认密码
     */
    private EditText edit_register_password_;

    /**
     * 注册按钮
     */
    private ImageView bt_register;
    /**
     * 已有账号登录
     */
    private TextView tv_tel_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.i(TAG, "hfcui-----onCreate");
    }

    /**
     * 初始化组件
     */
    protected void initView() {
        edit_register_username = (EditText) findViewById(R.id.edit_register_username);
        edit_register_email = (EditText) findViewById(R.id.edit_register_email);
        edit_register_password = (EditText) findViewById(R.id.edit_register_password);
        edit_register_password_ = (EditText) findViewById(R.id.edit_register_password_);
        bt_register = (ImageView) findViewById(R.id.bt_register);
        tv_tel_login = (TextView) findViewById(R.id.tv_tel_login);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("注册帐户");
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        bt_register.setOnClickListener(this);
        tv_tel_login.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    /**
     * 设置监听
     */
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                //校验注册内容
                checkInfo();
                //TODO  点击注册按钮
                break;
            case R.id.tv_tel_login:
                //跳转到登录界面
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //关闭该Activity
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 注册内容校验（非空，正则）
     */
    private void checkInfo() {
        //获得输入框的内容，转换为字符串并去除空格
        String username = edit_register_username.getText().toString().trim();
        String email = edit_register_email.getText().toString().trim();
        String password = edit_register_password.getText().toString().trim();
        String password_ = edit_register_password_.getText().toString().trim();
        //进行校验
        //用户名的非空和正则校验
        if (TextUtils.isEmpty(username)) {
            AppUtils.showTips(this, R.mipmap.tips_error, "未填写用户名");
        } else {
            Pattern patternName = Pattern
                    .compile("([a-zA-Z0-9]{6,20})");//0~9的数字和A-Z,a-z字母，最低6位，最高20位
            Matcher matcher = patternName.matcher(username);
            if (!matcher.matches()) {
                AppUtils.showTips(this, R.mipmap.tips_error, "用户名不合规");
                return;
            }
            //Email的非空和正则校验
            if (TextUtils.isEmpty(email)) {
                AppUtils.showTips(this, R.mipmap.tips_error, "未填写邮箱");
            } else {
                Pattern patternEmail = Pattern
                        .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//@前，首字符必须是字母或者数字，中间允许有 - + . 字符出现  @后同样类似是，区别在只能出现 - . 字符，最后结尾必须是字母或者数字
                matcher = patternEmail.matcher(email);
                if (!matcher.matches()) {
                    AppUtils.showTips(this, R.mipmap.tips_error, "邮箱不合规");
                    return;
                }
                //密码的非空和正则校验
                if (TextUtils.isEmpty(password)) {
                    AppUtils.showTips(this, R.mipmap.tips_error, "未填写密码");
                } else {
                    Pattern patternPassword = Pattern
                            .compile("([a-zA-Z0-9]{6,20})");//0~9的数字和A-Z,a-z字母，最低6位，最高20位
                    matcher = patternPassword.matcher(password);
                    if (!matcher.matches()) {
                        AppUtils.showTips(this, R.mipmap.tips_error, "密码不合规");
                        return;
                    }
                    //确认密码的非空校验
                    if (TextUtils.isEmpty(password_)) {
                        AppUtils.showTips(this, R.mipmap.tips_error, "未确认密码");
                    }
                    //确认密码和密码是否相同校验
                    else if (!password_.equals(password)) {
                        AppUtils.showTips(this, R.mipmap.tips_error, "两次密码不一致");
                        return;
                    } else {
                        String userInfo = username + "**" + email + "**" + password;//用户注册的所有信息。
                        SharedPreferencesUitl.saveStringData(getApplicationContext(), "username", username);
                        SharedPreferencesUitl.saveStringData(getApplicationContext(), "email", email);
                        SharedPreferencesUitl.saveStringData(getApplicationContext(), "password", password);
                        //TODO 发送注册信息到服务器端
                    }
                }
            }
        }
    }
}

