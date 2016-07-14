package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bridgelabz.app.R;
import com.bridgelabz.app.fragment.FragmentOne;
import com.bridgelabz.app.fragment.FragmentTwo;

public class FragmentDemo extends AppCompatActivity {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentManager=getSupportFragmentManager();

    }

    public void fragmentTwo(View view){
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment,new FragmentTwo());
        mFragmentTransaction.commit();
    }

    public void fragmentOne(View view){
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment,new FragmentOne());
       mFragmentTransaction.commit();
    }
}
