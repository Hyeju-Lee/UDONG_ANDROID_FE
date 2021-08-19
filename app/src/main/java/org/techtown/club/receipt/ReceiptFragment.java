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

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.ListItemDetail_Frag2;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.ReceiptListDto;
import org.techtown.club.retrofit.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ReceiptFragment extends Fragment{
    private View view;
    Button button1;
    Button button_add;
    ListView listView;
    Context mContext;
    List<ReceiptListDto> receiptDtos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt,container, false);
        mContext = getActivity();
        button_add = view.findViewById(R.id.button_add);
        getUserRole();

        receiptDtos = new ArrayList<>();

        String[][] item = {{"+3000", "-5000", "2021-05-15"}, {"+5000", "-1000", "2021-04-15"},
                {"+9000", "-8000", "2021-06-15"}, {"+3000", "-7000", "2021-08-15"}};

        listView = (ListView)view.findViewById(R.id.listView);
        ArrayList<ListItemDetail_Frag2> listViewData = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            ListItemDetail_Frag2 listData = new ListItemDetail_Frag2(item[i][0],
                    item[i][1], item[i][2]);

            listViewData.add(listData);
        }

        ListAdapter customAdapter = new MoneyListAdapter_Frag2(getContext(),listViewData);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickname = listViewData.get(i).plusText;
                Log.d("확인","플러스 텍스트는 "+ clickname);
            }
        });

        button1 = view.findViewById(R.id.button1); //1월 버튼
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), MoneyActivity2.class);
                Intent intent= new Intent(getActivity(), DetailMoneyActivity.class);
                startActivity(intent);
            }

        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MoneyAdd.class);
                startActivity(intent);
            }

        });


        return view;
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
                    if (role.equals("회장")) {            //나중에 총무로 바꾸기! 시연영상을 위해 바꿔놓음
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