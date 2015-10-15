package io.dflabs.lib.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Daniel García Alvarado on 10/11/15.
 * Gastalon - danielgarcia
 */
@SuppressWarnings("unused")
public class SuperRecyclerView extends FrameLayout{
    public static final String SUPER_RECYCLER_VIEW_TAG = "super_recycler_view_tag";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeToRefreshLayout;
    private FrameLayout emptyLayout;
    private TextView emptyTextView;

    public SuperRecyclerView(Context context) {
        super(context);
        init();
    }

    public SuperRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SuperRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        swipeToRefreshLayout = new SwipeRefreshLayout(getContext());
        swipeToRefreshLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        swipeToRefreshLayout.addView(recyclerView);

        emptyLayout = new FrameLayout(getContext());
        emptyLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        emptyTextView = new TextView(getContext());
        emptyTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        emptyLayout.addView(emptyTextView);
        emptyLayout.setVisibility(View.GONE);

        addView(swipeToRefreshLayout);
        addView(emptyLayout);

        setTag(SUPER_RECYCLER_VIEW_TAG);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeToRefreshLayout() {
        return swipeToRefreshLayout;
    }

    public FrameLayout getEmptyLayout() {
        return emptyLayout;
    }

    public TextView getEmptyTextView() {
        return emptyTextView;
    }
}
