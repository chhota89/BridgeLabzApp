package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bridgelabz.app.R;
import com.bridgelabz.app.adapter.HeterogenousAdapter;
import com.bridgelabz.app.model.UserInfo;

import java.util.ArrayList;
import java.util.Objects;

public class HeterogenousRecycleView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heterogenous_recycle_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleView);

        ArrayList<Object> objectsArrayList=new ArrayList<Object>();
        objectsArrayList.add(new UserInfo("mohmad","chhota"));
        objectsArrayList.add("Image");
        objectsArrayList.add(new UserInfo("dfds","fdf"));
        objectsArrayList.add(new UserInfo("mgfohmad","fdx"));
        objectsArrayList.add(new UserInfo("hy","gf"));
        objectsArrayList.add("Image");
        objectsArrayList.add(new UserInfo("gku","yuy"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HeterogenousAdapter adapter=new HeterogenousAdapter(objectsArrayList);

        recyclerView.setAdapter(adapter);
    }

}
