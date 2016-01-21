package gamemei.qiyun.com.gamemei.widget.rollviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gamemei.qiyun.com.gamemei.R;


/**
 * binner 插件 控制 RollViewPager2 ，dotsview , 等其他控件显示
 * Created by leo on 16/1/18.
 */
public class BinnerPlugin {

    private Context mContext;
    private RollViewPager viewpager;
    private TextView top_news_title;
    private List<String> titleList;

    private BinnerAdapter adapter;

    private LinearLayout dots_ll;

    private List<View> viewList = new ArrayList<View>();

    public BinnerPlugin(Context context) {
        this.mContext = context;
    }

    public void init(LinearLayout view) {
        initRollviewPager(view);
    }

    public void initRollviewPager(LinearLayout view) {
        viewpager = new RollViewPager(mContext);
        view.removeAllViews();
        view.addView(viewpager);
    }

    public void initTitleList(List<String> titleList, TextView view) {
        if (titleList != null && view != null && titleList.size() > 0) {
            top_news_title = view;
            // 默认值
            top_news_title.setText(titleList.get(0));
            this.titleList = titleList;
        }
    }

    public void initDotView(int size, LinearLayout dots) {
        dots_ll = dots;
        dots_ll.removeAllViews();
        viewList.clear();
        //通过集合中图片的个数创建点的个数
        for (int i = 0; i < size; i++) {
            View view = new View(mContext);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.dot_black);
            } else {
                view.setBackgroundResource(R.drawable.dot_white);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);// 1,定义点的宽高
            layoutParams.setMargins(0, 0, 10, 0); // 2,给点设置间距
            // 3,作用当前规则给子控件
            dots_ll.addView(view, layoutParams);
            viewList.add(view);
        }
    }


    public void setBinnerData(List<String> imgUrlList) {
        adapter = new BinnerAdapter(mContext, imgUrlList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(viewpager.getCurrentItem());
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewpager.setOnPageChangeListener(new RollviewPagerChangeListener(imgUrlList.size()));
    }

    /**
     * RollviewPager 与 dotsview 通信
     */
    private class RollviewPagerChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;
        private int size;

        RollviewPagerChangeListener(int size) {
            this.size = size;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        /**
         * 固定条目
         *
         * @param position
         */
//        @Override
//        public void onPageSelected(int position) {
//            viewList.get(oldPosition).setBackgroundResource(
//                    R.drawable.dot_white);
//            viewList.get((position + 5) % size).setBackgroundResource(
//                    R.drawable.dot_black);
//            oldPosition = (position + 5) % size;
//            Log.e("position", (position + 5) % size + "");
//            top_news_title.setText(titleList.get((position + 5) % size));
//        }

        /**
         * 从后台动态获取条目
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            viewList.get(oldPosition).setBackgroundResource(
                    R.drawable.dot_white);
            viewList.get((position + size - 1) % size).setBackgroundResource(
                    R.drawable.dot_black);
            oldPosition = (position + size - 1) % size;
            //  Log.e("position", (position + size - 1) % size + "");
            top_news_title.setText(titleList.get((position + size - 1) % size));
        }
    }

    /**
     * 开始自动滑动
     */
    public void start() {
        viewpager.startAutoScroll(3000);
    }

    /**
     * 停止自动滑动
     */
    public void stop() {
        viewpager.stopAutoScroll();
    }

    /**
     * 刷新数据
     */
    public void fresh() {
        //getAdData();
    }


    public void destroy() {

    }
}
