package org.techtown.club.retrofit;


import org.techtown.club.sendServerData.IdTokenObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("api/udong/idToken")
    Call<String> sendTokenToServer(@Body IdTokenObject idTokenObject);
}
