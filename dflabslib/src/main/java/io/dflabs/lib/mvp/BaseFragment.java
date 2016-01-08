package io.dflabs.lib.mvp;

import android.support.v4.app.Fragment;

/**
 * Created by Daniel García Alvarado on 10/10/15.
 * Crédito Real - danielgarcia
 */
public abstract class BaseFragment extends Fragment {

    private BasePresenter mPresenter;

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter = null;
    }

    protected <T extends BasePresenter> void setupPresenter(T basePresenter) {
        this.mPresenter = basePresenter;
    }

}
