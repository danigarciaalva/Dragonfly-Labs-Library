package io.dflabs.sample.presenters.callback;

import java.util.ArrayList;

import io.dflabs.sample.models.pojos.Fruit;

/**
 * Created by Daniel García Alvarado on 12/7/15.
 * DragonflyLabsLibrary - danielgarcia
 */
public interface FruitsCallback {
    void onSuccessFruitsLoaded(ArrayList<Fruit> data);
}
