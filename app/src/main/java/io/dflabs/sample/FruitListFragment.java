package io.dflabs.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.dflabs.lib.mvp.RecyclerListFragment;
import io.dflabs.lib.utils.Prefs;
import io.dflabs.sample.adapters.FruitAdapter;
import io.dflabs.sample.models.Fruit;
import io.dflabs.sample.presenters.FruitFragmentPresenter;

/**
 * Created by Daniel García Alvarado on 10/13/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitListFragment extends RecyclerListFragment<Fruit, FruitAdapter>{


    public static Fragment newInstance() {
        return new FruitListFragment();
    }

    private FruitFragmentPresenter mFruitPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fruit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFruitPresenter = new FruitFragmentPresenter(this);
        startRefreshing();
    }

    @Override
    protected void refresh() {
        mFruitPresenter.refresh();
        Prefs.setDefaultContext(getContext());
        Prefs.instance().bool("");
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

    public void updateRecycler(List<Fruit> fruits) {
        update(fruits, true);
    }
}
