package io.dflabs.lib.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.dflabs.lib.adapters.RecyclerListAdapter;
import io.dflabs.lib.interfaces.OnLoadMoreItems;
import io.dflabs.lib.interfaces.OnRefreshItems;
import io.dflabs.lib.mvp.LoadMoreItems;
import io.dflabs.lib.mvp.RefreshItems;

/**
 * Created by Daniel García Alvarado on 12/8/15.
 * DragonflyLabsLibrary - danielgarcia
 */
public class SuperRecyclerView2 extends FrameLayout {

    public RecyclerView recyclerView;
    public SwipeRefreshLayout swipeRefreshLayout;
    public View emptyView;
    public View contentView;
    private RefreshItems swipeRefreshListener;

    public SuperRecyclerView2(Context context) {
        super(context);
    }

    public SuperRecyclerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperRecyclerView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startRefreshing() {
        if (swipeRefreshLayout != null && swipeRefreshListener != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshListener.onRefresh();
                }
            });
        }
    }

    public void endRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void startBottomRefreshing() {
        RecyclerListAdapter adapter = (RecyclerListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.startEndlessLoading();
        }
    }

    public void endBottomRefreshing() {
        RecyclerListAdapter adapter = (RecyclerListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.update(new ArrayList(), true);
        }
    }


    public static class Builder {

        Context context;
        private LinearLayoutManager layoutManager;
        private RecyclerListAdapter adapter;
        private DefaultItemAnimator animator;
        private boolean endlessScroll;
        private boolean pullToRefresh;
        private OnRefreshItems onRefreshItems;
        private String emptyMessage;
        private boolean empty;
        private View emptyView;
        @ColorRes
        private int emptyMessageColor;
        private float textSize;
        private OnLoadMoreItems loadMoreItems;

        public Builder(Context context) {
            this.context = context;
            if (context == null) {
                throw new NullPointerException("Context must not be null");
            }
            endlessScroll = false;
            pullToRefresh = true;
            empty = false;
        }

        public <T extends LinearLayoutManager> Builder layoutManager(T layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }

        public <A extends RecyclerListAdapter> Builder adapter(A adapter) {
            this.adapter = adapter;
            return this;
        }

        public <AN extends DefaultItemAnimator> Builder animator(AN animator) {
            this.animator = animator;
            return this;
        }

        /**
         * @param enabled - default false
         */
        public Builder endlessScroll(boolean enabled, OnLoadMoreItems callback) {
            this.endlessScroll = enabled;
            this.loadMoreItems = callback;
            return this;
        }

        /**
         * @param enabled - default true
         */
        public Builder pullToRefresh(boolean enabled, OnRefreshItems onRefreshItems) {
            this.pullToRefresh = enabled;
            this.onRefreshItems = onRefreshItems;
            return this;
        }

        /**
         * @param message - Message when 0 items loaded
         */
        public Builder emptyMessage(@StringRes int message, @ColorRes int messageColor, float textSize) {
            this.textSize = textSize;
            this.empty = true;
            this.emptyMessageColor = messageColor;
            this.emptyMessage = this.context.getString(message);
            return this;
        }

        /**
         * @param message - Message
         */
        public Builder emptyMessage(String message, @ColorRes int messageColor, float textSize) {
            this.textSize = textSize;
            this.empty = true;
            this.emptyMessageColor = messageColor;
            this.emptyMessage = message;
            return this;
        }

        public Builder emptyView(View view) {
            this.empty = true;
            this.emptyView = view;
            return this;
        }

        public SuperRecyclerView2 inflateIn(Activity activity) {
            SuperRecyclerView2 superRecyclerView = createSuperRecycler();
            activity.setContentView(superRecyclerView);
            return superRecyclerView;
        }

        public <V extends ViewGroup> SuperRecyclerView2 inflateIn(V parentView) {
            SuperRecyclerView2 superRecyclerView = createSuperRecycler();
            parentView.addView(superRecyclerView);
            return superRecyclerView;
        }

        public SuperRecyclerView2 create() {
            return createSuperRecycler();
        }

        private SuperRecyclerView2 createSuperRecycler() {
            SuperRecyclerView2 superRecyclerView2 = new SuperRecyclerView2(context);
            superRecyclerView2.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            superRecyclerView2.recyclerView = createRecycler();

            if (pullToRefresh) {
                superRecyclerView2.swipeRefreshLayout = new SwipeRefreshLayout(context);
                superRecyclerView2.swipeRefreshLayout.setLayoutParams(
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                superRecyclerView2.swipeRefreshLayout.addView(superRecyclerView2.recyclerView);

                if (onRefreshItems != null) {
                    superRecyclerView2.swipeRefreshListener = new RefreshItems(onRefreshItems);
                    superRecyclerView2.swipeRefreshLayout
                            .setOnRefreshListener(superRecyclerView2.swipeRefreshListener);
                } else {
                    throw new NullPointerException("Pull to Refresh feature needs OnRefreshItems to be implemented");
                }

                superRecyclerView2.contentView = superRecyclerView2.swipeRefreshLayout;
                superRecyclerView2.addView(superRecyclerView2.swipeRefreshLayout);
            } else {
                superRecyclerView2.contentView = superRecyclerView2.recyclerView;
                superRecyclerView2.addView(superRecyclerView2.recyclerView);
            }

            if (empty) {
                superRecyclerView2.emptyView = createEmptyView();
                superRecyclerView2.addView(superRecyclerView2.emptyView);
            }

            return superRecyclerView2;
        }

        private View createEmptyView() {
            View emptyLayout = new FrameLayout(context);
            emptyLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            if (emptyView != null) {
                ((ViewGroup) emptyLayout).addView(emptyView);
            } else {
                TextView emptyTextView = new TextView(context);
                emptyTextView.setGravity(Gravity.CENTER);
                emptyTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));

                emptyTextView.setText(emptyMessage);
                emptyTextView.setTextSize(textSize);
                emptyTextView.setTextColor(context.getResources().getColor(emptyMessageColor));
                emptyLayout = emptyTextView;
            }
            emptyLayout.setVisibility(View.GONE);
            return emptyLayout;
        }

        private RecyclerView createRecycler() {
            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(animator);
            recyclerView.setLayoutManager(layoutManager);
            if (endlessScroll) {
                if (loadMoreItems != null) {
                    recyclerView.addOnScrollListener(new LoadMoreItems(adapter, layoutManager, this.loadMoreItems));
                } else {
                    throw new NullPointerException("Endless Scroll feature needs OnLoadMoreItems " +
                            "to be implemented");
                }
            }
            return recyclerView;
        }
    }
}
