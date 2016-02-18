package gamemei.qiyun.com.gamemei.fragment.menuFragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qiyun.sdk.GMSdk;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.GameDetailActivity;
import gamemei.qiyun.com.gamemei.activity.GameDownLoadActivity;
import gamemei.qiyun.com.gamemei.adapter.GameListViewAdapter;
import gamemei.qiyun.com.gamemei.bean.PlayGameInfoBean;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;
import gamemei.qiyun.com.gamemei.utils.SharedPreferencesUitl;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * 玩游戏界面
 *
 * @author hfcui 2016年2月15日
 */
public class PlayGameFragment extends BaseFragment implements XListView.IXListViewListener, OnClickListener {
    /**
     * 日志标记
     */
    private String TAG = "PlayGameFragment";

    private View view;
    /**
     * 下拉刷新的ListView
     */
    private XListView xListView;
    /**
     * 游戏列表
     */
    private List<PlayGameInfoBean> gameList = new ArrayList<PlayGameInfoBean>();
    /**
     * Xutils的Bitmap工具类
     */
    private BitmapUtils bitmapUtils;
    /**
     * 在线即玩
     */
    private TextView game_on_line;
    /**
     * 精选下载
     */
    private TextView game_choiceness;
    /**
     * 全部分类
     */
    private TextView tv_game_all_classify;
    /**
     * 排行榜
     */
    private TextView tv_game_top;
    /**
     * 下载按钮
     */
    private RelativeLayout rl_game_download;
    /**
     * 全部分类View
     */
    private LinearLayout ll_game_Ranking;
    /**
     * 排行榜View
     */
    private LinearLayout ll_game_type;

