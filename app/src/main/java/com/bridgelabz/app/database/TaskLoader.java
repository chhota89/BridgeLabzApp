package com.bridgelabz.app.database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by bridgeit on 19/9/16.
 */

public class TaskLoader extends AsyncTaskLoader<Cursor> {

    DBHelper dbHelper;

    public TaskLoader(Context context) {
        super(context);
        dbHelper=new DBHelper(context);
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        return dbHelper.getData();
    }
}
