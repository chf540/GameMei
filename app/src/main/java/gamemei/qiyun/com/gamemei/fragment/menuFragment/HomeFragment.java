package gamemei.qiyun.com.gamemei.fragment.menuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.AttentionActivity;
import gamemei.qiyun.com.gamemei.activity.LoginActivity;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.fragment.ExperienceFragment;
import gamemei.qiyun.com.gamemei.fragment.HotFragment;
import gamemei.qiyun.com.gamemei.fragment.InformationFragment;
import gamemei.qiyun.com.gamemei.fragment.MissionFragment;
import gamemei.qiyun.com.gamemei.fragment.QAFragment;
import gamemei.qiyun.com.gamemei.fragment.VideoFragment;
import gamemei.qiyun.com.gamemei.widget.slidingtab.PagerSlidingTabStrip;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

public class HomeFragment extends BaseFragment implements OnClickListener {

    private String TAG = "HomeFragment";
    /**
     * 设置我的关注
     */
    protected LinearLayout ll_subscription;

    protected ImageView button_subscription;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    protected ViewPager mViewPager;
    private View view;

    /**
     * 用户选择的顶部分类列表
     */
    protected static ArrayList<String> userChannelList;
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;

    private PagerSlidingTabStrip pagerSlidingTabStrip;

    Timer timer = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "hfcui =======HomeFragment===onCreateView");
        view = inflater.inflate(R.layout.fragment_home, null);
        initView();
        initData();
        setChangelView();
        showLoading();
        //定时器  1S后关闭loading界面
        if (timer != null) {
            timer.cancel();
        } else {
            timer = new Timer();
            timer.schedule(task, 1000);
        }
        return view;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dismissLoading();
            }
            super.handleMessage(msg);
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


    @Override
    public View initView() {
        /**************************************/
        pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.viewpagerindicator);
        pagerSlidingTabStrip.setTabPaddingLeftRight(30);
        /**************************************/

        ll_subscription = (LinearLayout) view.findViewById(R.id.ll_subscription);
        button_subscription = (ImageView) view.findViewById(R.id.button_subscription);
        ll_subscription.setOnClickListener(this);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        return null;
    }

    /**
     * 当栏目项发生变化时候调用
     */
    private void setChangelView() {
        initColumnData();
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
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            switch (i) {
                case 0:
                    HotFragment hotFragment = new HotFragment();
                    fragments.add(hotFragment);
                    break;
                case 1:
                    InformationFragment informationFragment = new InformationFragment();
                    fragments.add(informationFragment);
                    break;
                case 2:
                    VideoFragment videoFragment = new VideoFragment();
                    fragments.add(videoFragment);
                    break;
                case 3:
                    MissionFragment missionFragment = new MissionFragment();
                    fragments.add(missionFragment);
                    break;
                case 4:
                    QAFragment qaFragment = new QAFragment();
                    fragments.add(qaFragment);
                    break;
                case 5:
                    ExperienceFragment experienceFragment = new ExperienceFragment();
                    fragments.add(experienceFragment);
                    break;
            }
        }

        PagerModelManager factory = new PagerModelManager();
        factory.addCommonFragment(fragments, userChannelList);
        ModelPagerAdapter adapter = new ModelPagerAdapter(getFragmentManager(), factory);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(pageListener);
        pagerSlidingTabStrip.setViewPager(mViewPager);
    }

    /**
     * ViewPager切换监听方法
     */
    public OnPageChangeListener pageListener = new OnPageChangeListener() {

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageSelected(int position) {
                    mViewPager.setCurrentItem(position);
                    //selectTab(position);
                }
            };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initData() {
        //顶部标签添加固定的条目
        userChannelList = new ArrayList<>();
        userChannelList.add("热门");
        userChannelList.add("资讯");
        userChannelList.add("视频");
        userChannelList.add("任务");
        userChannelList.add("问答");
        userChannelList.add("经验");
    }

    int count = 0;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //跳转到订阅界面
            case R.id.ll_subscription:
                //如果没有登录则跳转到登录界面，已经登录的跳转到订阅界面
                count++;
                if (count % 2 == 1) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), AttentionActivity.class));
                }
        }
    }
}