    private HttpHandler handler;
    private Handler mHandler;
    private GameListViewAdapter gameListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playgame, null);
        // 初始化gameMeiSDK
        GMSdk.share().init(getActivity());
        initView();
        initData();// 获取数据
        SetXListView();// 设置XListView
        showLoading();
        return view;
    }

    @Override
    public void onResume() {
        initData();// 获取数据
        super.onResume();
    }

    @Override
    public View initView() {
        mHandler = new Handler();
        bitmapUtils = new BitmapUtils(getActivity());

        tv_game_all_classify = (TextView) view.findViewById(R.id.tv_game_all_classify);
        tv_game_top = (TextView) view.findViewById(R.id.tv_game_top);
        game_on_line = (TextView) view.findViewById(R.id.game_on_line);
        game_choiceness = (TextView) view.findViewById(R.id.game_choiceness);
        ll_game_type = (LinearLayout) view.findViewById(R.id.ll_game_type);
        ll_game_Ranking = (LinearLayout) view.findViewById(R.id.ll_game_Ranking);
        rl_game_download = (RelativeLayout) view.findViewById(R.id.rl_game_download);

        tv_game_all_classify.setOnClickListener(this);
        tv_game_top.setOnClickListener(this);
        game_on_line.setOnClickListener(this);
        game_choiceness.setOnClickListener(this);
        rl_game_download.setOnClickListener(this);
        initFilter();

        return null;
    }

    /**
     * 初始化导航过滤器
     */
    private void initFilter() {
        game_on_line.setTextColor(context.getResources().getColor(R.color.white));
        game_on_line.setBackgroundResource(R.drawable.nofinish_filter_pressed_bg);

        game_choiceness.setTextColor(context.getResources().getColor(R.color.white));
        game_choiceness.setBackgroundResource(R.drawable.finished_filter_normal_bg);
    }

    /**
     * 处理选中的导航过滤器
     *
     * @param position
     */
    private void handlerFilter(int position) {
        switch (position) {
            case 0:
                game_on_line.setTextColor(context.getResources().getColor(R.color.white));
                game_on_line.setBackgroundResource(R.drawable.nofinish_filter_pressed_bg);
                game_choiceness.setTextColor(context.getResources().getColor(R.color.white));
                game_choiceness.setBackgroundResource(R.drawable.finished_filter_normal_bg);
                break;
            case 1:
                game_on_line.setTextColor(context.getResources().getColor(R.color.white));
                game_on_line.setBackgroundResource(R.drawable.nofinish_filter_normal_bg);
                game_choiceness.setTextColor(context.getResources().getColor(R.color.white));
                game_choiceness.setBackgroundResource(R.drawable.finished_filter_pressed_bg);
                break;
            default:
                break;
        }
    }

    /**
     * 处理按钮的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.game_on_line:
                handlerFilter(0);
                getDate(MyHttpUtils.ALL_GAME_TYPE);
                gameListViewAdapter.notifyDataSetChanged();
                xListView.setSelection(1);
                break;
            case R.id.game_choiceness:
                handlerFilter(1);
                getDate(MyHttpUtils.GAME_Top);
                gameListViewAdapter.notifyDataSetChanged();
                xListView.setSelection(1);
                break;
            case R.id.tv_game_all_classify:
                ll_game_Ranking.setVisibility(View.GONE);
                //判断控件是否已展示
                int isVisibel = ll_game_type.getVisibility();
                if (isVisibel == 0) {
                    ll_game_type.setVisibility(View.GONE);
                    //设置文字颜色
                    tv_game_all_classify.setTextColor(Color.rgb(36, 41, 51));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_all_classify.setCompoundDrawables(null, null, drawable, null);
                } else {
                    ll_game_type.setVisibility(View.VISIBLE);
                    //设置文字颜色
                    tv_game_all_classify.setTextColor(Color.rgb(10, 217, 178));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen_pre);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_all_classify.setCompoundDrawables(null, null, drawable, null);
                }
                break;
            case R.id.tv_game_top:
                ll_game_type.setVisibility(View.GONE);
                //判断控件是否已展示
                int isVisibel1 = ll_game_Ranking.getVisibility();
                if (isVisibel1 == 0) {
                    ll_game_Ranking.setVisibility(View.GONE);
                    //设置文字颜色
                    tv_game_top.setTextColor(Color.rgb(36, 41, 51));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_top.setCompoundDrawables(null, null, drawable, null);
                } else {
                    ll_game_Ranking.setVisibility(View.VISIBLE);
                    //设置文字颜色
                    tv_game_top.setTextColor(Color.rgb(10, 217, 178));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen_pre);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_top.setCompoundDrawables(null, null, drawable, null);
                }
                break;
            case R.id.rl_game_download:
                startActivity(new Intent(getActivity(), GameDownLoadActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 设置XListView
     */
    public void SetXListView() {
        xListView = (XListView) view.findViewById(R.id.game_listview);
        xListView.setAdapter(gameListViewAdapter = new GameListViewAdapter(this.getActivity(), gameList));
        xListView.setPullRefreshEnable(true); // 设置可以下拉刷新和上拉加载
        //如果数据太少则关闭上拉加载
        if (gameList.size() <= 20) {
            xListView.setPullLoadEnable(false);
        }
        xListView.setXListViewListener(this); // 设置监听事件
        // 设置条目可以被点击
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 点击跳转到游戏明细页面
                startActivity(new Intent(getActivity(), GameDetailActivity.class));
            }
        });
    }

    /**
     * 获取网络数据填充UI
     */
    @Override
    public void initData() {
        dismissLoading();
        // 首先获取服务器端的数据缓存正在本地，再去服务器端拿取最新的数据。
        String result = SharedPreferencesUitl.getStringData(context,
                MyHttpUtils.ALL_GAME_TYPE, "");
        if (!TextUtils.isEmpty(result)) {
            processData(result, true);// 解析数据
        } else {
            // 如何本地没有缓存的数据则从网络获取数据
            getDate(MyHttpUtils.ALL_GAME_TYPE);
        }
    }

    /**
     * xUtil获取网络数据
     */
    public void getDate(String url) {
        requestData(HttpMethod.GET, url, null,
                new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException arg0,
                                          String responseInfo) {
                        Toast.makeText(context, "网络出错，请检查网络",
                                Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // 获取成功返回的json串
                        String result = responseInfo.result;
                        // 本地化存储
                        SharedPreferencesUitl.saveStringData(getActivity(),
                                MyHttpUtils.BASE_URL, result);
                        // 解析数据
                        processData(result, true);
                    }
                });
    }

    /**
     * 解析数据 ----GSON
     */
    private void processData(String result, boolean a) {
        // 对照bean解析json
        Gson gson = new Gson();
        PlayGameInfoBean infoBean = gson.fromJson(result,
                PlayGameInfoBean.class);
        gameList.clear();
        for (int i = 0; i < infoBean.games.size(); i++) {
            gameList.add(i, infoBean);
        }
        gameListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 下拉刷新界面
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // gameList.clear();
                // getDate();
                gameListViewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameListViewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("hfcui", "PlayGameView------onDestroyView");
    }
}