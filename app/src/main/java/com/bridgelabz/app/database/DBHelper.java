package com.bridgelabz.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bridgelabz.app.model.User;

/**
 * Created by bridgelabz5 on 26/5/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userDB";

    // Contacts table name
    private static final String TABLE_USER = "user";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_AGE = "age";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_AGE+ " INTEGER"+ ")";

        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    public void addContact(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // Contact Name
        values.put(KEY_PH_NO, user.getPhoneNumber()); // Contact Phone
        values.put(KEY_AGE, user.getAge()); // Contact Age

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public Cursor getData(){
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return  cursor;
    }

    public Cursor getUserByMobileNumber(String phoneNumber){
        Cursor cursor = db.query(true,TABLE_USER,new String[]{KEY_NAME,KEY_AGE},KEY_PH_NO+"="+phoneNumber,null,null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        return cursor;

    }

    public boolean deleteUser(String phoneNumber){
        return db.delete(TABLE_USER,KEY_PH_NO+"="+phoneNumber,null)>0;
    }

    public boolean update(User user){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // Contact Name
        values.put(KEY_AGE, user.getAge()); // Contact Age

        return db.update(TABLE_USER,values,KEY_PH_NO+"="+user.getPhoneNumber(),null)>0;
    }
}
