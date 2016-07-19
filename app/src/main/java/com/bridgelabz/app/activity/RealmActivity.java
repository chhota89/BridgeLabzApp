package com.bridgelabz.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.model.RealmUser;
import com.bridgelabz.app.utility.NumberUtility;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.realm_activity));
        setSupportActionBar(toolbar);

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
                            RealmUser realmUser = new RealmUser();
                            // Set its fields
                            realmUser.setId(NumberUtility.getRandom());
                            realmUser.setName(name.getText().toString());
                            realmUser.setAge(Integer.parseInt(age.getText().toString()));
                            realmUser.setPhoneNumber(phoneNumber.getText().toString());
                            myRealm.copyToRealm(realmUser);
                            long start=System.currentTimeMillis();
                            myRealm.commitTransaction();

                            long end=System.currentTimeMillis();
                            NumberUtility.getTime(start,end,"Realm Insert");

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
                Intent intent = new Intent(RealmActivity.this, DisplayData.class);
                intent.putExtra("DATABASE_TYPE", "REALM");
                startActivity(intent);

            }
        });

        //Update value based on phone number
        Button updateValue = (Button) findViewById(R.id.updateValue);
        updateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(RealmActivity.this);
                dialog.setContentView(R.layout.insert_user_details_dialog);
                dialog.setTitle("Update User Details");

                final TextView name, age;
                name = (TextView) dialog.findViewById(R.id.userName);
                age = (TextView) dialog.findViewById(R.id.age);
                name.setFocusable(false);
                age.setFocusable(false);
                ImageView search = (ImageView) dialog.findViewById(R.id.search);
                final Button insertdetails = (Button) dialog.findViewById(R.id.insertValue);
                final TextView phoneNumber = (TextView) dialog.findViewById(R.id.phoneNumber);
                insertdetails.setText("Update");
                insertdetails.setEnabled(false);

                dialog.show();

                insertdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            RealmUser realmUser = myRealm.where(RealmUser.class)
                                    .equalTo("phoneNumber", phoneNumber.getText().toString()).findFirst();
                            myRealm.beginTransaction();
                            realmUser.setName(name.getText().toString());
                            realmUser.setAge(Integer.parseInt(age.getText().toString()));
                            long start=System.currentTimeMillis();
                            myRealm.commitTransaction();
                            long end=System.currentTimeMillis();
                            NumberUtility.getTime(start,end,"Realm Update");
                            dialog.hide();
                            Toast.makeText(RealmActivity.this, getString(R.string.save_data_sqlite), Toast.LENGTH_LONG).show();

                        } catch (NumberFormatException exception) {
                            Toast.makeText(RealmActivity.this, getString(R.string.error_enter_number), Toast.LENGTH_LONG).show();
                        } catch (Exception exception) {
                            Log.e("SQLITE_Fragemnt", "onClick: " + exception.getMessage());
                        }
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setFocusable(false);
                        age.setFocusable(false);
                        insertdetails.setEnabled(false);

                        RealmUser realmUser;
                        //Quary user by mobile number.
                        realmUser = myRealm.where(RealmUser.class).equalTo("phoneNumber", phoneNumber.getText().toString()).findFirst();

                        if (realmUser == null)
                            Toast.makeText(RealmActivity.this, getString(R.string.no_mobile_number_found), Toast.LENGTH_LONG).show();
                        else {
                            name.setText(realmUser.getName());
                            age.setText(String.valueOf(realmUser.getAge()));
                            name.setEnabled(true);
                            age.setEnabled(true);
                            name.setFocusableInTouchMode(true);
                            age.setFocusableInTouchMode(true);
                            insertdetails.setEnabled(true);
                        }
                    }
                });//end of search click
            }
        });


        //Delete Button
        Button deleteValue = (Button) findViewById(R.id.deleteValue);
        deleteValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(RealmActivity.this);
                dialog.setContentView(R.layout.insert_user_details_dialog);
                dialog.setTitle("Delete Record");

                final TextView name, age;
                name = (TextView) dialog.findViewById(R.id.userName);
                age = (TextView) dialog.findViewById(R.id.age);
                name.setFocusable(false);
                age.setFocusable(false);
                ImageView search = (ImageView) dialog.findViewById(R.id.search);
                final Button deleteValue = (Button) dialog.findViewById(R.id.insertValue);
                final TextView phoneNumber = (TextView) dialog.findViewById(R.id.phoneNumber);
                deleteValue.setText("Delete");
                deleteValue.setEnabled(false);

                dialog.show();

                deleteValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Setting delete condition like phoneNumber=123

                        RealmResults<RealmUser> result;
                        //Quary user by mobile number.
                        result = myRealm.where(RealmUser.class).equalTo("phoneNumber", phoneNumber.getText().toString()).findAll();

                        myRealm.beginTransaction();
                        result.clear();
                        myRealm.commitTransaction();

                        dialog.hide();
                        Toast.makeText(RealmActivity.this, getString(R.string.record_deleted), Toast.LENGTH_LONG).show();
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setFocusable(false);
                        age.setFocusable(false);
                        deleteValue.setEnabled(false);
                        RealmUser realmUser;
                        //Quary user by mobile number.
                        realmUser = myRealm.where(RealmUser.class).equalTo("phoneNumber", phoneNumber.getText().toString()).findFirst();

                        if (realmUser == null)
                            Toast.makeText(RealmActivity.this, getString(R.string.no_mobile_number_found), Toast.LENGTH_LONG).show();
                        else {
                            name.setText(realmUser.getName());
                            age.setText(String.valueOf(realmUser.getAge()));
                            deleteValue.setEnabled(true);
                        }
                    }
                });
            }
        });
    }


}


