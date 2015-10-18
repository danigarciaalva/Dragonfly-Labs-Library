package io.dflabs.lib.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import io.dflabs.lib.adapters.RecyclerListAdapter;
import io.dflabs.lib.ui.SuperRecyclerView;

/**
 * Created by Daniel García Alvarado on 8/27/15.
 * Gastalon - danielgarcia
 */
@SuppressWarnings("unused")
public abstract class RecyclerListFragment<T, R extends RecyclerListAdapter<T, ?>>
        extends BaseFragment {

    protected RecyclerView mRecyclerView;
    TextView mEmptyTextView;
    FrameLayout mEmptyLayout;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    protected R mAdapter;
    private RecyclerView.ItemAnimator mItemAnimator;
    private RecyclerView.LayoutManager mLayoutManager;

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

        mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        };

        this.mAdapter = getAdapter();
        this.mItemAnimator = getItemAnimator();
        this.mLayoutManager = getLayoutManager();

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(mItemAnimator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    protected abstract void refresh();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.ItemAnimator getItemAnimator();

    protected abstract R getAdapter();

    protected void update(List<T> collection, boolean append) {
        mEmptyLayout.setVisibility(collection.size() == 0 ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(collection.size() > 0 ? View.VISIBLE : View.GONE);
        mAdapter.update(collection, append);
        mAdapter.notifyDataSetChanged();
    }

    public void startRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mOnRefreshListener.onRefresh();
                stopRefreshing();
            }
        });
    }

    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        this.mRecyclerView.setLayoutManager(layoutManager);
    }

    protected void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mItemAnimator = itemAnimator;
        this.mRecyclerView.setItemAnimator(itemAnimator);
    }

    /*
    *   Setters area
    *
    *   By default, the SuperRecycler takes as arguments those
    *   provided by the programmer, but It can be modified at runtime
    *
     */
    protected void setEmptyView(View v) {
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(v);
    }

    protected void setEmptyText(int text) {
        mEmptyTextView.setText(text);
    }

    protected void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        this.mOnRefreshListener = onRefreshListener;
    }

    protected void setAdapter(R adapter) {
        this.mAdapter = adapter;
        this.mRecyclerView.setAdapter(mAdapter);
    }
}
