package com.bridgelabz.app.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.activity.DisplayData;
import com.bridgelabz.app.activity.ORM_Activity;
import com.bridgelabz.app.activity.RealmActivity;
import com.bridgelabz.app.database.DBHelper;
import com.bridgelabz.app.model.User;
import com.bridgelabz.app.utility.NumberUtility;

/**
 * Created by bridgelabz5 on 26/5/16.
 */
public class SqliteFragment extends Fragment {
    View view;
    DBHelper dbHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.sqlite,container,false);
        dbHelper=new DBHelper(view.getContext());
        final Button display,update,insert,delete,orm,realm;
        display=(Button)view.findViewById(R.id.display);
        update=(Button)view.findViewById(R.id.updateValue);
        delete=(Button)view.findViewById(R.id.deleteValue);
        insert=(Button)view.findViewById(R.id.insertValue);
        orm=(Button)view.findViewById(R.id.orm);
        realm=(Button)view.findViewById(R.id.realm);

        //open ORM Activity.
        orm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), ORM_Activity.class));
            }
        });

        //Realm Activity
        realm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), RealmActivity.class));
            }
        });

        //Create Table
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), DisplayData.class);
                intent.putExtra("DATABASE_TYPE","sqlite");
                getActivity().startActivity(intent);
            }
        });

        //Update the value of user
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.insert_user_details_dialog);
                dialog.setTitle("Update User Details");

                final TextView name,age;
                name=(TextView)dialog.findViewById(R.id.userName);
                age=(TextView)dialog.findViewById(R.id.age);
                name.setFocusable(false);
                age.setFocusable(false);
                ImageView search=(ImageView)dialog.findViewById(R.id.search);
                final Button insertdetails=(Button)dialog.findViewById(R.id.insertValue);
                final TextView phoneNumber=(TextView)dialog.findViewById(R.id.phoneNumber);
                insertdetails.setText("Update");
                insertdetails.setEnabled(false);

                dialog.show();

                insertdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if(dbHelper.update(new User(name.getText().toString(),phoneNumber.getText().toString(),Integer.parseInt(age.getText().toString())))){
                                dialog.hide();
                                Toast.makeText(view.getContext(),getString(R.string.save_data_sqlite),Toast.LENGTH_LONG).show();
                            }

                        }catch (NumberFormatException exception){
                            Toast.makeText(view.getContext(),getString(R.string.error_enter_number),Toast.LENGTH_LONG).show();
                        }
                        catch (Exception exception){
                            Log.e("SQLITE_Fragemnt", "onClick: "+exception.getMessage());
                        }
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setFocusable(false);
                        age.setFocusable(false);
                        insertdetails.setEnabled(false);
                        Cursor cursor=dbHelper.getUserByMobileNumber(""+phoneNumber.getText().toString());
                        if(cursor.getCount()==0)
                            Toast.makeText(view.getContext(),getString(R.string.no_mobile_number_found),Toast.LENGTH_LONG).show();
                        else{
                            name.setText(cursor.getString(0));
                            age.setText(cursor.getString(1));
                            name.setEnabled(true);
                            age.setEnabled(true);
                            name.setFocusableInTouchMode(true);
                            age.setFocusableInTouchMode(true);
                            insertdetails.setEnabled(true);
                        }
                    }
                });

            }
        });

        //Insert the new Values to database
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.insert_user_details_dialog);
                dialog.setTitle("Insert User Details");
                ImageView search=(ImageView)dialog.findViewById(R.id.search);
                search.setVisibility(View.INVISIBLE);
                dialog.show();

                Button insertdetails=(Button)dialog.findViewById(R.id.insertValue);
                //Insert User Data when Button is pressed.
                insertdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Add user details to data base
                        TextView name,age,phoneNumber;
                        name=(TextView)dialog.findViewById(R.id.userName);
                        age=(TextView)dialog.findViewById(R.id.age);
                        phoneNumber=(TextView)dialog.findViewById(R.id.phoneNumber);
                        try{
                            long start=System.currentTimeMillis();
                            dbHelper.addContact(new User(name.getText().toString(),phoneNumber.getText().toString(),Integer.parseInt(age.getText().toString())));
                            long end=System.currentTimeMillis();
                            NumberUtility.getTime(start,end,"SQLITE Insert");
                            dialog.hide();
                            Toast.makeText(view.getContext(),getString(R.string.save_data_sqlite),Toast.LENGTH_LONG).show();
                        }catch (NumberFormatException exception){
                            Toast.makeText(view.getContext(),getString(R.string.error_enter_number),Toast.LENGTH_LONG).show();
                        }
                        catch (Exception exception){
                            Log.e("SQLITE_Fragemnt", "onClick: "+exception.getMessage());
                        }
                    }
                });

            }
        });

        //delete record from the database
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog that take user detils
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.insert_user_details_dialog);
                dialog.setTitle("Delete Record");

                final TextView name,age;
                name=(TextView)dialog.findViewById(R.id.userName);
                age=(TextView)dialog.findViewById(R.id.age);
                name.setFocusable(false);
                age.setFocusable(false);
                ImageView search=(ImageView)dialog.findViewById(R.id.search);
                final Button insertdetails=(Button)dialog.findViewById(R.id.insertValue);
                final TextView phoneNumber=(TextView)dialog.findViewById(R.id.phoneNumber);
                insertdetails.setText("Delete");
                insertdetails.setEnabled(false);

                dialog.show();

                insertdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dbHelper.deleteUser(phoneNumber.getText().toString())) {
                            dialog.hide();
                            Toast.makeText(view.getContext(), getString(R.string.record_deleted), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setFocusable(false);
                        age.setFocusable(false);
                        insertdetails.setEnabled(false);
                        Cursor cursor=dbHelper.getUserByMobileNumber(""+phoneNumber.getText().toString());
                        if(cursor.getCount()==0)
                            Toast.makeText(view.getContext(),getString(R.string.no_mobile_number_found),Toast.LENGTH_LONG).show();
                        else{
                            name.setText(cursor.getString(0));
                            age.setText(cursor.getString(1));
                            insertdetails.setEnabled(true);
                        }
                    }
                });
            }
        });
        return view;
    }
}
