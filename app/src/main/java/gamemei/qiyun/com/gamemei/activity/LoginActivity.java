package gamemei.qiyun.com.gamemei.activity;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;
import gamemei.qiyun.com.gamemei.utils.SharedPreferencesUitl;

public class LoginActivity extends BaseActivity implements OnClickListener {

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
    private final String TAG = "LoginActivity";
    /**
     * 登录用户名
     */
    private EditText edit_login_username;
    /**
     * 登录密码
     */
    private EditText edit_login_password;
    /**
     * QQ登录
     */
    private LinearLayout ll_qq;
    /**
     * 新浪微博登录
     */
    private LinearLayout ll_xinlangweibo;
    /**
     * 微信登录
     */
    private LinearLayout ll_wechat;
    /**
     * 注册新用户
     */
    private TextView register_new;
    /**
     * 登录按钮
     */
    private Button btn_login;
    /**
     * 友盟 首先在activity页里添加下面的成员变量
     */
    private UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.login");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Log.i(TAG, "hfcui-----onCreate");
        configPlatforms();
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
        edit_login_username = (EditText) findViewById(R.id.edit_login_username);
        edit_login_password = (EditText) findViewById(R.id.edit_login_password);
        register_new = (TextView) findViewById(R.id.register_new);
        ll_qq = (LinearLayout) findViewById(R.id.ll_qq);
        ll_xinlangweibo = (LinearLayout) findViewById(R.id.ll_xinlangweibo);
        ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //设置顶部标题
        tv_title.setText("用户登录");
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);

        register_new.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        ll_xinlangweibo.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        btn_login.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_new://点击注册按钮
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                break;
            case R.id.ll_qq: // QQ登录
                threadLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.ll_xinlangweibo: // 新浪微博登录
                threadLogin(SHARE_MEDIA.SINA);
                break;
            case R.id.ll_wechat:  // 微信登录
                threadLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_login://用户名密码登录
                Login();
            default:
                break;
        }
    }

    /**
     * 点击登录按钮
     */
    private void Login() {
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
        });
    }

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    private void threadLogin(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.this, platform,
                new UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权开始",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权失败",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        // 获取uid
                        String uid = value.getString("uid");
                        if (!TextUtils.isEmpty(uid)) {
                            // uid不为空，获取用户信息
                            getUserInfo(platform);
                        } else {
                            Toast.makeText(LoginActivity.this, "授权失败...",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 配置分享平台参数
     */
    private void configPlatforms() {
        // 添加新浪sso授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();
        // 添加微信、微信朋友圈平台
        addWXPlatform();

    }

    /**
     * @return
     * @功能描述 : QQ平台登录分享
     */
    private void addQQQZonePlatform() {
        String appId = "100424468";
        String appKey = "c7394704798a158208a74ab60104f0ba";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
                LoginActivity.this, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx967daebe835fbeac";
        String appSecret = "5bb696d9ccd75a38c8a0bfe0675559b3";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this, appId,
                appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(LoginActivity.this,
                appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    // 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                resultCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform,
                new UMDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {

                        /**
                         * QQ登录信息
                         *
                         * 12-09 14:21:25.283: I/umengsocial(21619):
                         * {is_yellow_year_vip=0, vip=0, level=0, province=安徽,
                         * yellow_vip_level=0, is_yellow_vip=0, gender=男,
                         * screen_name=与梦逆风而行, msg=,
                         * profile_image_url=http://q.qlogo .cn/qqapp/100424468/
                         * C760014E3CF49E25057757DDC319E38D /100, city=合肥}
                         */

                        /**
                         * 新浪微博登录信息
                         *
                         * 12-09 14:23:40.453: I/umengsocial(21619):
                         * {uid=3205585611, favourites_count=1, location=安徽 合肥,
                         * description=与梦逆风而行, verified=false, friends_count=80,
                         * gender=1, screen_name=与梦顺风而行, statuses_count=59,
                         * followers_count=39,
                         * profile_image_url=http://tp4.sinaimg
                         * .cn/3205585611/180/40011457848/1,
                         * access_token=2.00jP_wUDMZD6RE88b3121451HT2ciB}
                         */

                        if (info != null) {
                            Toast.makeText(LoginActivity.this, info.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.i(info.toString());
                        }
                    }
                });
    }

    /**
     * 返回按钮
     */
    @Override
    public void onBackPressed() {
        //  startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
