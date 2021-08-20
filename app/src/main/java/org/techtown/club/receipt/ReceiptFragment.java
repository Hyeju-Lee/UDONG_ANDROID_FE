package org.techtown.club.receipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.ReceiptListDto;
import org.techtown.club.retrofit.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptFragment extends Fragment{
    private View view;
    Button button1;
    Button button_add;
    ListView listView;
    Context mContext;
    List<ReceiptListDto> receiptDtos;
    List<String> useDateList;
    ArrayList<ListItemDetail_Frag2> listViewData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt,container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        receiptDtos = new ArrayList<>();
        useDateList = new ArrayList<>();
        listViewData = new ArrayList<>();
        listView = view.findViewById(R.id.listView);
        mContext = getActivity();
        button_add = view.findViewById(R.id.button_add);
        getUserRole();
        getReceipts();


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MoneyAdd.class);
                startActivity(intent);
            }

        });
    }

    public void getReceipts() {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Call<Object> call = RetrofitClient.getApiService().getClub(clubId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get notice","error code"+response.code());
                    return;
                }
                try {
                    String result = new Gson().toJson(response.body());
                    Log.d("연결완료 receipt",result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("receipts");
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String useDate = object.getString("useDate");
                        Log.d("receipt 리스트",useDate);
                        if (!useDateList.contains(useDate))
                            useDateList.add(useDate);
                    }
                    for (String use : useDateList) {
                        Log.d("useDate테스트",use);
                        getReceiptList(use);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public void getReceiptList(String useDate) {
        Long clubId = PreferenceManager.getLong(mContext,"clubId");
        Call<ResponseBody> call = RetrofitClient.getApiService().getReceiptList(clubId, useDate);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get receipt list","error code"+response.code());
                    return;
                }
                try {
                    int plusCost = 0;
                    int minusCost = 0;
                    String result = response.body().string();
                    Log.d("연결완료 get receipt list",result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cost = jsonObject.getString("cost");
                        if (cost.charAt(0) == '+') {
                            plusCost += Integer.parseInt(cost.substring(1));
                        }
                        else {
                            minusCost += Integer.parseInt(cost.substring(1));
                        }
                    }
                    Log.d("cost 확인",plusCost+"///"+minusCost+useDate);
                    //for (int i = 0; i < useDateList.size(); i++) {
                    ListItemDetail_Frag2 listData = new ListItemDetail_Frag2("+"+plusCost,"-"+minusCost,useDate);
                        //item[i][0], item[i][1], item[i][2]);

                    listViewData.add(listData);
                    //}

                    ListAdapter customAdapter = new MoneyListAdapter_Frag2(getContext(),listViewData);
                    listView.setAdapter(customAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String useDate = listViewData.get(i).useDate;
                            Log.d("확인","플러스 텍스트는 "+ useDate);
                            PreferenceManager.setString(mContext,"useDate",useDate);
                            Intent intent = new Intent(getActivity(), DetailMoneyActivity.class);
                            startActivity(intent);
                        }
                    });
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

    public void getUserRole() {
        Long userId = PreferenceManager.getLong(mContext,"userId");
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Log.d("id들 확인",userId+"//"+clubId);
        Call call = RetrofitClient.getApiService().getUserRole(userId,clubId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    Log.e("get userRole 연결 비정상","error code"+response.code());
                    return;
                }
                String result = new Gson().toJson(response.body());
                //연결이 잘 됐고, 해당 클럽코드를 가진 동아리가 있을 때
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String role = jsonObject.getString("name");
                    boolean auth = jsonObject.getBoolean("notice_auth");
                    PreferenceManager.setBoolean(mContext, "notice_auth", auth);
                    Boolean bo = PreferenceManager.getBoolean(mContext,"notice_auth");
                    Log.d("auth확인",Boolean.toString(bo));
                    if (role.equals("총무") || role.equals("회장")) {
                        button_add.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("user role 연결 실패", t.getMessage());
            }
        });
    }

}