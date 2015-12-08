package io.dflabs.sample.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.dflabs.lib.mvp.RecyclerListFragment;
import io.dflabs.sample.R;
import io.dflabs.sample.views.adapters.FruitAdapter;
import io.dflabs.sample.models.pojos.Fruit;
import io.dflabs.sample.presenters.impl.FruitPresenter;
import io.dflabs.sample.presenters.callback.FruitsCallback;

/**
 * Created by Daniel García Alvarado on 10/13/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitListFragment extends RecyclerListFragment<Fruit, FruitAdapter> implements FruitsCallback {

    private FruitPresenter mFruitPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fruit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFruitPresenter = new FruitPresenter(getContext(), this);
        startRefreshing();
    }

    @Override
    protected void onRecyclerNeedsMoreData() {
        startRefreshing();
        onRecyclerEndLoadingMoreData();
    }

    @Override
    protected void refresh() {
        mFruitPresenter.refresh();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView.ItemAnimator getItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Override
    protected FruitAdapter getAdapter() {
        return new FruitAdapter();
    }

    @Override
    public void onSuccessFruitsLoaded(ArrayList<Fruit> data) {
        update(data, true);
        stopRefreshing();
    }

    public static FruitListFragment newInstance() {
        return new FruitListFragment();
    }
}
