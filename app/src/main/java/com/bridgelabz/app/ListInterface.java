package com.bridgelabz.app;

import com.bridgelabz.app.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by bridgeit on 15/6/16.
 */

public interface ListInterface {

    @GET("/md/mohmad.json")
    Call<UserInfo> getUserInfo();
}
