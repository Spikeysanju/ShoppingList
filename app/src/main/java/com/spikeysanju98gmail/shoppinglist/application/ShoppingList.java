package com.spikeysanju98gmail.shoppinglist.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ShoppingList extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("shopping.db")
                .schemaVersion(0)
                .build();

        Realm.setDefaultConfiguration(configuration);

    }
}
