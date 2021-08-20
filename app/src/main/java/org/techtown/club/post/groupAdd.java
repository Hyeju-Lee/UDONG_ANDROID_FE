package org.techtown.club.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.MainActivity;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.Receipt;
import org.techtown.club.receipt.ListItemDetail;
import org.techtown.club.receipt.ListViewDetailAdapter_MoneyAdd1;
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

public class groupAdd extends AppCompatActivity {
    ListView peopleNameListView;
    Button nameButton;
    //EditText nameAddView;
    Spinner spinner;
    Context mContext;
    List<String> nameArrayList;
    HashMap<Long, String> hashMap;
    EditText groupName;
    List<Long> clubUserIdList;
    ListViewDetailAdapter_GroupAdd adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);
        mContext = this;
        nameArrayList = new ArrayList<>();
        hashMap = new HashMap<>();
        clubUserIdList = new ArrayList<>();
        nameButton = findViewById(R.id.nameButton);
        groupName = findViewById(R.id.groupName);
        peopleNameListView = (ListView) findViewById(R.id.peopleNameListView);
        getUserList();

        //String[] emoticonList = {"\uD83D\uDE00","\uD83D\uDE3A", "\uD83D\uDE0D"};

        //Spinner emoticonSpinner = (Spinner)findViewById(R.id.emoticonSpinner);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                emoticonList);*/
        //emoticonSpinner.setAdapter(adapter);
        //emoticonSpinner.setSelection(0);


        /*String[] nameList = {"가","나","다","라","마"};
        spinner = findViewById(R.id.nameSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item,nameList
        );
        spinner.setAdapter(adapter);

        //nameAddView = findViewById(R.id.nameAddView);
        nameButton = findViewById(R.id.nameButton);
        ListViewDetailAdapter_GroupAdd adapter3 = new ListViewDetailAdapter_GroupAdd(groupAdd.this);
        peopleNameListView = (ListView) findViewById(R.id.peopleNameListView);
        peopleNameListView.setAdapter(adapter3);


        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();
                Log.d("스피너",text);
                adapter3.addItem(text);
                adapter3.notifyDataSetChanged();
            }
        });*/


        Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserId();
            }
        });


    }

    public void getUserList() {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Call<ResponseBody> call = RetrofitClient.getApiService().getUserList(clubId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get user list","error code"+response.code());
                    return;
                }

                try {
                    String result = response.body().string();
                    Log.d("연결 완료 user list", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        Long id = jsonObject.getLong("id");
                        nameArrayList.add(name);
                        hashMap.put(id, name);
                    }
                    //nameArrayList.add("가");
                    //nameArrayList.add("나");
                    //nameArrayList.add("다");
                    spinner = findViewById(R.id.nameSpinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            mContext, R.layout.support_simple_spinner_dropdown_item,nameArrayList
                    );
                    spinner.setAdapter(adapter);

                    adapter3 = new ListViewDetailAdapter_GroupAdd(groupAdd.this);
                    peopleNameListView.setAdapter(adapter3);


                    nameButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = spinner.getSelectedItem().toString();
                            Log.d("스피너",text);
                            adapter3.addItem(text);
                            adapter3.notifyDataSetChanged();
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("연결 실패 user list", t.getMessage());
            }
        });
    }

    public void getUserId() {
        for (int i =0 ; i < ListViewDetailAdapter_GroupAdd.listItems4.size();i++) {
            ListItemDetail2 detail2 = ListViewDetailAdapter_GroupAdd.listItems4.get(i);
            String name = detail2.getWhat();
            Log.d("이름 확인",name);
            Long userId = getKey(hashMap,name);
            getClubUserId(userId);
        }
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public void getClubUserId(Long userId) {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Call<Long> call = RetrofitClient.getApiService().getClubUserId(userId, clubId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 clubuserid","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 clubuserid",response.body().toString());
                setTeam(response.body());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결실패 clubuserid",t.getMessage());
            }
        });
    }


    public void setTeam(Long clubUserId) {
        int teamNumber = Integer.parseInt(groupName.getText().toString());
        Call<Long> call = RetrofitClient.getApiService().setTeamNumber(clubUserId, teamNumber);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 set team","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 set team",response.body().toString());
                adapter3.clear();
                finish();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결실패 set team",t.getMessage());
            }
        });
    }
}