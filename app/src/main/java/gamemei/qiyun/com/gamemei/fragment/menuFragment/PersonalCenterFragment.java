package gamemei.qiyun.com.gamemei.fragment.menuFragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.widget.swiperefresh.MySwipereAdapter;
import gamemei.qiyun.com.gamemei.widget.swiperefresh.RefreshLayout;


public class PersonalCenterFragment extends BaseFragment implements OnRefreshListener,
        RefreshLayout.OnLoadListener {
    /**
     * 日志标记
     */
    private String TAG = "PersonalCenterFragment";
    private View view;
    private RefreshLayout swipeLayout;
    private ListView listView;
    private MySwipereAdapter adapter;
    private ArrayList<HashMap<String, String>> list;
    private View header;

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_center, null);
        header = getLayoutInflater(getArguments()).inflate(R.layout.listview_header,
                null);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        //设置刷新头的颜色
//        swipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        Log.i("TAG", "---onCreateView");
        setData();
        setListener();
        return view;
    }

    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    private void setData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            list.add(new HashMap<String, String>());
        }

        listView = (ListView) view.findViewById(R.id.list);
        listView.addHeaderView(header);
        adapter = new MySwipereAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据
                // 更新完后调用该方法结束刷新
                swipeLayout.setRefreshing(false);
            }
        }, 1000);

    }

    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据
                // 更新完后调用该方法结束刷新
                swipeLayout.setLoading(false);
            }
        }, 1000);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("TAG", "---onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View initView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }
}