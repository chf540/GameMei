package gamemei.qiyun.com.gamemei.widget.rollviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import gamemei.qiyun.com.gamemei.R;


/**
 * Created by leo on 16/1/18.
 */
public class BinnerAdapter extends PagerAdapter {

    private Context context;
    private List<String> imgUrlList;
    private BitmapUtils bitmapUtils;

    public BinnerAdapter(Context context, List<String> imgUrlList) {
        this.context = context;
        this.imgUrlList = imgUrlList;
        this.bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return imgUrlList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // 加网络下载下来的图片
        View view = View.inflate(context, R.layout.viewpager_item, null);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        // 通过xutils提供的工具类异步下载缓存图片
        bitmapUtils.display(image, imgUrlList.get(position));
        container.addView(view);

        //点击事件。。。

        return view;
    }

}
