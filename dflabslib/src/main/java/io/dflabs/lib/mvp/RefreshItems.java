package io.dflabs.lib.mvp;

import android.support.v4.widget.SwipeRefreshLayout;

import io.dflabs.lib.interfaces.OnRefreshItems;

/**
 * Created by Daniel García Alvarado on 12/8/15.
 * DragonflyLabsLibrary - danielgarcia
 */
public class RefreshItems implements SwipeRefreshLayout.OnRefreshListener {

    private OnRefreshItems onRefreshItems;

    public RefreshItems(OnRefreshItems onRefreshItems) {
        this.onRefreshItems = onRefreshItems;
    }

    @Override
    public void onRefresh() {
        onRefreshItems.onRefresh();
    }

}
