package com.bridgelabz.app.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.bridgelabz.app.R;
import com.bridgelabz.app.database.DBHelper;

public class DisplayData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBHelper dbHelper=new DBHelper(DisplayData.this);
        Cursor cursor=dbHelper.getData();

        genrateTable(cursor);
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

}
