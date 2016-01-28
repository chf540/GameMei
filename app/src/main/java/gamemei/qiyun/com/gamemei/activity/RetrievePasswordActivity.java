package gamemei.qiyun.com.gamemei.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.widget.clearedittext.ClearEditText;
import gamemei.qiyun.com.gamemei.widget.dialog.CustomDialog;

/**
 * 密码找回界面
 * Created by hfcui on 2016/1/27.
 */
public class RetrievePasswordActivity extends BaseActivity implements OnClickListener {

    /**
     * 顶部标题
     */
    private TextView tv_title;
    /**
     * 顶部后退按钮
     */
    private ImageView title_bar_back;
    /**
     * 邮箱输入框
     */
    private ClearEditText et_email;
    /**
     * 找回密码按钮
     */
    private ImageView btn_retrieve_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
    }

    @Override
    protected void initView() {
        et_email = (ClearEditText) findViewById(R.id.et_email);
        btn_retrieve_password = (ImageView) findViewById(R.id.btn_retrieve_password);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("找回密码");
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        btn_retrieve_password.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retrieve_password:
                messageBox();
                break;

            default:
                break;
        }
    }

    //弹出对话框
    private void messageBox() {
        String email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            AppUtils.showTips(this, R.mipmap.tips_error, "未填写注册邮箱");
            return;
        } else {
            Pattern patternEmail = Pattern
                    .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//@前，首字符必须是字母或者数字，中间允许有 - + . 字符出现  @后同样类似是，区别在只能出现 - . 字符，最后结尾必须是字母或者数字
            Matcher matcher = patternEmail.matcher(email);
            if (!matcher.matches()) {
                AppUtils.showTips(this, R.mipmap.tips_error, "邮箱格式不正确");
                return;
            }
        }
        // 取回密码成功弹框
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //跳转到登录界面
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                //TODO 找回密码
            }
        });
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
