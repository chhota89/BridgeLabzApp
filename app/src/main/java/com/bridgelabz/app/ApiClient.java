package com.bridgelabz.app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bridgeit on 15/6/16.
 */

public class ApiClient {

    public static final String BASE_URL = "http://192.168.0.121/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
        }

        return retrofit;
    }

}
