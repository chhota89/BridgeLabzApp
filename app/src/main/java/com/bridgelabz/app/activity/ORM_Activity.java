package com.bridgelabz.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bridgelabz.app.R;
import com.bridgelabz.app.database.ORM_Helper;
import com.bridgelabz.app.model.ORMUser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;

public class ORM_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orm);
        Button ormCreate=(Button)findViewById(R.id.ormCreate);
        ormCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ORM_Helper helper= OpenHelperManager.getHelper(ORM_Activity.this,ORM_Helper.class);

                //RuntimeExceptionDao<ORMUser,Integer> runtimeExceptionDao=helper.getRuntimeExceptionDao();
                try {
                    final Dao<ORMUser,Integer> userDao=helper.getDao();
                    //Insert User
                    userDao.create(new ORMUser("Mohmad",22));
                    userDao.create(new ORMUser("ABCD",26));
                } catch (SQLException e) {
                    e.printStackTrace();
                }



                OpenHelperManager.releaseHelper();
            }
        });
    }


}
