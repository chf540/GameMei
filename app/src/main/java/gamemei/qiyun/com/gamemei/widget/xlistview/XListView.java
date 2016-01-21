package gamemei.qiyun.com.gamemei.widget.xlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import gamemei.qiyun.com.gamemei.R;


public class XListView extends ListView {

    private static final String TAG = "XListView";

    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    // 滑动时长
    private final static int SCROLL_DURATION = 400;
    // 加载更多的距离
    private final static int PULL_LOAD_MORE_DELTA = 120;
    // 滑动比例
    private final static float OFFSET_RADIO = 2f;
    // 记录按下点的y坐标
    private float lastY;
    // 用来回滚
    private Scroller scroller;
    private IXListViewListener mListViewListener;
    private XListViewHeader headerView;
    private RelativeLayout headerViewContent;
    // header的高度
    private int headerHeight;
    // 是否能够刷新
    private boolean enableRefresh = true;
    // 是否正在刷新
    private boolean isRefreashing = false;
    // footer
    private XListViewFooter footerView;
    // 是否可以加载更多
    private boolean enableLoadMore;
    // 是否正在加载
    private boolean isLoadingMore;
    // 是否footer准备状态
    private boolean isFooterAdd = false;
    // total list items, used to detect is at the bottom of listview.
    private int totalItemCount;
    // 记录是从header还是footer返回
    private int mScrollBack;

    public XListView(Context context) {
        super(context);
        initView(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {

        scroller = new Scroller(context, new DecelerateInterpolator());

        headerView = new XListViewHeader(context);
        footerView = new XListViewFooter(context);

        headerViewContent = (RelativeLayout) headerView
                .findViewById(R.id.xlistview_header_content);
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        headerHeight = headerViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        addHeaderView(headerView);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // 确保footer最后添加并且只添加一次
        if (isFooterAdd == false) {
            isFooterAdd = true;
            addFooterView(footerView);
        }
        super.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        totalItemCount = getAdapter().getCount();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下的坐标
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算移动距离
                float deltaY = ev.getRawY() - lastY;
                lastY = ev.getRawY();
                // 是第一项并且标题已经显示或者是在下拉
                if (getFirstVisiblePosition() == 0
                        && (headerView.getVisiableHeight() > 0 || deltaY > 0)) {
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                } else if (getLastVisiblePosition() == totalItemCount - 1
                        && (footerView.getBottomMargin() > 0 || deltaY < 0)) {
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (getFirstVisiblePosition() == 0) {
                    if (enableRefresh
                            && headerView.getVisiableHeight() > headerHeight) {
                        isRefreashing = true;
                        headerView.setState(XListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == totalItemCount - 1) {
                    if (enableLoadMore
                            && footerView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {

        // 松手之后调用
        if (scroller.computeScrollOffset()) {

            if (mScrollBack == SCROLLBACK_HEADER) {
                headerView.setVisiableHeight(scroller.getCurrY());
            } else {
                footerView.setBottomMargin(scroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    public void setPullRefreshEnable(boolean enable) {
        enableRefresh = enable;

        if (!enableRefresh) {
            headerView.hide();
        } else {
            headerView.show();
        }
    }

    public void setPullLoadEnable(boolean enable) {
        enableLoadMore = enable;
        if (!enableLoadMore) {
            footerView.hide();
            footerView.setOnClickListener(null);
        } else {
            isLoadingMore = false;
            footerView.show();
            footerView.setState(XListViewFooter.STATE_NORMAL);
            footerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    public void stopRefresh() {
        if (isRefreashing == true) {
            isRefreashing = false;
            resetHeaderHeight();
        }
    }

    public void stopLoadMore() {
        if (isLoadingMore == true) {
            isLoadingMore = false;
            footerView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    private void updateHeaderHeight(float delta) {
        headerView.setVisiableHeight((int) delta
                + headerView.getVisiableHeight());
        // 未处于刷新状态，更新箭头
        if (enableRefresh && !isRefreashing) {
            if (headerView.getVisiableHeight() > headerHeight) {
                headerView.setState(XListViewHeader.STATE_READY);
            } else {
                headerView.setState(XListViewHeader.STATE_NORMAL);
            }
        }

    }

    private void resetHeaderHeight() {
        // 当前的可见高度
        int height = headerView.getVisiableHeight();
        // 如果正在刷新并且高度没有完全展示
        if ((isRefreashing && height <= headerHeight) || (height == 0)) {
            return;
        }
        // 默认会回滚到header的位置
        int finalHeight = 0;
        // 如果是正在刷新状态，则回滚到header的高度
        if (isRefreashing && height > headerHeight) {
            finalHeight = headerHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        // 回滚到指定位置
        scroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // 触发computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = footerView.getBottomMargin() + (int) delta;
        if (enableLoadMore && !isLoadingMore) {
            if (height > PULL_LOAD_MORE_DELTA) {
                footerView.setState(XListViewFooter.STATE_READY);
            } else {
                footerView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        footerView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = footerView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            scroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        isLoadingMore = true;
        footerView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }

    public interface IXListViewListener {

        public void onRefresh();

        public void onLoadMore();
    }

    public void setRefreshTime(String string) {

    }
}
