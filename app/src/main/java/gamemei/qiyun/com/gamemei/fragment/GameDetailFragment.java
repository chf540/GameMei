package gamemei.qiyun.com.gamemei.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.GameDetailActivity;
import gamemei.qiyun.com.gamemei.activity.GameDownLoadActivity;
import gamemei.qiyun.com.gamemei.activity.ImageActivity;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;

/**
 * 游戏详情-详情界面
 * Created by hfcui on 2016/2/18
 */
public class GameDetailFragment extends BaseFragment implements OnClickListener {

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
    private LinearLayout ll_gamers_same;
    /**
     * 试玩按钮
     */
    private Button btn_trial_game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_details, null);
        initView();
        initData();// 获取数据
        return view;
    }

    @Override
    public void initData() {
        setRelevantGame();
        setGamersSame();
    }

    /**
     * 初始化控件
     */
    @Override
    public View initView() {
        ll_relevant_game = (LinearLayout) view.findViewById(R.id.ll_relevant_game);
        ll_gamers_same = (LinearLayout) view.findViewById(R.id.ll_gamers_same);
        btn_trial_game = (Button) view.findViewById(R.id.btn_trial_game);

        ll_game_Thumbnail = (LinearLayout) view.findViewById(R.id.ll_game_Thumbnail);
        expandableTextView = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
        expandableTextView.setText("    无敌流浪汉第五部登场了!在之前,特种兵部队没能追捕流浪汉,却让外星人给捉走了," +
                "这次要面对人造人强力的重型武器装备,和外星人,我们的流浪汉能否继续逃亡呢?大家拭目以待" +
                "无敌流浪汉第五部登场了在之前,特种兵部队没能追捕流浪汉,却让外星人给捉走了," +
                "这次要面对人造人强力的重型武器装备," +
                "和外星人,我们的流浪汉能否继续逃亡呢?");
        setGameThumbnail();

        btn_trial_game.setOnClickListener(this);
        return null;
    }

    /**
     * 设置游戏缩略图
     */
    private void setGameThumbnail() {
        for (int i = 0; i < 5; i++) {
            View gameThumbnail = LayoutInflater.from(getActivity()).inflate(
                    R.layout.information_gallery_item, null);
            ImageView icon = (ImageView) gameThumbnail
                    .findViewById(R.id.id_index_gallery_item_image);// 拿个这行的icon 就可以设置图片
            icon.setImageResource(R.mipmap.game_01);
            gameThumbnail.setOnClickListener(new OnClickListener() {// 每个item的点击事件加在这里
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ImageActivity.class));
                }
            });
            ll_game_Thumbnail.addView(gameThumbnail);
        }
    }

    /**
     * 相关推荐游戏
     */
    private void setRelevantGame() {
        for (int i = 0; i < 10; i++) {
            View relevantGame = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_hot_game, null);
            // 拿个这行的name 就可以设置文字
            TextView name = (TextView) relevantGame
                    .findViewById(R.id.tv_hot_game_name);
            TextPaint tp = name.getPaint();
            tp.setFakeBoldText(true);
            // 拿个这行的icon 就可以设置图片
            ImageView icon = (ImageView) relevantGame
                    .findViewById(R.id.iv_hot_game_icon);
            icon.setImageResource(R.mipmap.play);
            // 每个item的点击事件
            relevantGame.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), GameDetailActivity.class));
                }
            });
            ll_relevant_game.addView(relevantGame);
        }
    }

    /**
     * 设置玩过的玩家还玩的游戏
     */
    private void setGamersSame() {
        for (int i = 0; i < 10; i++) {
            View gamersSame = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_hot_game, null);
            // 拿个这行的name 就可以设置文字
            TextView name = (TextView) gamersSame
                    .findViewById(R.id.tv_hot_game_name);
            name.setTextColor(Color.parseColor("#596170"));
            TextPaint tp = name.getPaint();
            tp.setFakeBoldText(true);

            // 拿个这行的icon 就可以设置图片
            ImageView icon = (ImageView) gamersSame
                    .findViewById(R.id.iv_hot_game_icon);
            icon.setImageResource(R.mipmap.play);
            // 每个item的点击事件
            gamersSame.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), GameDetailActivity.class));
                }
            });
            ll_gamers_same.addView(gamersSame);
        }
    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_trial_game:
                Toast.makeText(getActivity(), "试玩游戏", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

