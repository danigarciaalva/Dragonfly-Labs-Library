package io.dflabs.sample.views;

import android.app.Application;

import io.dflabs.sample.models.pojos.Fruit;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Daniel García Alvarado on 1/12/16.
 * DragonflyLabsLibrary - danielgarcia
 */
public class FruitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .name("fruitRealm")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long matches = realm.where(Fruit.class).count();
                if (matches == 0) {
                    String[] fruits = new String[]{"Apple", "Apricot", "Avocado", "Banana", "Broccoli",
                            "Carrot", "Cherry", "Grape", "Guava", "Kiwi", "Lemon", "Mango", "Melon",
                            "Mushroom", "Nut", "Orange", "Peanut", "Pear", "Pepper", "Pineapple", "Pumpkin",
                            "Radish", "Strawberry", "Tomato", "Watermelon"};
                    int fruitId = 1;
                    for (int i = 0; i < 20; i++) {
                        for (String fruit : fruits) {
                            Fruit fruitRealm = new Fruit(fruit);
                            fruitRealm.setFruitId(fruitId);
                            fruitId++;
                            realm.copyToRealmOrUpdate(fruitRealm);
                        }
                    }
                }
            }
        });
        realm.close();
    }
}
