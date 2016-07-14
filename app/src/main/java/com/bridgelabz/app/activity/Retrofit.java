package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bridgelabz.app.R;
import com.bridgelabz.app.adapter.DrawerAdapter;
import com.bridgelabz.app.model.UserInfoList;
import com.bridgelabz.app.retrofit.ApiClient;
import com.bridgelabz.app.retrofit.ListInterface;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


        //RxAndroid with retrofit to retrieve data from the server.
        ListInterface listInterface=ApiClient.getClient().create(ListInterface.class);
        Observable<UserInfoList> userInfoListObservable=listInterface.getUserInfoRx();
        userInfoListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoList>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfoList userInfoList) {
                        DrawerAdapter adapter=new DrawerAdapter(userInfoList.getResult(),Retrofit.this);
                        recyclerView.setAdapter(adapter);
                    }
                });

        /*//Displaying data using retrofit get method
        ListInterface listInterface=ApiClient.getClient().create(ListInterface.class);
        Call<UserInfoList> userInfoCall=listInterface.getUserInfo();
        userInfoCall.enqueue(new Callback<UserInfoList>() {
            @Override
            public void onResponse(Call<UserInfoList> call, Response<UserInfoList> response) {
                UserInfoList userInfoList = response.body();
                DrawerAdapter adapter=new DrawerAdapter(userInfoList.getResult(),Retrofit.this);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<UserInfoList> call, Throwable t) {
            }
        });*/



        /*
        //Sending json as retrofit Request
        LoginInterface loginInterface=ApiClient.getClient().create(LoginInterface.class);
        Call<LoginResponse> loginResponseCall=loginInterface.getLogin(new LoginModel("Chhota89@gmail.com","jjdkfj"));
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                if(loginResponse.getSuccess())
                    Toast.makeText(Retrofit.this,"Login Success",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Retrofit.this,"Login fails",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });*/

    }

}
