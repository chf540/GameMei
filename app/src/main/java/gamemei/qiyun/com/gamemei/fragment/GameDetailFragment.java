package gamemei.qiyun.com.gamemei.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;

/**
 * 游戏详情-详情界面
 * Created by hfcui on 2016/2/18
 */
public class GameDetailFragment extends BaseFragment {

    /**
     * 日志标记
     */
    private String TAG = "GameDetailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_details, null);
        initView();
        initData();// 获取数据
        return view;
    }

    @Override
    public void initData() {

    }


    @Override
    public View initView() {
        return null;
    }

    /**
     * 获取数据
     */
    private void getDate() {

    }
}

