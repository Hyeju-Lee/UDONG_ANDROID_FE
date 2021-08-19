package org.techtown.club.retrofit;


import android.util.Log;

import org.techtown.club.dto.Club;
import org.techtown.club.dto.Role;
import org.techtown.club.sendServerData.IdTokenObject;

import java.lang.invoke.MethodHandles;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST("api/udong/idToken")
    Call<String> sendTokenToServer(@Body IdTokenObject idTokenObject);

    @GET("api/udong/user/club/{userId}")
    Call<ResponseBody> getClubList(@Path("userId")Long userId);

    @POST("api/udong/club")
    Call<Long> postClub(@Body Club club);

    @GET("api/udong/club/clubCode/check/{clubCode}")
    Call<Boolean> checkCode(@Path("clubCode") String clubCode);

    @GET("api/udong/club/clubCode/{clubCode}")
    Call<Object> getClubInfo(@Path("clubCode") String clubCode);

    @GET("api/udong/clubUser/{clubId}/{userId}")
    Call<Long> registerUserToClub(@Path("clubId") Long clubId, @Path("userId") Long userId);

    @GET("api/udong/club/role/{clubId}")
    Call<ResponseBody> getRoleList(@Path("clubId") Long clubId);

    @GET("api/udong/clubRole/{clubId}/{roleId}")
    Call<Long> getClubRoleId(@Path("clubId") Long clubId, @Path("roleId") Long roleId);

    @GET("api/udong/clubRoleUser/{userId}/{clubRole_Id}")
    Call<Long> setUserRole(@Path("userId") Long userId, @Path("clubRole_Id") Long clubRole_Id);

    @POST("api/udong/clubRole/{clubId}")
    Call<Long> addLeaderRole(@Path("clubId") Long clubId, @Body Role role);

}
