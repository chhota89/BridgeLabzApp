package com.bridgelabz.app.retrofit;

import com.bridgelabz.app.model.UserInfoList;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;
/**
 * Created by bridgeit on 15/6/16.
 */

public interface ListInterface {

    @GET("/md/mohmad.json")
    Call<UserInfoList> getUserInfo();


    @GET("/md/mohmad.json")
    Observable<UserInfoList> getUserInfoRx();
}
