package io.dflabs.sample.presenters.impl;

import android.content.Context;

import io.dflabs.lib.mvp.BasePresenter;
import io.realm.Realm;

/**
 * Created by Daniel García Alvarado on 1/12/16.
 * DragonflyLabsLibrary - danielgarcia
 */
public class RealmPresenter extends BasePresenter {

    protected Realm mRealm;

    public RealmPresenter(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
