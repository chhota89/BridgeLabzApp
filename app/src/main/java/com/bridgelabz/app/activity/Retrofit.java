package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bridgelabz.app.ApiClient;
import com.bridgelabz.app.ListInterface;
import com.bridgelabz.app.R;
import com.bridgelabz.app.adapter.DrawerAdapter;
import com.bridgelabz.app.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Retrofit extends AppCompatActivity {

    private final static String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ListInterface listInterface=ApiClient.getClient().create(ListInterface.class);

        /*listInterface.getUserInfo(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                ArrayList<UserInfo> userInfoArrayList= (ArrayList<UserInfo>) response.body();
                DrawerAdapter adapter=new DrawerAdapter(userInfoArrayList,Retrofit.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {

            }
        });*/

        /*Call<UserInfo> userInfoCall=listInterface.getUserInfo();

        userInfoCall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                String abc=response.body().getFirstName();
                List<UserInfo> userInfoList =  response.body();
                DrawerAdapter adapter=new DrawerAdapter(userInfoList,Retrofit.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

                String open="fjdkdf";
                int a=0;
            }
        });*/

    }

}
