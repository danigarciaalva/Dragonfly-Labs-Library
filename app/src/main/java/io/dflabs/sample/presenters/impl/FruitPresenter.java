package io.dflabs.sample.presenters.impl;

import android.content.Context;

import java.util.ArrayList;

import io.dflabs.sample.models.pojos.Fruit;
import io.dflabs.sample.presenters.callback.FruitsCallback;
import io.realm.RealmResults;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitPresenter extends RealmPresenter {

    private final FruitsCallback callback;
    private int lastId;

    public FruitPresenter(Context context, FruitsCallback callback) {
        super(context);
        this.callback = callback;
        lastId = 0;
    }

    public void refresh(boolean bottomRefresh) {
        if (!bottomRefresh) {
            lastId = 0;
        }
        RealmResults<Fruit> results = mRealm.where(Fruit.class)
                .greaterThan("fruitId", lastId).findAll();
        ArrayList<Fruit> fruits = new ArrayList<>();
        if (results.size() > 0) {
            if (results.size() > 40) {
                for (int i = 0; i < 40; i++) {
                    fruits.add(results.get(i));
                }
            } else {
                fruits.addAll(results);
            }
            lastId = fruits.get(fruits.size() - 1).getFruitId();
        }
        callback.onSuccessFruitsLoaded(fruits, bottomRefresh);
    }
}
