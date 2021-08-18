package org.techtown.club.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.MainActivity;
import org.techtown.club.R;
import org.techtown.club.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinClubActivity extends AppCompatActivity {

    static ArrayList<String> job;
    static HashMap<String,Object> hashMap;
    //static Long clubid;
    static Long roleId;
    TextView textView;
    Long clubid;
    Button makeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinclub);

        Button makegroupbutton = (Button) findViewById(R.id.makegroupbutton);
        EditText searchText = (EditText) findViewById(R.id.search);
        makeBtn = (Button)findViewById(R.id.makeBtn);
        ImageButton searchBtn = (ImageButton) findViewById(R.id.searchBtn);

        textView = findViewById(R.id.textViewJoin);
        job = new ArrayList<>();
        hashMap = new HashMap<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchText.getText() != null)
                    searchClubCode(searchText.getText().toString());
            }
        });


        job.add("직급을 선택하세요");
        Spinner groupSpinner = (Spinner)findViewById(R.id.spinner_group);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, job);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
        groupSpinner.setSelection(0, false);

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getKey(hashMap,String.valueOf(job.get(i))) != null) roleId = Long.parseLong(getKey(hashMap,String.valueOf(job.get(i))));
                //Toast.makeText(JoinClubActivity.this,"선택된 아이템 : "+roleId.toString(),Toast.LENGTH_SHORT).show();
                getClubRoleId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    public void getClubRoleId() {
        Log.d("********",roleId.toString());
        Call<Long> call = RetrofitClient.getApiService().getClubRoleId(LoginActivity.clubId.get(0), roleId);  //////0으로 바꾸기
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get clubroleid","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 get clubroleid",response.body().toString());
                Long clubRoleId = response.body();
                makeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRole(clubRoleId);
                        registerUser();
                    }
                });
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결 실패 get clubroleid", t.getMessage());
            }
        });
    }

    public static <K, V> K getKey(Map<K, V>map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
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
                    LoginActivity.clubId.set(0, clubid);
                    String clubName = jsonObject.getString("name");
                    if (!jsonObject.isNull("info")){
                        String clubInfo = jsonObject.getString("info");
                        Log.d("info",clubInfo);
                        textView.setText("          "+clubInfo);
                    }
                    else {
                        textView.setText(null);
                    }
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
        Long clubId = LoginActivity.clubId.get(0);  //나중에 클럽 여러개 가입 가능하면 바꾸기!!!!
        Call<Long> call = RetrofitClient.getApiService().registerUserToClub(userId, clubId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 register user","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 register user",response.body().toString());
                Intent intent = new Intent(JoinClubActivity.this,MainActivity.class);
                startActivity(intent);
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
                        hashMap.put(id.toString(), name);
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

    public void setRole(Long clubRoleId) {
        Long userId = LoginActivity.userId;
        Call<Long> call = RetrofitClient.getApiService().setUserRole(userId, clubRoleId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 set role","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 set role",response.body().toString());

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결 실패 set role", t.getMessage());
            }
        });
    }


}