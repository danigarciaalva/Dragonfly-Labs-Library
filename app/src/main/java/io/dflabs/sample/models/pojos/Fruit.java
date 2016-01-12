package io.dflabs.sample.models.pojos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class Fruit extends RealmObject {

    @PrimaryKey
    private int fruitId;

    public Fruit() {

    }

    public Fruit(String name) {
        this.name = name;
    }


    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
