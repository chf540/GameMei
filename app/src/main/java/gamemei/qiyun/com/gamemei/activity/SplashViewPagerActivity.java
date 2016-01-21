package gamemei.qiyun.com.gamemei.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.adapter.ViewPagerAdapter;


/**
 * 引导页面，首次登陆显示，在FirstActivity用SharedPreferences记录状态，再次登陆时不显示。
 *
 * @author hfcui
 */
public class SplashViewPagerActivity extends Activity implements
        OnClickListener, OnPageChangeListener {
    /**
     * 日志标记
     */
    private final String TAG = "SplashViewPagerActivity";
    /**
     * 引导页滑动的viewPager
     */
    private ViewPager viewPager;
    /**
     * viewpager的adapter
     */
    private ViewPagerAdapter vpAdapter;
    /**
     * 展示可滑动图片的view
     */
    private List<View> views;
    /**
     * 点击进入的button
     */
    private Button button;

    /**
     * 引导图片资源
     */
    private static final int[] pics = {R.drawable.splash_1,
            R.drawable.splash_2, R.drawable.splash_3,};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcom);
        initData();
    }

    /**
     * 初始化引导页数据
     */
    private void initData() {
        button = (Button) findViewById(R.id.button);
        views = new ArrayList<>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int pic : pics) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pic);
            views.add(iv);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        viewPager.setAdapter(vpAdapter);
        // 绑定回调
        viewPager.setOnPageChangeListener(this);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //跳转到主界面
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 如果当前的界面是第三个view则显示出进入按钮
     */
    @Override
    public void onPageSelected(int arg0) {
        if (arg0 == 2) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
    }
}