package org.techtown.club.retrofit;


import org.techtown.club.sendServerData.IdTokenObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST("api/udong/idToken")
    Call<String> sendTokenToServer(@Body IdTokenObject idTokenObject);

    @GET("/api/udong/user/club/{userId}")
    Call<ResponseBody> getClubList(@Path("userId")Long userId);
}
