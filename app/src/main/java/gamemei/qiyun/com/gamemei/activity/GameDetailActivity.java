package gamemei.qiyun.com.gamemei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.ArrayList;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.fragment.ExperienceFragment;
import gamemei.qiyun.com.gamemei.fragment.GameCommentFragment;
import gamemei.qiyun.com.gamemei.fragment.GameDetailFragment;
import gamemei.qiyun.com.gamemei.fragment.HotFragment;
import gamemei.qiyun.com.gamemei.fragment.InformationFragment;
import gamemei.qiyun.com.gamemei.fragment.MissionFragment;
import gamemei.qiyun.com.gamemei.fragment.QAFragment;
import gamemei.qiyun.com.gamemei.fragment.VideoFragment;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.widget.slidingtab.PagerSlidingTabStrip;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

/**
 * Created by hfcui on 2016/2/18
 */
public class GameDetailActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    protected ViewPager gameViewPager;

    /**
     * 日志标记
     */
    private String TAG = "GameDetailActivity";
    /**
     * 用户选择的顶部分类列表
     */
    private static ArrayList<String> typeList;
    /**
     * 顶部切换选择器
     */
    private PagerSlidingTabStrip game_viewpager_indicator;
    /**
     * 返回按钮
     */
    private LinearLayout ll_title_bar_back;
    /**
     * 试玩按钮
     */
    private Button trial_game;
    /**
     * 分享
     */
    private LinearLayout ll_share;
    /**
     * 收藏
     */
    private LinearLayout ll_collect;
    /**
     * 顶部游戏性信息view
     */
    private View item_game_detail;
    /**
     * 友盟社会化组件
     */
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initColumnData();
        //配置分享平台参数
        configPlatforms();
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
        mController.setShareMedia(new UMImage(this,
                "http://www.gamemei.com/background/uploads/160125/2-1601251R5401b.png"));

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
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
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, appId, appKey);
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

    /***************************** 友盟分享--结束 ********************************/

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        game_viewpager_indicator = (PagerSlidingTabStrip) findViewById(R.id.game_viewpager_indicator);
        game_viewpager_indicator.setTabPaddingLeftRight(30);
        game_viewpager_indicator.setTabPaddingLeftRight(30);

        item_game_detail = View.inflate(getApplicationContext(), R.layout.item_game_detail, null);
        ViewUtils.inject(this, item_game_detail);

        trial_game = (Button) findViewById(R.id.trial_game);
        ll_title_bar_back = (LinearLayout) findViewById(R.id.ll_game_title_bar_back);
        ll_share = (LinearLayout) item_game_detail.findViewById(R.id.ll_share);
        ll_collect = (LinearLayout) item_game_detail.findViewById(R.id.ll_collect);

        trial_game.setOnClickListener(this);
        ll_title_bar_back.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_collect.setOnClickListener(this);

        gameViewPager = (ViewPager) findViewById(R.id.gameViewPager);
    }

    @Override
    protected void initData() {
        typeList = new ArrayList<>();
        typeList.add("详情");
        typeList.add("评论");
    }

    @Override
    protected void setListener() {

    }

    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {

        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments.clear();// 清空
        int count = typeList.size();
        for (int i = 0; i < count; i++) {
            switch (i) {
                case 0:
                    GameDetailFragment gameDetailFragment = new GameDetailFragment();
                    fragments.add(gameDetailFragment);
                    break;
                case 1:
                    GameCommentFragment gameCommentFragment = new GameCommentFragment();
                    fragments.add(gameCommentFragment);
                    break;
            }
        }

        PagerModelManager factory = new PagerModelManager();
        factory.addCommonFragment(fragments, typeList);
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), factory);
        gameViewPager.setAdapter(adapter);
        gameViewPager.setOnPageChangeListener(pageListener);
        game_viewpager_indicator.setViewPager(gameViewPager);
    }

    /**
     * ViewPager切换监听方法
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            gameViewPager.setCurrentItem(position);
            //selectTab(position);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_game_title_bar_back:
                finish();
                break;
            case R.id.trial_game:
                Toast.makeText(this, "试玩游戏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_share:
                mController.openShare(this, false);
                break;
            case R.id.ll_collect:
                Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

