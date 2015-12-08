package io.dflabs.lib.mvp;

import android.content.Context;

/**
 * Created by Daniel García Alvarado on 10/10/15.
 * Crédito Real - danielgarcia
 */
public abstract class BasePresenter {

    protected final Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    public void onResume() {

    }

    public void onPause() {

    }
}
