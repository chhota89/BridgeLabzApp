package com.bridgelabz.app;

import android.app.Application;

import com.bridgelabz.app.database.SimpleRealmModule;
import com.bridgelabz.app.model.RealmUser;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.annotations.RealmModule;

/**
 * Created by bridgeit on 18/7/16.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
