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

    private RecyclerListAdapter adapter;
    private final LinearLayoutManager mLinearLayoutManager;
    private OnLoadMoreItems onLoadMoreItems;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int currentPage = 1;

    public LoadMoreItems(RecyclerListAdapter adapter, LinearLayoutManager linearLayoutManager, OnLoadMoreItems onLoadMoreItems) {
        this.adapter = adapter;
        this.mLinearLayoutManager = linearLayoutManager;
        this.onLoadMoreItems = onLoadMoreItems;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal + 1) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 1;
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            adapter.startEndlessLoading();
            onLoadMoreItems.onLoadMoreItems(currentPage);
            loading = true;
        }
    }

}