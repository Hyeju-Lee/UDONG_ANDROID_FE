package org.techtown.club.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.PreferenceManager;
import org.techtown.club.dto.Notice;
import org.techtown.club.NoticeListAdapter2;
import org.techtown.club.R;
import org.techtown.club.dto.NoticeResponse;
import org.techtown.club.notice.NoticeListAdapter;
import org.techtown.club.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton2;
    private ListView noticeListView;
    private List<NoticeResponse> noticeList;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        mContext = this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("확인",Integer.toString( PreferenceManager.getInt(mContext, "teamNumber")));
        getUserTeam();
        getPostList();

        noticeListView = findViewById(R.id.noticeListView);
        noticeList = new ArrayList<>();
        /*noticeList.add(new Notice("33333","김예나","2021-08-12",
                        " \uD83D\uDC99프로젝트 발표회 안내\uD83D\uDC99 \n" +
                        "\n" +
                        "26기 여러분! 여름방학 잘 보내고 계신가요?\n" +
                        "\n" +
                        "어느덧 대략 한 학기 동안 준비하신 프로젝트를 끝을 향해가고 있습니다. 얼마 남지 않은 만큼 조금만 더 힘을 내주세요~\n" +
                        "이번 1학기 프로젝트 발표회는 8월 21일(토) 14:00~17:00에 진행하려고 합니다\uD83C\uDF89\uD83C\uDF89\n")
        );*/

        //NoticeListAdapter2 adapter = new NoticeListAdapter2(getBaseContext(),noticeList);
        //noticeListView.setAdapter(adapter);

        floatingActionButton2 = findViewById(R.id.floatingActionButton2);

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(GroupActivity.this, GroupWriteActivity.class);
                GroupActivity.this.startActivity(registerIntent);
            }
        });

    }

    public void getPostList() {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        int teamNumber = PreferenceManager.getInt(mContext, "teamNumber");
        Call<ResponseBody> call = RetrofitClient.getApiService().getPostList(clubId, teamNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get post list","error code"+response.code());
                    return;
                }
                try {
                    String result = response.body().string();
                    Log.d("연결 완료 get post list", result);
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
                        Log.d("post 확인", date + title);
                        NoticeListAdapter adapter = new NoticeListAdapter(mContext,noticeList);
                        noticeListView.setAdapter(adapter);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("연결 실패 get post list",t.getMessage());
            }
        });
    }

    public void getUserTeam() {
        Long userId = PreferenceManager.getLong(mContext, "userId");
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Call<Integer> call = RetrofitClient.getApiService().getUserTeam(userId, clubId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get user team","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 get user team",response.body().toString());
                PreferenceManager.setInt(mContext, "userTeamNumber", response.body());
                if (PreferenceManager.getInt(mContext, "teamNumber") == response.body()) {
                    floatingActionButton2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("연결 실패 get user team", t.getMessage());
            }
        });
    }
}