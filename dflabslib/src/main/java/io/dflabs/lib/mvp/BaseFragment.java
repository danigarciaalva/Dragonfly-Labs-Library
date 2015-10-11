package io.dflabs.lib.mvp;

import android.support.v4.app.Fragment;

/**
 * Created by Daniel García Alvarado on 10/10/15.
 * Crédito Real - danielgarcia
 */
public abstract class BaseFragment extends Fragment {

    private BaseFragmentPresenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter = null;
        super.onDestroyView();
    }

    protected <T extends BaseFragmentPresenter> void setupPresenter(T basePresenter) {
        this.mPresenter = basePresenter;
    }

}
