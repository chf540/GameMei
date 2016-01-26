package gamemei.qiyun.com.gamemei.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;

/**
 * Created by hfcui on 2016/1/25.
 */
public class PlayVideoActivity extends BaseActivity {

    /**
     * 控制器
     */
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("http://www.gamemei.com/hfcuird_qiyun/viewdemo.mp4");

        /***
         * 将播放器关联上一个音频或者视频文件
         * videoView.setVideoURI(Uri uri)
         * videoView.setVideoPath(String path)
         */
        videoView.setVideoURI(uri);
        //  videoView.setVideoPath("data/yueding.mp3");

        /**
         * w为其提供一个控制器，控制其暂停、播放……等功能
         */
        mMediaController = new MediaController(this);
        videoView.setMediaController(mMediaController);

        /**
         * 设置全屏播放
         */
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);

        /**
         * 开始播放
         */
        videoView.start();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
