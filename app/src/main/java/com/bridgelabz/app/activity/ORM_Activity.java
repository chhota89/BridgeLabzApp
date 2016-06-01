package com.bridgelabz.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.database.ORM_Helper;
import com.bridgelabz.app.model.ORMUser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ORM_Activity extends OrmLiteBaseActivity<ORM_Helper> {

    Dao<ORMUser, Integer> userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orm);

        try {
            userDao = getHelper().getDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Insert value in the table
        Button ormCreate = (Button) findViewById(R.id.insertValue);
        if (ormCreate != null)
            ormCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Open dialog that take user detils
                    final Dialog dialog = new Dialog(ORM_Activity.this);
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
                                try {

                                    //When you extending Activity.
                                    //final Dao<ORMUser,Integer> userDao=helper.getDao();

                                    //Insert User
                                    userDao.create(new ORMUser(name.getText().toString(), phoneNumber.getText().toString(), Integer.parseInt(age.getText().toString())));

                                    /* //Quary for all user in the list.
                                    List<ORMUser> userList = userDao.queryForAll();*/

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //When you extending Activity.
                                OpenHelperManager.releaseHelper();
                                //dbHelper.addContact(new User(name.getText().toString(),phoneNumber.getText().toString(),Integer.parseInt(age.getText().toString())));
                                dialog.hide();
                                Toast.makeText(ORM_Activity.this, getString(R.string.save_data_sqlite), Toast.LENGTH_LONG).show();
                            } catch (NumberFormatException exception) {
                                Toast.makeText(ORM_Activity.this, getString(R.string.error_enter_number), Toast.LENGTH_LONG).show();
                            } catch (Exception exception) {
                                Log.e("SQLITE_Fragemnt", "onClick: " + exception.getMessage());
                            }
                        }
                    });

                    //ORM_Helper helper= OpenHelperManager.getHelper(ORM_Activity.this,ORM_Helper.class);
                    //RuntimeExceptionDao<ORMUser,Integer> runtimeExceptionDao=helper.getRuntimeExceptionDao();
                }
            });


        //Display Button
        Button displayData = (Button) findViewById(R.id.display);
        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Display Data activity
                startActivity(new Intent(ORM_Activity.this, DisplayData.class));
            }
        });

        //Update value based on phone number
        Button updateValue = (Button) findViewById(R.id.updateValue);
        updateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(ORM_Activity.this);
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
                            UpdateBuilder<ORMUser, Integer> updateBuilder = userDao.updateBuilder();

                            //setting criteria like poneNumber=123
                            updateBuilder.where().eq("phoneNumber", phoneNumber.getText().toString());

                            //Setting new value for update builder.
                            updateBuilder.updateColumnValue("name", name.getText().toString());
                            updateBuilder.updateColumnValue("age", Integer.parseInt(age.getText().toString()));

                            //update value and display messsage whether it's updated or not
                            if (updateBuilder.update() > 0) {
                                dialog.hide();
                                Toast.makeText(ORM_Activity.this, getString(R.string.save_data_sqlite), Toast.LENGTH_LONG).show();
                            }

                        } catch (NumberFormatException exception) {
                            Toast.makeText(ORM_Activity.this, getString(R.string.error_enter_number), Toast.LENGTH_LONG).show();
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

                        List<ORMUser> quaryByNumber = new ArrayList<>();

                        //Quary user by mobile number.
                        try {
                            quaryByNumber = userDao.queryForEq("phoneNumber", phoneNumber.getText().toString());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (quaryByNumber.size() == 0)
                            Toast.makeText(ORM_Activity.this, getString(R.string.no_mobile_number_found), Toast.LENGTH_LONG).show();
                        else {
                            name.setText(quaryByNumber.get(0).getName());
                            age.setText(quaryByNumber.get(0).getAge());
                            name.setEnabled(true);
                            age.setEnabled(true);
                            name.setFocusableInTouchMode(true);
                            age.setFocusableInTouchMode(true);
                            insertdetails.setEnabled(true);
                        }
                    }
                });//end of search click
            }
        });//end of update button click


        //Delete Button
        Button deleteValue = (Button) findViewById(R.id.deleteValue);
        deleteValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(ORM_Activity.this);
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
                        DeleteBuilder<ORMUser, Integer> deleteBuilder = userDao.deleteBuilder();
                        try {
                            //Setting delete condition like phoneNumber=123
                            deleteBuilder.where().eq("phoneNumber", phoneNumber.getText().toString());

                            if (deleteBuilder.delete() > 0) {
                                dialog.hide();
                                Toast.makeText(ORM_Activity.this, getString(R.string.record_deleted), Toast.LENGTH_LONG).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setFocusable(false);
                        age.setFocusable(false);
                        deleteValue.setEnabled(false);
                        List<ORMUser> quaryByNumber = new ArrayList<>();
                        try {
                            quaryByNumber = userDao.queryForEq("phoneNumber", phoneNumber.getText().toString());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (quaryByNumber.size() == 0)
                            Toast.makeText(ORM_Activity.this, getString(R.string.no_mobile_number_found), Toast.LENGTH_LONG).show();
                        else {
                            name.setText(quaryByNumber.get(0).getName());
                            age.setText(quaryByNumber.get(0).getAge());
                            deleteValue.setEnabled(true);
                        }
                    }
                });
            }
        });

    }


}
