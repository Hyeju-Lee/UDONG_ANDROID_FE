package org.techtown.club.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.R;
import org.techtown.club.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinClubActivity extends AppCompatActivity {

    static List<String> job;
    static HashMap<Long,String> hashMap;
    public static Long clubid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinclub);

        Button makegroupbutton = (Button) findViewById(R.id.makegroupbutton);
        SearchView searchText = (SearchView)findViewById(R.id.search);

        String code = "dliejladlj"; //사용자가 입력한 code로 바꾸기
        searchClubCode(code);

        makegroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(JoinClubActivity.this, OpenClubActivity.class);
                JoinClubActivity.this.startActivity(registerIntent);
            }
        });

        job = new ArrayList<>();
        hashMap = new HashMap<>();

        Spinner groupSpinner = (Spinner)findViewById(R.id.spinner_group);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, job);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);


    }

    public void searchClubCode(String clubCode) {//클럽 코드로 동아리 정보 가져오기
        Call call = RetrofitClient.getApiService().getClubInfo(clubCode);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    Log.e("search club 연결 비정상","error code"+response.code());
                    return;
                }
                String result = new Gson().toJson(response.body());
                //연결이 잘 됐고, 해당 클럽코드를 가진 동아리가 있을 때
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    clubid = jsonObject.getLong("id");
                    String clubName = jsonObject.getString("name");
                    if (!jsonObject.isNull("info")){
                    String clubInfo = jsonObject.getString("info");
                    Log.d("info",clubInfo);}
                    int generation = jsonObject.getInt("generation");
                    Log.d("결과", "id"+clubid+"name"+clubName +"/generation"+generation);
                    getRoles();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                //해당 클럽코드 가진 동아리 없을 때
                Log.e("search club 연결 실패", t.getMessage());
            }
        });
    }

    public void registerUser() { //user를 동아리에 가입시키는 function
        Long userId = LoginActivity.userId;
        Long clubId = Long.parseLong("2");
        Call<Long> call = RetrofitClient.getApiService().registerUserToClub(userId, clubId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 register user","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 register user",response.body().toString());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결 실패 register user", t.getMessage());
            }
        });
    }

    public void getRoles() {
        Log.d("확인",clubid.toString());
        Call<ResponseBody> call = RetrofitClient.getApiService().getRoleList(clubid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get roles","error code"+response.code());
                    return;
                }
                try {
                    String result = response.body().string();
                    Log.d("연결 완료 get role", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        Long id = jsonObject.getLong("id");
                        Log.d("role","name"+name+"id"+id);
                        hashMap.put(id, name);
                        job.add(name);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("실패 get role", t.getMessage());
            }
        });
    }


}