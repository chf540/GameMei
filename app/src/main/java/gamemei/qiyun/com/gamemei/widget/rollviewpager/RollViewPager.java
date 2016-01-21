package gamemei.qiyun.com.gamemei.widget.rollviewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * 当向右翻页（文中指的向右翻页是指手指由右向左滑，向左翻页反之），
 * 数据滑动到最后一条数据时调用setCurrentItem（0）返回第一条，当向左翻页，数据滑
 * 动到第一条数据时setCurrentItem（getCount()-1）返回到最后一条数据。在设置数据
 * 的时候需要多生成两条数据，比如原始数据是[a,b,c,d,e]，那么生成的数据为[e,a,b,c,d,e,a],
 * 原始数据第一条前面加了最后一条数据e，原始数据的最后一条的后面加了原始数据的第一条数据a。
 * 从a滑到e时，继续向右翻页那么就到了数据a（最右边的那个a），停止滑动时调用setCurrentItem（1）
 * 返回第一条数据a（最左边的那个a）；从e滑动到a时，继续向左翻页那么就到了数据e（做左边的那个e），
 * 停止滑动时调用setCurrentItem（5）。
 * Created by leo on 16/1/18.
 */
public class RollViewPager extends AutoScrollViewPager {
    private RollViewAdapter mAdapter;

    public RollViewPager(Context context) {
        super(context);
        setOnPageChangeListener(null);
    }

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPageChangeListener(null);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter.getCount() <= 0)) {
            mAdapter = new RollViewAdapter(adapter);
            super.setAdapter(mAdapter);
            setCurrentItem(1);
        } else {
            super.setAdapter(mAdapter);
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(new InnerOnPageChangeListener(listener));
    }

    private class InnerOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;
        private int mPosition;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            if (null != listener) {
                listener.onPageScrollStateChanged(state);
            }
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mPosition == mAdapter.getCount() - 1) {
                    setCurrentItem(1, false);
                } else if (mPosition == 0) {
                    setCurrentItem(mAdapter.getCount() - 2, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (null != listener) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }


        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            if (null != listener) {
                listener.onPageSelected(position);
            }
        }
    }

    private class RollViewAdapter extends PagerAdapter {

        private PagerAdapter adapter;

        public RollViewAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
            adapter.registerDataSetObserver(new DataSetObserver() {

                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        public int getCount() {
            return adapter.getCount() + 2;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return adapter.isViewFromObject(view, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                position = adapter.getCount() - 1;
            } else if (position == adapter.getCount() + 1) {
                position = 0;
            } else {
                position -= 1;
            }
            return adapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem(container, position, object);
        }

    }
}
