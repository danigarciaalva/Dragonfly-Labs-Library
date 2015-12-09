package io.dflabs.sample.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.dflabs.lib.interfaces.OnLoadMoreItems;
import io.dflabs.lib.interfaces.OnRefreshItems;
import io.dflabs.lib.ui.SuperRecyclerView2;
import io.dflabs.sample.R;
import io.dflabs.sample.views.adapters.FruitAdapter;
import io.dflabs.sample.models.pojos.Fruit;
import io.dflabs.sample.presenters.impl.FruitPresenter;
import io.dflabs.sample.presenters.callback.FruitsCallback;

/**
 * Created by Daniel García Alvarado on 10/13/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitListFragment extends Fragment implements FruitsCallback {

    private FruitPresenter mFruitPresenter;
    private SuperRecyclerView2 mSuperRecyclerView2;
    private FruitAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fruit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFruitPresenter = new FruitPresenter(getContext(), this);
        mAdapter = new FruitAdapter();
        mSuperRecyclerView2 = new SuperRecyclerView2.Builder(getContext())
                .adapter(mAdapter)
                .animator(new DefaultItemAnimator())
                .emptyMessage(R.string.no_data, R.color.colorAccent, 15)
                .endlessScroll(true, new OnLoadMoreItems() {
                    @Override
                    public void onLoadMoreItems(int currentPage) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mFruitPresenter.refresh(true);
                            }
                        }, 3000);
                    }
                })
                .pullToRefresh(true, new OnRefreshItems() {
                    @Override
                    public void onRefresh() {
                        mFruitPresenter.refresh(false);
                    }
                })
                .layoutManager(new LinearLayoutManager(getContext()))
                .inflateIn((ViewGroup) view);

        mSuperRecyclerView2.startRefreshing();
    }

    public static FruitListFragment newInstance() {
        return new FruitListFragment();
    }

    @Override
    public void onSuccessFruitsLoaded(ArrayList<Fruit> data, boolean bottomRefresh) {
        mAdapter.update(data, bottomRefresh);
        mSuperRecyclerView2.endRefreshing();
    }
}
