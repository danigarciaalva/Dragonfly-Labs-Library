package io.dflabs.lib.mvp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.dflabs.lib.adapters.RecyclerListAdapter;
import io.dflabs.lib.interfaces.OnLoadMoreItems;

/**
 * Created by Daniel García Alvarado on 12/8/15.
 * DragonflyLabsLibrary - danielgarcia
 */
public class LoadMoreItems extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager;
    private OnLoadMoreItems onLoadMoreItems;
    private RecyclerListAdapter mAdapter;
    private boolean loading = true;
    private int previousTotal = 0, currentPage = 1, adapterType = 0, type = 0;

    public LoadMoreItems(OnLoadMoreItems onLoadMoreItems) {
        this.onLoadMoreItems = onLoadMoreItems;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (type == 0) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                type = 1;
                mLinearLayoutManager = (LinearLayoutManager) layoutManager;
            } else {
                throw new RuntimeException("LayoutManager must extends LinearLayout");
            }
        }
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal + 1) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 1;
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            if (adapterType == 0) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter instanceof RecyclerListAdapter) {
                    adapterType = 1;
                    mAdapter = (RecyclerListAdapter) adapter;
                } else {
                    throw new RuntimeException("Adapter must extends RecyclerListAdapter");
                }
            }
            mAdapter.startEndlessLoading();
            onLoadMoreItems.onLoadMoreItems(currentPage);
            loading = true;
        }
    }
}