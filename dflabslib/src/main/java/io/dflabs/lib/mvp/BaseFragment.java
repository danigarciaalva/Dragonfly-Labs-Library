package io.dflabs.lib.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Daniel García Alvarado on 10/10/15.
 * Crédito Real - danielgarcia
 */
public abstract class BaseFragment extends Fragment {

    protected BasePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        if (mPresenter != null) mPresenter.onCreate();
    }

    protected abstract BasePresenter getPresenter();

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
        if (mPresenter != null) mPresenter.onDestroy();
        mPresenter = null;
    }

    protected <T extends BasePresenter> void setupPresenter(T basePresenter) {
        this.mPresenter = basePresenter;
    }
}
