package org.techtown.club.receipt;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.Adapter;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;


public class DetailMoneyActivity extends AppCompatActivity {

    private Context mContext1;
    private ListView mListView1;
    private Adapter adapter1;

    String[][] item = new String[][]{
            {"지각","S", "+2000"}, {"지각", "A", "+2000"}, {"지각", "C", "+2000"},
         {"미제출", "A", "+2000"}, {"미흡제출", "D", "+3000"}, {"미흡제출", "Z", "+3000"}, {"지출", "10조 강의 구매", "-30000"}
    };

    private ArrayList<String>tmp1;
    private ArrayList<String>tmp2;
    private ArrayList<String>tmp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ... 코드 계속
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_money);
        this.mContext1 = getApplicationContext();
        this.mContext1 = getApplicationContext();
        mListView1 = (ListView) findViewById(R.id.listView1);
        getOnlyReceiptList();


        tmp1 = new ArrayList<>();
        tmp2 = new ArrayList<>();
        tmp3 = new ArrayList<>();
        for (int i=0; i<item.length; i++) {
            tmp1.add(item[i][0]);
            tmp2.add(item[i][1]);
            tmp3.add(item[i][2]);
        }


        adapter1 = new Adapter(mContext1, tmp1, tmp2, tmp3);
        mListView1.setAdapter(adapter1);
//        adapter2 = new Adapter(mContext2, tmp2);
//        mListView1.setAdapter(adapter2);
//        adapter3 = new Adapter(mContext3, tmp3);
//        mListView1.setAdapter(adapter3);

    }

    public void getOnlyReceiptList() {
        String useDate = PreferenceManager.getString(mContext1, "useDate");
        Log.d("check 유즈 데이트",useDate
        );
        Long clubId = PreferenceManager.getLong(mContext1,"clubId");
        Call<ResponseBody> call = RetrofitClient.getApiService().getReceiptList(clubId, useDate);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get receipt list","error code"+response.code());
                    return;
                }
                try {
                    String result = response.body().string();
                    Log.d("연결완료 get receipt list",result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cost = jsonObject.getString("cost");

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("연결 실패 get receipt list",t.getMessage());
            }
        });

    }

}