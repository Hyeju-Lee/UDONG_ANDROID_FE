package org.techtown.club.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.PreferenceManager;
import org.techtown.club.dto.Notice;
import org.techtown.club.R;
import org.techtown.club.dto.NoticeResponse;
import org.techtown.club.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {
    private View view;
    private FloatingActionButton floatingActionButton;

    private ListView noticeListView;
    private List<NoticeResponse> noticeList;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice,container, false);

        mContext = getActivity();
        getUserRole();
        noticeListView = (ListView) view.findViewById(R.id.noticeListView);
        noticeList = new ArrayList<>();
        getNotices();

        //NoticeListAdapter adapter = new NoticeListAdapter(getContext(),noticeList);
        //noticeListView.setAdapter(adapter);

        floatingActionButton = view.findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), NoticeWriteActivity.class);
                startActivity(intent);
            }

        });

        return view;


    }

    public void getNotices() {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Call<ResponseBody> call = RetrofitClient.getApiService().getNotice(clubId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get notice","error code"+response.code());
                    return;
                }
                try {
                    String result = response.body().string();
                    Log.d("연결완료 notice",result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String author = jsonObject.getString("author");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");
                        String date = jsonObject.getString("createdDate");
                        date = date.substring(0, 10);
                        NoticeResponse noticeResponse = new NoticeResponse(title, author, date, content);
                        noticeList.add(noticeResponse);
                        Log.d("notice 확인", date + title);
                        NoticeListAdapter adapter = new NoticeListAdapter(getContext(),noticeList);
                        noticeListView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                    boolean auth = jsonObject.getBoolean("notice_auth");
                    PreferenceManager.setBoolean(mContext, "notice_auth", auth);
                    Boolean bo = PreferenceManager.getBoolean(mContext,"notice_auth");
                    Log.d("auth확인",Boolean.toString(bo));
                    floatingActionButton.setVisibility(View.VISIBLE);
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