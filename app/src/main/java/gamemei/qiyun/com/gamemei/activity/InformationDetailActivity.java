package gamemei.qiyun.com.gamemei.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.utils.AppUtils;

/**
 * 经验详情界面
 * Created by hfcui on 2016/1/28.
 */
public class InformationDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 日志标记
     */
    private String TAG = "InformationDetailActivity";
    /**
     * 返回按钮
     */
    private LinearLayout ll_title_bar_back;
    /**
     * 顶部分享、收藏按钮
     */
    private LinearLayout ll_title_bar_more;
    /**
     * 分享
     */
    private Button btn_top_share;
    /**
     * 收藏
     */
    private Button btn_top_collect;

    private Button btn_issue;

    private EditText et_comment_content;

    /**
     * 友盟社会化组件
     */
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        //设置分享和收藏
        setShareCollect();
        //配置分享平台参数
        configPlatforms();
        setWebView();
    }

    private void setWebView() {
        WebView webView = (WebView) findViewById(R.id.wv_content);
        String url = "http://www.gamemei.com/news/9582.html";
        webView.loadUrl(url);
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        //优先使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设定支持缩放
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//设定支持缩放
        //点击后的网页继续使用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }


    /***************************** 友盟分享--开始 *********************************/
    /**
     * 配置分享平台参数
     */
    private void configPlatforms() {
        // 添加新浪sso授权
        //  mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();
        // 添加微信、微信朋友圈平台
        addWXPlatform();
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

        mController.setShareContent("GameMei游戏魅");
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(InformationDetailActivity.this,
                "http://www.gamemei.com/background/uploads/160125/2-1601251R5401b.png"));

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(InformationDetailActivity.this, appId,
                appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(InformationDetailActivity.this,
                appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform() {
        String appId = "100424468";
        String appKey = "c7394704798a158208a74ab60104f0ba";

        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(InformationDetailActivity.this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
                InformationDetailActivity.this, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    /***************************** 友盟分享--结束 *********************************/

    /**
     * 设置分享和收藏popwindow
     */
    private void setShareCollect() {
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.pop_share, null);
        final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        //分享按钮
        btn_top_share = (Button) view.findViewById(R.id.btn_top_share);
        btn_top_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.openShare(InformationDetailActivity.this, false);
                pop.dismiss();
            }
        });
        //收藏按钮
        btn_top_collect = (Button) view.findViewById(R.id.btn_top_collect);
        btn_top_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.showTips(getApplicationContext(), R.mipmap.tips_smile, "收藏成功");
                pop.dismiss();
            }
        });
        //点击右上方按钮打开收起popwindow
        ll_title_bar_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop.isShowing()) {
                    // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
                    pop.dismiss();
                } else {
                    // 显示窗口
                    pop.showAsDropDown(v);
                }
            }
        });
    }


    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        ll_title_bar_back = (LinearLayout) findViewById(R.id.ll_title_bar_back);
        ll_title_bar_more = (LinearLayout) findViewById(R.id.ll_title_bar_more);
        btn_issue = (Button) findViewById(R.id.btn_issue);
        et_comment_content = (EditText) findViewById(R.id.et_comment_content);

        btn_issue.setOnClickListener(this);
        ll_title_bar_back.setOnClickListener(this);
        ll_title_bar_more.setOnClickListener(this);
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
        //设置输入框变化监听
        et_comment_content.addTextChangedListener(new TextWatcher() {
            //初始化状态
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_issue.setBackgroundResource(R.drawable.bg_edittext);
            }

            //输入框变时状态
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_issue.setBackgroundColor(Color.parseColor("#0ad9b2"));
                btn_issue.setTextColor(Color.parseColor("#ffffff"));
            }

            //输入后的状态
            @Override
            public void afterTextChanged(Editable editable) {
                String string = et_comment_content.getText().toString().trim();
                if (TextUtils.isEmpty(string)) {
                    btn_issue.setBackgroundResource(R.drawable.bg_edittext);
                    btn_issue.setTextColor(Color.parseColor("#969696"));
                }
            }
        });
    }

    /**
     * 处理点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_bar_back:
                finish();
                break;
            case R.id.btn_issue:
                //获取到输入框内容
                String string = et_comment_content.getText().toString().trim();
                if (!TextUtils.isEmpty(string)) {
                    AppUtils.showTips(this, R.mipmap.tips_smile, "发布成功");
                    //清空输入框
                    et_comment_content.setText("");
                } else {
                    AppUtils.showTips(this, R.mipmap.tips_error, "不能发布空白评论");
                }
                break;

            default:
                break;
        }
    }

    /**
     * ListView 适配器
     *
     * @author hfcui
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter() {
            this.mInflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item_comment_list, null);
            return convertView;
        }
    }
}
