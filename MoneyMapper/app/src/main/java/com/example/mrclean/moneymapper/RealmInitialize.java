package com.example.mrclean.moneymapper;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;



/**
 * Created by j2md7_000 on 9/24/2017.
 */

public class RealmInitialize extends Application {


    public static final int SCHEMA_VERSION = 2;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
            .schemaVersion(SCHEMA_VERSION)
            .name("account.realm")
            .build();


        Realm.setDefaultConfiguration(config);
    }
}
