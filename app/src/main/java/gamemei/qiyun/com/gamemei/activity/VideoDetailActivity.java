package gamemei.qiyun.com.gamemei.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * Created by hfcui on 2016/1/25.
 */
public class VideoDetailActivity extends BaseActivity {
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
        xListView = (XListView) findViewById(R.id.information_listview);
        xListView.setAdapter(madapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true); // 设置可以下拉刷新和上拉加载
        //   xListView.setXListViewListener(this); // 设置监听事件
        // 设置条目可以被点击
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });
    }

    @Override
    protected void initView() {
        // mHandler = new Handler();
        madapter = new MyAdapter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

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
