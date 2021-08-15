package org.techtown.club.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL = "http://ec2-3-38-59-33.ap-northeast-2.compute.amazonaws.com/";
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static RetrofitAPI getApiService(){return getInstance().create(RetrofitAPI.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
