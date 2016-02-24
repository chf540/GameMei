package gamemei.qiyun.com.gamemei.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * 游戏详情-评论界面
 * Created by hfcui on 2016/2/18
 */
public class GameCommentFragment extends BaseFragment implements OnClickListener {
    /**
     * 日志标记
     */
    private String TAG = "GameCommentFragment";
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
    private View head_game_comment_view;
    /**
     * 底部评论按钮
     */
    private Button btn_game_comment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_comment, null);
        initView();
        initData();// 获取数据
        setXListView();
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        head_game_comment_view = View.inflate(getActivity(), R.layout.head_game_comment_view, null);
        ViewUtils.inject(this, head_game_comment_view);

        btn_game_comment = (Button) view.findViewById(R.id.btn_game_comment);
        btn_game_comment.setOnClickListener(this);
        return null;
    }

    /**
     * 设置XListView
     */
    private void setXListView() {
        xListView = (XListView) view.findViewById(R.id.game_comment_listView);
        madapter = new MyAdapter();
        xListView.setAdapter(madapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false); // 设置不用下拉刷新和加载更多
        xListView.addHeaderView(head_game_comment_view);
        // 设置条目可以被点击
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    /**
     * 按钮的点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_game_comment:
                Toast.makeText(getActivity(), "发布评论", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
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
            this.mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return 20;
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

