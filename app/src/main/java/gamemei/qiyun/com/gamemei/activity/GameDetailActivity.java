package gamemei.qiyun.com.gamemei.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import gamemei.qiyun.com.gamemei.widget.slidingtab.PagerSlidingTabStrip;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

/**
 * Created by hfcui on 2016/2/18
 */
public class GameDetailActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initColumnData();
    }

    @Override
    protected void initView() {
        game_viewpager_indicator = (PagerSlidingTabStrip) findViewById(R.id.game_viewpager_indicator);
        game_viewpager_indicator.setTabPaddingLeftRight(30);
        game_viewpager_indicator.setTabPaddingLeftRight(30);

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
}

