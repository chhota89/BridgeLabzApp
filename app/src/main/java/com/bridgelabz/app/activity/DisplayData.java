package com.bridgelabz.app.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.bridgelabz.app.R;
import com.bridgelabz.app.database.DBHelper;
import com.bridgelabz.app.database.ORM_Helper;
import com.bridgelabz.app.model.ORMUser;
import com.bridgelabz.app.model.RealmUser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DisplayData extends AppCompatActivity {

    private final String TAG="DisplayData";
    private Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_display_data));
        setSupportActionBar(toolbar);
        myRealm = Realm.getInstance(DisplayData.this);

        String callerPage=getIntent().getStringExtra("DATABASE_TYPE");
        if (callerPage!=null && callerPage.equals("sqlite")){
            DBHelper dbHelper=new DBHelper(DisplayData.this);
            Cursor cursor=dbHelper.getData();
            genrateTable(cursor);
        }
        else if(callerPage!=null && callerPage.equals("REALM")){
            RealmResults<RealmUser> results1 =
                    myRealm.where(RealmUser.class).findAll();
            displayData(results1);
        }
        else {
            ORM_Helper helper= OpenHelperManager.getHelper(DisplayData.this,ORM_Helper.class);
            try {
                final Dao<ORMUser,Integer> userDao=helper.getDao();
                List<ORMUser> userList = userDao.queryForAll();
                OpenHelperManager.releaseHelper();
                displayData(userList);
            } catch (SQLException e) {
                Log.e(TAG, "onCreate: Display ORM Data ",e );
            }
        }
    }

    private void genrateTable(Cursor cursor) {
        TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        cursor.moveToFirst();

        int rows = cursor.getCount();
        int cols = cursor.getColumnCount();
        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 0; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv.setPadding(0, 5, 0, 5);

                tv.setText(cursor.getString(j));

                row.addView(tv);

            }

            cursor.moveToNext();

            table_layout.addView(row);

        }
        cursor.close();
    }


    private void displayData(List<?> list){
        TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        int rows = list.size();
        int cols = 1;
        // outer for loop
        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 0; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv.setPadding(0, 5, 0, 5);

                tv.setText(list.get(i).toString());

                row.addView(tv);
            }
            table_layout.addView(row);
        }
    }

}
