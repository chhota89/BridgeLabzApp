package com.bridgelabz.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabz.app.R;
import com.bridgelabz.app.model.ORMUser;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by bridgelabz5 on 30/5/16.
 */
public class ORM_Helper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "orm.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG ="ORM_Helper";

    private Dao<ORMUser,Integer> dao=null;
    RuntimeExceptionDao<ORMUser,Integer> runtimeExceptionDao=null;


    public ORM_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d(TAG, "onCreate() called with: " + "database = [" + database + "], connectionSource = [" + connectionSource + "]");
        try {
            TableUtils.createTable(connectionSource, ORMUser.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ORMUser.class, true);
        } catch (SQLException e) {
            Log.e(TAG, "onUpgrade: "+e.getMessage() );
        }
        // after we drop the old databases, we create the new ones
        onCreate(database, connectionSource);
    }

    public Dao<ORMUser,Integer> getDao() throws SQLException {
        if(dao==null){
            dao=getDao(ORMUser.class);
        }
        return dao;
    }

    public RuntimeExceptionDao<ORMUser,Integer> getRuntimeExceptionDao(){
        if (runtimeExceptionDao==null)
            runtimeExceptionDao=getRuntimeExceptionDao(ORMUser.class);
        return runtimeExceptionDao;
    }
}
