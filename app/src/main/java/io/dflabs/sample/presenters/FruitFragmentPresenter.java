package io.dflabs.sample.presenters;

import android.support.v4.widget.SwipeRefreshLayout;

import io.dflabs.lib.mvp.BaseFragmentPresenter;
import io.dflabs.sample.FruitListFragment;
import io.dflabs.sample.models.FruitDAO;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitFragmentPresenter extends BaseFragmentPresenter<FruitListFragment>{

    public FruitFragmentPresenter(FruitListFragment fragment) {
        super(fragment);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    public void refresh() {
        mFragment.updateRecycler(FruitDAO.loadFruits());
    }
}
