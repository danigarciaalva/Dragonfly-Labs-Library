package io.dflabs.lib.mvp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Daniel García Alvarado on 10/10/15.
 * Crédito Real - danielgarcia
 */
public abstract class BasePresenter<T extends AppCompatActivity> {

    protected final T activity;
    protected final Context context;

    public BasePresenter(T appCompatActivity) {
        this.activity = appCompatActivity;
        this.context = appCompatActivity;
    }

    public abstract void onResume();

    public abstract void onPause();
}
