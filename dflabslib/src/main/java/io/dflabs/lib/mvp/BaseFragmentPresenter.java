package io.dflabs.lib.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Daniel García Alvarado on 10/10/15.
 * Crédito Real - danielgarcia
 */
public abstract class BaseFragmentPresenter<T extends Fragment> {

    protected final T mFragment;
    protected final Context mContext;

    public BaseFragmentPresenter(T fragment) {
        this.mFragment = fragment;
        this.mContext = fragment.getContext();
    }

    public abstract void onResume();

    public abstract void onPause();
}
