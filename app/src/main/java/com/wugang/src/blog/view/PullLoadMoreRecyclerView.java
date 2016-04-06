package com.wugang.src.blog.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.wugang.src.blog.R;


/**
 * Created by WuXiaolong on 2015/7/2.
 */
public class PullLoadMoreRecyclerView extends LinearLayout {
    private EmptyRecyclerView mRecyclerView;
    private CustomEmptyView mEmptyView;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private LinearLayout mFooterView;
    private Context mContext;
    private int currentStatu;

    public PullLoadMoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.pull_loadmore_layout,
                        null);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.wuxiaolong.pullloadmorerecyclerview.R.id.swipeRefreshLayout);
        //mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        //mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));

        mRecyclerView = (EmptyRecyclerView) view
                .findViewById(R.id.recyclerView);
        mEmptyView = (CustomEmptyView) view.findViewById(R.id.mEmptyView);
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(
        //getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScroll(this));
        //Solve IndexOutOfBoundsException exception
        mRecyclerView.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (isRefresh) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );

        mFooterView = (LinearLayout) view
                .findViewById(R.id.footer_linearlayout);
        mFooterView.setVisibility(View.GONE);
        this.addView(view);
    }


    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }


    /**
     * StaggeredGridLayoutManager
     */

    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }


    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    public void loadMore() {
        if (mPullLoadMoreListener != null && hasMore) {
            mFooterView.setVisibility(View.VISIBLE);
            mPullLoadMoreListener.onLoadMore();
        }
    }


    public void setPullLoadMoreCompleted() {
        isLoadMore = false;
        mFooterView.setVisibility(View.GONE);
    }


    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }

    //public void refresh()
    //{
    //    mRecyclerView.setVisibility(View.VISIBLE);
    //    if ( mPullLoadMoreListener != null )
    //    {
    //        mPullLoadMoreListener.onRefresh();
    //    }
    //}

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public interface PullLoadMoreListener {
        //public void onRefresh();

        public void onLoadMore();
    }

    public void setEmptyViewOnclick(OnClickListener click) {
        if (currentStatu == STATU_FAILS) {
            mEmptyView.setOnClickListener(click);
        }
    }

    public void setEmptyStatu(int statu) {
        currentStatu = statu;
        switch (statu) {
            case STATU_FAILS:
                mEmptyView.setLoadFailure();
                break;
            case STATU_NULL:
                mEmptyView.setLoadDataNull();
                break;
            case STATU_RELOAD:
                mEmptyView.setBarVisibility();
                break;
            case STATU_SUCCESS:
                mEmptyView.setGone();
                break;
        }
    }

    public void setEmptyClick(OnClickListener onclick) {
        mEmptyView.setOnClickListener(onclick);
    }

    public static final int STATU_FAILS = 0x10;//加载失败

    public static final int STATU_NULL = 0x11;//没有数据

    public static final int STATU_RELOAD = 0x12;//重新加载数据
    public static final int STATU_SUCCESS = 0x13;//加载成功
}
