package org.techtown.club.retrofit;


import android.util.Log;

import org.techtown.club.dto.Club;
import org.techtown.club.dto.ClubPost;
import org.techtown.club.dto.Notice;
import org.techtown.club.dto.Receipt;
import org.techtown.club.dto.Role;
import org.techtown.club.register.LoginActivity;
import org.techtown.club.sendServerData.IdTokenObject;

import java.lang.invoke.MethodHandles;
import java.util.List;

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

    @GET("api/udong/user/clubRole/{userId}/{clubId}")
    Call<Object> getUserRole(@Path("userId")Long userId, @Path("clubId")Long clubId);

    @POST("api/udong/notice/{club_id}/{userId}")
    Call<Long> postNotice(@Path("club_id")Long club_id, @Path("userId")Long userId,
                          @Body Notice notice);

    @GET("api/udong/club/notice/{clubId}")
    Call<ResponseBody> getNotice(@Path("clubId")Long clubId);

    @POST("api/udong/receipt/{club_id}/{userId}")
    Call<Void> postReceipt(@Path("club_id")Long club_id, @Path("userId")Long userId,
                           @Body List<Receipt> receipts);

    @GET("api/udong/club/{id}")
    Call<Object> getClub(@Path("id")Long id);

    @GET("api/udong/receipt/useDate/{clubId}/{useDate}")
    Call<ResponseBody> getReceiptList(@Path("clubId")Long clubId, @Path("useDate")String useDate);

    @GET("api/udong/club/user/{clubId}")
    Call<ResponseBody> getUserList(@Path("clubId")Long clubId);

    @GET("api/udong/user/clubUser/{userId}/{clubId}")
    Call<Long> getClubUserId(@Path("userId")Long userId, @Path("clubId")Long clubId);

    @GET("api/udong/clubUser/teamNumber/{id}/{teamNumber}")
    Call<Long> setTeamNumber(@Path("id")Long id, @Path("teamNumber")int teamNumber);

    @GET("api/udong/clubUser/teamNumber/user/{clubId}")
    Call<ResponseBody> getTeam(@Path("clubId")Long clubId);

    @GET("api/udong/user/teamNumber/{userId}/{clubId}")
    Call<Integer> getUserTeam(@Path("userId")Long userId, @Path("clubId")Long clubId);

    @GET("api/udong/post/teamNumber/{clubId}/{teamNumber}")
    Call<ResponseBody> getPostList(@Path("clubId")Long clubId, @Path("teamNumber")int teamNumber);

    @POST("/api/udong/post/{club_id}/{userId}")
    Call<Long> sendPost(@Path("club_id")Long club_id, @Path("userId")Long userId, @Body ClubPost clubPost);

    @POST("api/udong/clubRole/roles/{clubId}")
    Call<Void> sendRoles(@Path("clubId")Long clubId, @Body List<Role> roles);
}
