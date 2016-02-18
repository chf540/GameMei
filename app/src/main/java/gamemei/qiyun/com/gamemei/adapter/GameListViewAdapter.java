package gamemei.qiyun.com.gamemei.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.qiyun.sdk.GMSdk;

import java.io.File;
import java.util.List;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.bean.PlayGameInfoBean;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;

public class GameListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private HttpHandler handler;
    private List<PlayGameInfoBean> gameList;
    /**
     * Xutils的Bitmap工具类
     */
    private BitmapUtils bitmapUtils;

    public GameListViewAdapter(Context context, List<PlayGameInfoBean> gameList) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.gameList = gameList;
        bitmapUtils = new BitmapUtils(mContext);
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            holder.iv_play_game = (ImageView) convertView
                    .findViewById(R.id.iv_play_game);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PlayGameInfoBean info = gameList.get(position);
        if (info != null) {
            info.games.get(position);
            holder.game_name.setText(info.games.get(position).game_name);
            //TODO 描述乱码待服务器修改数据
            holder.tv_game_desc.setText(info.games.get(position).game_name);
            bitmapUtils.display(holder.game_image, MyHttpUtils.PHOTOS_URL
                    + info.games.get(position).getGame_image_url());
        }
        // 下载游戏
        holder.iv_play_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String gameName = AppUtils.getSDPath() + "/gameMei/"
                            + info.games.get(position).game_name + ".zip";
                    File file = new File(gameName);
                    Log.i("hfcui----file", file.toString());
                    //如果游戏zip不存在下载游戏，如果存在的话打开游戏
                    if (!file.exists()) {
                        DownLoadFiles(
                                MyHttpUtils.DOWNLOAD_URL
                                        + info.games.get(position).game_download_url,
                                gameName);
                    } else {
                        Toast.makeText(mContext, "正在加载游戏",
                                Toast.LENGTH_SHORT).show();
                        if (GMSdk.share().initRuntime()
                                && GMSdk.share().installGameZip(
                                info.games.get(position).game_name,
                                gameName)) {
                            GMSdk.share().runGame(
                                    info.games.get(position).game_name);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        return convertView;
    }

    private final class ViewHolder {
        public ImageView game_image;
        public TextView game_name;
        public TextView tv_game_desc;
        public ImageView iv_play_game;
    }

    /**
     * 下载游戏
     */
    private void DownLoadFiles(String url, String path) {
        HttpUtils httpUtils = new HttpUtils();
        handler = httpUtils.download(url, path, true, true,
                new RequestCallBack<File>() {
                    ProgressDialog progressDialog = new ProgressDialog(mContext);

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {

                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Toast.makeText(mContext, "加载失败，请检查网络",
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
}