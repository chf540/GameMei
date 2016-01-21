package gamemei.qiyun.com.gamemei.activity;

import android.app.Activity;
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
 * Created by hfcui on 2015/12/22.
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
    private Button bt_register;
    /**
     * 已有账号登录
     */
    private TextView tv_login;

    /**
     * 手机号注册
     */
    private TextView tv_tel_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
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
        bt_register = (Button) findViewById(R.id.bt_register);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_tel_register = (TextView) findViewById(R.id.tv_tel_register);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("注册新用户");
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        bt_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_tel_register.setOnClickListener(this);
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
                //TODO  点击注册按钮
                //校验注册内容
                checkInfo();
                break;
            case R.id.tv_login:
                //跳转到登录界面
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //关闭该Activity
                finish();
                break;
            case R.id.tv_tel_register:
                //跳转到手机号码注册界面
                startActivity(new Intent(getApplicationContext(), TelRegisterActivity.class));
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
            Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Pattern patternName = Pattern
                    .compile("([a-zA-Z0-9]{6,20})");//0~9的数字和A-Z,a-z字母，最低6位，最高20位
            Matcher matcher = patternName.matcher(username);
            if (!matcher.matches()) {
                Toast.makeText(getApplicationContext(), "用户名格式错误", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            //Email的非空和正则校验
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "邮箱不能为空", Toast.LENGTH_SHORT).show();
            } else {
                Pattern patternEmail = Pattern
                        .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//@前，首字符必须是字母或者数字，中间允许有 - + . 字符出现  @后同样类似是，区别在只能出现 - . 字符，最后结尾必须是字母或者数字
                matcher = patternEmail.matcher(email);
                if (!matcher.matches()) {
                    Toast.makeText(getApplicationContext(), "邮箱格式错误", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                //密码的非空和正则校验
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Pattern patternPassword = Pattern
                            .compile("([a-zA-Z0-9]{6,20})");//0~9的数字和A-Z,a-z字母，最低6位，最高20位
                    matcher = patternPassword.matcher(password);
                    if (!matcher.matches()) {
                        Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    //确认密码的非空校验
                    if (TextUtils.isEmpty(password_)) {
                        Toast.makeText(getApplicationContext(), "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    //确认密码和密码是否相同校验
                    else if (!password_.equals(password)) {
                        Toast.makeText(getApplicationContext(), "密码两次输入不一致", Toast.LENGTH_SHORT)
                                .show();
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

