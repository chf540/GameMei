package gamemei.qiyun.com.gamemei.fragment.menuFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;


/**
 * 社区界面
 *
 * @author hfcui
 */
public class GameGiftFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_gift, null);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        return null;
    }
}