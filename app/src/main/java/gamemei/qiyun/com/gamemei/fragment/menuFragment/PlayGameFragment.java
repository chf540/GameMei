package gamemei.qiyun.com.gamemei.fragment.menuFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qiyun.sdk.GMSdk;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.bean.PlayGameInfoBean;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;
import gamemei.qiyun.com.gamemei.utils.SharedPreferencesUitl;
import gamemei.qiyun.com.gamemei.widget.draggridview.DragGrid;
import gamemei.qiyun.com.gamemei.widget.roundrectimageview.RoundRectImageView;
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
     * 全部分类View
     */
    private LinearLayout ll_game_Ranking;
    /**
     * 排行榜View
     */
    private LinearLayout ll_game_type;
    /**
     * 游戏筛选，游戏类型的GRIDVIEW
     */
    private DragGrid game_search_type;


    private MyAdapter madapter;
    private HttpHandler handler;
    private Handler mHandler;

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
        madapter = new MyAdapter(this);
        bitmapUtils = new BitmapUtils(context);

        tv_game_all_classify = (TextView) view.findViewById(R.id.tv_game_all_classify);
        tv_game_top = (TextView) view.findViewById(R.id.tv_game_top);
        game_on_line = (TextView) view.findViewById(R.id.game_on_line);
        game_choiceness = (TextView) view.findViewById(R.id.game_choiceness);
        ll_game_type = (LinearLayout) view.findViewById(R.id.ll_game_type);
        ll_game_Ranking = (LinearLayout) view.findViewById(R.id.ll_game_Ranking);

        tv_game_all_classify.setOnClickListener(this);
        tv_game_top.setOnClickListener(this);
        game_on_line.setOnClickListener(this);
        game_choiceness.setOnClickListener(this);
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
                getDate();
                madapter.notifyDataSetChanged();
                break;
            case R.id.game_choiceness:
                handlerFilter(1);
                getDate();
                madapter.notifyDataSetChanged();
                break;
            case R.id.tv_game_all_classify:
                ll_game_Ranking.setVisibility(View.GONE);
                //判断控件是否已展示
                int isVisibel = ll_game_type.getVisibility();
                if (isVisibel == 0) {
                    ll_game_type.setVisibility(View.GONE);
                    tv_game_all_classify.setTextColor(Color.rgb(36, 41, 51));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_all_classify.setCompoundDrawables(null, null, drawable, null);
                } else {
                    ll_game_type.setVisibility(View.VISIBLE);
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
                    tv_game_top.setTextColor(Color.rgb(36, 41, 51));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_top.setCompoundDrawables(null, null, drawable, null);
                } else {
                    ll_game_Ranking.setVisibility(View.VISIBLE);
                    tv_game_top.setTextColor(Color.rgb(10, 217, 178));
                    //设置箭头
                    Drawable drawable = getResources().getDrawable(R.mipmap.screen_pre);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_game_top.setCompoundDrawables(null, null, drawable, null);
                }
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
        xListView.setAdapter(madapter);
        xListView.setPullRefreshEnable(true); // 设置可以下拉刷新和上拉加载
        //如果数据太少则关闭上拉加载
        if (gameList.size() <= 4) {
            xListView.setPullLoadEnable(false);
        }
        xListView.setXListViewListener(this); // 设置监听事件
        // 设置条目可以被点击
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 点击跳转到游戏明细页面
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
                MyHttpUtils.BASE_URL, "");
        if (!TextUtils.isEmpty(result)) {
            processData(result, true);// 解析数据
        } else {
            // 如何本地没有缓存的数据则从网络获取数据
            getDate();
        }
    }

    /**
     * xUtil获取网络数据
     */
    private void getDate() {
        showLoading();
        requestData(HttpMethod.GET, MyHttpUtils.PLAY_GAME_LIST, null,
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
                        dismissLoading();//关闭loading界面
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
        madapter.notifyDataSetChanged();
    }

    /**
     * 下载游戏
     */
    private void DownLoadFiles(String url, String path) {
        HttpUtils httpUtils = new HttpUtils();
        handler = httpUtils.download(url, path, true, true,
                new RequestCallBack<File>() {
                    ProgressDialog progressDialog = new ProgressDialog(
                            getActivity());

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {

                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Toast.makeText(context, "下载失败，请检查网络",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        progressDialog.setMax(100); // 设置最大值为100
                        // 设置进度条风格STYLE_HORIZONTAL
                        progressDialog
                                .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setTitle("下载进度");// 设置title文字
                        progressDialog.show();
                        if (progressDialog.getProgress() >= 100) {
                            progressDialog.dismiss();
                        } else {
                            progressDialog
                                    .incrementProgressBy((int) ((double) current
                                            / (double) total * 100));
                        }
                    }
                });
    }

    /**
     * 下拉刷新界面
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameList.clear();
                getDate();
                madapter.notifyDataSetChanged();
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
                madapter.notifyDataSetChanged();
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

    /**
     * ListView 适配器
     *
     * @author hfcui
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(PlayGameFragment fragmentPage2) {
            this.mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return gameList.size();
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
        @SuppressLint("InflateParams")
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;
            // 如果缓存convertView为空，则需要创建View
            if (convertView == null) {
                holder = new ViewHolder();
                // 根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.item_playgame_list, null);
                holder.game_image = (ImageView) convertView
                        .findViewById(R.id.game_image);
                holder.game_name = (TextView) convertView
                        .findViewById(R.id.game_name);
                holder.tv_game_desc = (TextView) convertView
                        .findViewById(R.id.tv_game_desc);

                // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final PlayGameInfoBean info = gameList.get(position);
            if (info != null) {
                info.games.get(position);

                holder.game_name.setText(info.games.get(position).game_name);
                holder.tv_game_desc.setText(info.games.get(position).game_desc);

                bitmapUtils.display(holder.game_image, MyHttpUtils.PHOTOS_URL
                        + info.games.get(position).getGame_image_url());

            }
            // 下载游戏
//            holder.download.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    try {
//                        String gameName = AppUtils.getSDPath() + "/gameMei/"
//                                + info.games.get(position).game_name + ".zip";
//                        File file = new File(gameName);
//                        Log.i("hfcui----file", file.toString());
//                        if (!file.exists()) {
//                            DownLoadFiles(
//                                    MyHttpUtils.DOWNLOAD_URL
//                                            + info.games.get(position).game_download_url,
//                                    gameName);
//                        } else {
//                            Toast.makeText(context, "正在加载游戏",
//                                    Toast.LENGTH_SHORT).show();
//                            if (GMSdk.share().initRuntime()
//                                    && GMSdk.share().installGameZip(
//                                    info.games.get(position).game_name,
//                                    gameName)) {
//                                GMSdk.share().runGame(
//                                        info.games.get(position).game_name);
//                            }
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//            });
            return convertView;
        }
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView game_image;
        public TextView game_name;
        public TextView tv_game_desc;
        public TextView game_author;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("hfcui", "PlayGameView------onDestroyView");
    }
}