package com.bridgelabz.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.model.ORMUser;
import com.bridgelabz.app.model.RealmUser;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bridgeit on 18/7/16.
 */

public class RealmActivity extends AppCompatActivity {

    Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orm);
        myRealm = Realm.getInstance(RealmActivity.this);

        //Insert value in the table
        Button ormCreate = (Button) findViewById(R.id.insertValue);
        ormCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(RealmActivity.this);
                dialog.setContentView(R.layout.insert_user_details_dialog);
                dialog.setTitle("Insert User Details");
                ImageView search = (ImageView) dialog.findViewById(R.id.search);
                search.setVisibility(View.INVISIBLE);
                dialog.show();

                Button insertdetails = (Button) dialog.findViewById(R.id.insertValue);
                //Insert User Data when Button is pressed.
                insertdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Add user details to data base
                        TextView name, age, phoneNumber;
                        name = (TextView) dialog.findViewById(R.id.userName);
                        age = (TextView) dialog.findViewById(R.id.age);
                        phoneNumber = (TextView) dialog.findViewById(R.id.phoneNumber);
                        try {
                            //Insert record
                            myRealm.beginTransaction();
                            // Create an object
                            RealmUser realmUser=new RealmUser();
                            // Set its fields
                            realmUser.setId(getRandom());
                            realmUser.setName(name.getText().toString());
                            realmUser.setAge(Integer.parseInt(age.getText().toString()));
                            realmUser.setPhoneNumber(phoneNumber.getText().toString());
                            myRealm.copyToRealm(realmUser);
                            myRealm.commitTransaction();

                            dialog.hide();
                            Toast.makeText(RealmActivity.this, getString(R.string.save_data_sqlite), Toast.LENGTH_LONG).show();
                        } catch (NumberFormatException exception) {
                            Toast.makeText(RealmActivity.this, getString(R.string.error_enter_number), Toast.LENGTH_LONG).show();
                        } catch (Exception exception) {
                            Log.e("SQLITE_Fragemnt", "onClick: " + exception.getMessage());
                        }
                    }
                });
            }
        });


        //Display Button
        Button displayData = (Button) findViewById(R.id.display);
        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Display Data activity
                //startActivity(new Intent(RealmActivity.this, DisplayData.class));
                RealmResults<RealmUser> results1 =
                        myRealm.where(RealmUser.class).findAll();

                for(RealmUser c:results1) {
                    Log.d("results1", c.getName());
                }

            }
        });

        //Update value based on phone number
        Button updateValue = (Button) findViewById(R.id.updateValue);
        updateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Delete Button
        Button deleteValue = (Button) findViewById(R.id.deleteValue);
        deleteValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private int getRandom(){
        return 0 + (int)(Math.random() * ((Integer.MAX_VALUE - 0) + 1));
    }
}


