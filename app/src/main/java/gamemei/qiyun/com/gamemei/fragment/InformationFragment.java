package gamemei.qiyun.com.gamemei.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.widget.rollviewpager.BinnerPlugin;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * 资讯界面
 * Created by hfcui on 2016/1/10
 */
public class InformationFragment extends BaseFragment implements XListView.IXListViewListener {
    /**
     * 日志标记
     */
    private String TAG = "HotFragment";
    /**
     * 下拉刷新的ListView
     */
    private XListView xListView;
    /**
     * 界面适配器
     */
    private MyAdapter madapter;
    /**
     * ListView顶部的View
     */
    private View information_roll_view;
    /**
     * 放置顶部轮播图所在的线性布局
     */
    @ViewInject(R.id.top_viewpager)
    private LinearLayout top_viewpager;
    /**
     * 放置点所在的集合
     */
    private List<View> viewList = new ArrayList<View>();
    /**
     * 放置轮播图文字所在的textView
     */
    @ViewInject(R.id.top_information_title)
    private TextView top_information_title;
    /**
     * 放置轮播点所在的线性布局
     */
    @ViewInject(R.id.dots_ll)
    private LinearLayout dots_ll;
    /**
     * 存放轮播图文字的集合
     */
    private List<String> titleList = new ArrayList<String>();
    /**
     * 存放轮播图图片链接地址的集合
     */
    private List<String> imgUrlList = new ArrayList<String>();
    /**
     * 热推游戏 横划LinearLayout
     */
    private LinearLayout header_ll;
    private Handler mHandler;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_information, null);
        initView();
        initData();// 获取数据
        //设置ListView
        setXListView();
        return view;
    }

    /**
     * 设置XListView
     */
    private void setXListView() {
        xListView = (XListView) view.findViewById(R.id.information_listview);
        xListView.setAdapter(madapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true); // 设置可以下拉刷新和上拉加载
        xListView.setXListViewListener(this); // 设置监听事件
        xListView.addHeaderView(information_roll_view); // listView添加头布局
        // 设置条目可以被点击
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "点击的是" + position,
                        Toast.LENGTH_SHORT).show();
                // TODO 点击跳转到游戏明细页面
            }
        });
    }

    /**
     * 轮播图插件
     */
    BinnerPlugin binnerPlugin;

    @Override
    public void initData() {
        //设置轮播图
        titleList.add("1");
        titleList.add("2");
        titleList.add("3");
        imgUrlList.add("http://www.gamemei.com/background/uploads/160113/3-160113092603Q0.jpg");
        imgUrlList.add("http://www.gamemei.com/background/uploads/160112/3-1601121505012F.PNG");
        imgUrlList.add("http://www.gamemei.com/background/uploads/151229/1-1512291F549333.jpg");

        binnerPlugin = new BinnerPlugin(getActivity());
        binnerPlugin.init(top_viewpager);
        binnerPlugin.initDotView(imgUrlList.size(), dots_ll);
        binnerPlugin.initTitleList(titleList, top_information_title);
        binnerPlugin.setBinnerData(imgUrlList);
        binnerPlugin.start();
    }

    @Override
    public View initView() {
        mHandler = new Handler();
        madapter = new MyAdapter();
        // 关联顶部内容的布局文件
        information_roll_view = View.inflate(context, R.layout.layout_information_head_view, null);
        ViewUtils.inject(this, information_roll_view);
        header_ll = (LinearLayout) information_roll_view.findViewById(R.id.header_ll);
        return null;
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDate();
                madapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 获取数据
     */
    private void getDate() {

    }

    /**
     * 加载
     */
    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                madapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * ListView 适配器
     *
     * @author hfcui
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter() {
            this.mInflater = LayoutInflater.from(getActivity());
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
            convertView = mInflater.inflate(R.layout.item_top_information_list, null);
            return convertView;
        }
    }

    @Override
    public void onDestroyView() {
        //清空集合
        imgUrlList.clear();
        super.onDestroyView();
    }
}

