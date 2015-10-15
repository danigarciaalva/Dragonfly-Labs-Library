package io.dflabs.lib.mvp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import io.dflabs.lib.ui.SuperRecyclerView;

/**
 * Created by Daniel García Alvarado on 8/27/15.
 * Gastalon - danielgarcia
 */
@SuppressWarnings("unused")
public abstract class RecyclerFragment<T, R extends RecyclerView.Adapter &
        RecyclerFragment.SuperRecyclerAdapter<T>> extends BaseFragment {

    protected RecyclerView mRecyclerView;
    TextView mEmptyTextView;
    FrameLayout mEmptyLayout;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    protected R mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SuperRecyclerView superRecyclerView = (SuperRecyclerView) view.
                findViewWithTag(SuperRecyclerView.SUPER_RECYCLER_VIEW_TAG);
        if (superRecyclerView == null) {
            throw new InflateException("You must add <io.dflabs.lib.ui.SuperRecyclerView /> in your view");
        }
        mRecyclerView = superRecyclerView.getRecyclerView();
        mEmptyTextView = superRecyclerView.getEmptyTextView();
        mEmptyLayout = superRecyclerView.getEmptyLayout();
        mSwipeRefreshLayout = superRecyclerView.getSwipeToRefreshLayout();
    }

    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mRecyclerView.setLayoutManager(layoutManager);
    }

    protected void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.mRecyclerView.setItemAnimator(itemAnimator);
    }

    protected void setEmptyView(View v) {
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(v);
    }

    protected void setEmptyText(int text) {
        mEmptyTextView.setText(text);
    }

    protected void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
        this.mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    protected void setAdapter(R adapter) {
        this.mAdapter = adapter;
        this.mRecyclerView.setAdapter(mAdapter);
    }

    protected void addItems(List<T> collection) {
        mEmptyLayout.setVisibility(collection.size() == 0 ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(collection.size() > 0 ? View.VISIBLE : View.GONE);
        mAdapter.addAll(collection);
    }

    protected void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    protected void startRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mOnRefreshListener.onRefresh();
            }
        });
    }

    protected void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mOnRefreshListener == null) {
            throw new NullPointerException("You must call setOnRefreshListener() within " +
                    "onViewCreated | onActivityCreated");
        }
        if (mAdapter == null) {
            throw new NullPointerException("You must call setAdapter() within " +
                    "onViewCreated | onActivityCreated");
        }
    }

    public interface SuperRecyclerAdapter<T> {
        void addAll(List<T> items);
    }
}
