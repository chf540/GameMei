package gamemei.qiyun.com.gamemei.activity;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;
import gamemei.qiyun.com.gamemei.utils.SharedPreferencesUitl;
import gamemei.qiyun.com.gamemei.widget.clearedittext.ClearEditText;

/**
 * 登录界面
 * Created by hfcui on 2015/1/26
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    /**
     * 顶部标题
     */
    private TextView tv_title;

    /**
     * 顶部后退按钮
     */
    private LinearLayout ll_top_back;
    /**
     * 日志标记
     */
    private final String TAG = "LoginActivity";
    /**
     * 登录用户名
     */
    private ClearEditText edit_login_username;
    /**
     * 登录密码
     */
    private ClearEditText edit_login_password;
    /**
     * QQ登录
     */
    private ImageView iv_qq;
    /**
     * 新浪微博登录
     */
    private ImageView iv_xinlangweibo;
    /**
     * 微信登录
     */
    private ImageView iv_wechat;
    /**
     * 注册新用户
     */
    private ImageView btn_register;
    /**
     * 登录按钮
     */
    private ImageView btn_login;
    /**
     * 忘记密码
     */
    private TextView tv_forget_password;

    /**
     * 友盟第三方授权
     */
    private UMShareAPI mShareAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "hfcui-----onCreate");
        mShareAPI = UMShareAPI.get(this);
        //如果记住用户名的话复显
        if (SharedPreferencesUitl.getStringData(getApplicationContext(), "username", "") != null) {
            String username = SharedPreferencesUitl.getStringData(getApplicationContext(), "username", "");
            edit_login_username.setText(username);
        }
        //如果记住密码的话复显
        if (SharedPreferencesUitl.getStringData(getApplicationContext(), "password", "") != null) {
            String password = SharedPreferencesUitl.getStringData(getApplicationContext(), "password", "");
            edit_login_password.setText(password);
        }
    }


    @Override
    protected void initView() {
        edit_login_username = (ClearEditText) findViewById(R.id.edit_login_username);
        edit_login_password = (ClearEditText) findViewById(R.id.edit_login_password);
        btn_register = (ImageView) findViewById(R.id.btn_register);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_xinlangweibo = (ImageView) findViewById(R.id.iv_xinlangweibo);
        iv_wechat = (ImageView) findViewById(R.id.iv_wechat);
        btn_login = (ImageView) findViewById(R.id.btn_login);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("账户登录");
        ll_top_back = (LinearLayout) findViewById(R.id.ll_top_back);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);

        btn_register.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_xinlangweibo.setOnClickListener(this);
        iv_wechat.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        SHARE_MEDIA platform = null;

        switch (v.getId()) {
            case R.id.btn_register://点击注册按钮
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.iv_qq: // QQ登录
                platform = SHARE_MEDIA.QQ;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.iv_xinlangweibo: // 新浪微博登录
                platform = SHARE_MEDIA.SINA;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.iv_wechat:  // 微信登录
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.btn_login://登录
                Login();
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(getApplicationContext(), RetrievePasswordActivity.class));
            default:
                break;
        }
    }

    /**
     * 点击登录按钮
     */
    private void Login() {
        String username = edit_login_username.getText().toString().trim();
        String password = edit_login_password.getText().toString().trim();

        //用户名和密码非空和正则校验
        if (TextUtils.isEmpty(username)) {
            AppUtils.showTips(this, R.mipmap.tips_error, "未填写用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            AppUtils.showTips(this, R.mipmap.tips_error, "未填写密码");
            return;
        } else {
            Pattern patternPassword = Pattern
                    .compile("([a-zA-Z0-9]{6,20})");//0~9的数字和A-Z,a-z字母，最低6位，最高20位
            Matcher matcher = patternPassword.matcher(password);
            if (!matcher.matches()) {
                AppUtils.showTips(this, R.mipmap.tips_error, "密码不合规");
                return;
            }
        }
        //TODO 和服务器交互
        AppUtils.showTips(this, R.mipmap.tips_smile, "登录成功，跳转到个人中心界面");

        final HttpUtils httpUtils = new HttpUtils();
        final String geturl = "http://www.baidu.com";
        httpUtils.send(HttpRequest.HttpMethod.GET, geturl, new RequestCallBack<Object>() {
                    @Override
                    //成功回调
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        //保存cookies
                        DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
                        MyHttpUtils.cookieStore = dh.getCookieStore();
                        CookieStore cs = dh.getCookieStore();
                        //获取到SESSIONID
                        List<Cookie> cookies = cs.getCookies();
                        String a = null;
                        for (int i = 0; i < cookies.size(); i++) {
                            if ("JSESSIONID".equals(cookies.get(i).getName())) {
                                a = cookies.get(i).getValue();
                                break;
                            }
                        }
                        Log.d("hfcui", "获取sessionid" + a);
                        Log.d("cookieStore", "haha" + MyHttpUtils.cookieStore.toString());
                    }

                    @Override
                    //失败回调
                    public void onFailure(HttpException e, String s) {

                    }
                }
        );
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mShareAPI.getPlatformInfo(this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Toast.makeText(getApplicationContext(), map.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    /**
     * 返回按钮
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
