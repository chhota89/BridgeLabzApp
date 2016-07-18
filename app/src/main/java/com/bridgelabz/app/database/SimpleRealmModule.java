package com.bridgelabz.app.database;

import com.bridgelabz.app.model.RealmUser;

import io.realm.annotations.RealmModule;

/**
 * Created by bridgeit on 18/7/16.
 */
@RealmModule(classes = {RealmUser.class})
public class SimpleRealmModule {
}
