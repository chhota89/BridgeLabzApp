package com.bridgelabz.app.retrofit;

import com.bridgelabz.app.model.LoginModel;
import com.bridgelabz.app.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by bridgeit on 20/6/16.
 */

public interface LoginInterface {

    @Headers( "Content-Type: application/json" )
    @POST("/md/loginWebService.php")
    Call<LoginResponse> getLogin(@Body LoginModel loginModel);
}
