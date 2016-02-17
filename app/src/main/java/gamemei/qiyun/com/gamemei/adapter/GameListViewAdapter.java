package gamemei.qiyun.com.gamemei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.bean.PlayGameInfoBean;
import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;

public class GameListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<PlayGameInfoBean> gameList;
    /**
     * Xutils的Bitmap工具类
     */
    private BitmapUtils bitmapUtils;

    public GameListViewAdapter(Context context, List<PlayGameInfoBean> gameList) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.gameList = gameList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            holder.tv_game_desc.setText(info.games.get(position).game_desc);

            bitmapUtils.display(holder.game_image, MyHttpUtils.PHOTOS_URL
                    + info.games.get(position).getGame_image_url());
        }
        return convertView;
    }

    private final class ViewHolder {
        public ImageView game_image;
        public TextView game_name;
        public TextView tv_game_desc;
        public ImageView iv_play_game;
    }
}