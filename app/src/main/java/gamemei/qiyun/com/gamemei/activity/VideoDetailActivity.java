package gamemei.qiyun.com.gamemei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * Created by hfcui on 2016/1/25.
 */
public class VideoDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 日志标记
     */
    private String TAG = "VideoDetailActivity";
    /**
     * 评论列表项
     */
    private XListView xListView;
    /**
     * listview适配器
     */
    private MyAdapter madapter;
    /**
     * ListView顶部的View
     */
    private View activity_head_video_view;
    /**
     * 点击播放视频
     */
    private ImageView iv_play;
    /**
     * 视频缩略图
     */
    private ImageView video_picture;
    /**
     * 返回按钮
     */
    private ImageView title_bar_back;
    /**
     * 发布评论按钮
     */
    private Button video_detail_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        //设置XListView
        setXListView();
    }

    /**
     * 设置XListView
     */
    private void setXListView() {
        xListView = (XListView) findViewById(R.id.video_detail_listview);
        xListView.setAdapter(madapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false); // 设置不用下拉刷新和加载更多
        // 设置条目可以被点击
        xListView.addHeaderView(activity_head_video_view); // listView添加头布局
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //  Toast.makeText(getApplication(), position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        madapter = new MyAdapter();
        activity_head_video_view = View.inflate(getApplicationContext(), R.layout.activity_head_video_view, null);
        ViewUtils.inject(this, activity_head_video_view);
        video_picture = (ImageView) activity_head_video_view.findViewById(R.id.video_picture);
        iv_play = (ImageView) activity_head_video_view.findViewById(R.id.iv_play);
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        video_detail_comment = (Button) findViewById(R.id.video_detail_comment);
        iv_play.setOnClickListener(this);
        title_bar_back.setOnClickListener(this);
        video_detail_comment.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        //设置展示图片
        video_picture.setImageResource(R.drawable.picture_demo);
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {

    }

    /**
     * 处理点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                Intent playVideo = new Intent(this, PlayVideoActivity.class);
                startActivity(playVideo);
                break;
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.video_detail_comment:
                Toast.makeText(getApplicationContext(), "发布评论", Toast.LENGTH_SHORT).show();
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
