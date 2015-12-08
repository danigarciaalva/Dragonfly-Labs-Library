package io.dflabs.sample.presenters.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import io.dflabs.lib.mvp.BasePresenter;
import io.dflabs.sample.models.pojos.Fruit;
import io.dflabs.sample.presenters.callback.FruitsCallback;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitPresenter extends BasePresenter {

    private final FruitsCallback callback;

    public FruitPresenter(Context context, FruitsCallback callback) {
        super(context);
        this.callback = callback;
    }

    public void refresh() {
        ArrayList<Fruit> data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add(new Fruit("Data " + new Random().nextDouble()));
        }
        callback.onSuccessFruitsLoaded(data);
    }
}
