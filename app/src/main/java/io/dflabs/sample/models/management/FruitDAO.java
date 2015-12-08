package io.dflabs.sample.models.management;

import java.util.ArrayList;

import io.dflabs.sample.models.pojos.Fruit;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitDAO {

    public static ArrayList<Fruit> loadFruits() {
        return new ArrayList<Fruit>(){{
            add(new Fruit("Banana"));
            add(new Fruit("Apple"));
            add(new Fruit("Pineapple"));
            add(new Fruit("Strawberry"));
            add(new Fruit("Lemon"));
            add(new Fruit("Orange"));
            add(new Fruit("Grape"));
            add(new Fruit("Blueberry"));
        }};
    }
}
