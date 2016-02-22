package gamemei.qiyun.com.gamemei.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;

/**
 * 游戏详情-详情界面
 * Created by hfcui on 2016/2/18
 */
public class GameDetailFragment extends BaseFragment {

    View view;
    /**
     * 日志标记
     */
    private String TAG = "GameDetailFragment";
    /**
     * 游戏介绍Textview,点击展开，隐藏
     */
    ExpandableTextView expandableTextView;
    /**
     * 游戏缩略图
     */
    private LinearLayout ll_game_Thumbnail;
    /**
     * 相关游戏
     */
    private LinearLayout ll_relevant_game;
    /**
     * 玩过的玩家还玩
     */
    private LinearLayout ll_gameer_same;

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

    /**
     * 初始化控件
     */
    @Override
    public View initView() {
        ll_game_Thumbnail = (LinearLayout) view.findViewById(R.id.ll_game_Thumbnail);
        expandableTextView = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
        expandableTextView.setText("    无敌流浪汉第五部登场了!在之前,特种兵部队没能追捕流浪汉,却让外星人给捉走了," +
                "这次要面对人造人强力的重型武器装备,和外星人,我们的流浪汉能否继续逃亡呢?大家拭目以待" +
                "无敌流浪汉第五部登场了在之前,特种兵部队没能追捕流浪汉,却让外星人给捉走了," +
                "这次要面对人造人强力的重型武器装备," +
                "和外星人,我们的流浪汉能否继续逃亡呢?");
        setGameThumbnail();
        return null;
    }

    /**
     * 设置游戏缩略图
     */
    private void setGameThumbnail() {
        for (int i = 0; i < 5; i++) {
            View coupon_home_ad_item = LayoutInflater.from(getActivity()).inflate(
                    R.layout.information_gallery_item, null);
            ImageView icon = (ImageView) coupon_home_ad_item
                    .findViewById(R.id.id_index_gallery_item_image);// 拿个这行的icon 就可以设置图片
            icon.setImageResource(R.drawable.legend);
            coupon_home_ad_item.setOnClickListener(new View.OnClickListener() {// 每个item的点击事件加在这里

                @Override
                public void onClick(View v) {

                }
            });
            ll_game_Thumbnail.addView(coupon_home_ad_item);
        }
    }

    /**
     * 获取数据
     */
    private void getDate() {

    }
}

