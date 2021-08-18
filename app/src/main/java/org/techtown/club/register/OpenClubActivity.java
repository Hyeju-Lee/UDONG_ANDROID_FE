package org.techtown.club.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.club.MainActivity;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.Club;
import org.techtown.club.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenClubActivity extends AppCompatActivity {

    ListView listView1;
    ListViewAdapter_openClub adapter;
    ArrayList<String> listItem;

    EditText groupjob;
    Button jobaddbutton;

    EditText groupName;
    EditText groupNum;
    EditText groupCode;

    Button checkBtn;
    TextView clubInfo;
    Button makeGroupBtn;
    TextView textView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openclub);
        context = this;

        groupjob = findViewById(R.id.groupjob);
        jobaddbutton = findViewById(R.id.jobaddbutton);
        listView1 = (ListView) findViewById(R.id.listView1);

        groupName = findViewById(R.id.groupname);
        groupCode = (EditText)findViewById(R.id.groupcode);
        groupNum = findViewById(R.id.groupnumber);

        clubInfo = (TextView)findViewById(R.id.clubInfo);
        checkBtn = findViewById(R.id.checkBtn);

        textView = findViewById(R.id.textView);

        adapter = new ListViewAdapter_openClub(OpenClubActivity.this);
        listView1.setAdapter(adapter);

        makeGroupBtn = findViewById(R.id.makegroupbutton2);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeCheck(groupCode.getText().toString());
            }
        });

        jobaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.addItem(groupjob.getText().toString());
                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                groupjob.setText("");
            }
        });

        Button searchgroupbutton = (Button) findViewById(R.id.searchgroupbutton);

        listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        searchgroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(OpenClubActivity.this, JoinClubActivity.class);
                OpenClubActivity.this.startActivity(registerIntent);
            }
        });


        makeGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupName.getText().toString();
                int generation = Integer.parseInt(groupNum.getText().toString());
                String code = groupCode.getText().toString();
                String info = clubInfo.getText().toString();
                //Log.d("이름 확인",name);

                Club club = new Club(name, generation, info, code);
                openClub(club);

                //Intent registerIntent = new Intent(OpenClubActivity.this, MainActivity.class);
                //OpenClubActivity.this.startActivity(registerIntent);
            }
        });
    }

    public void openClub(Club club) {
        Call<Long> call = RetrofitClient.getApiService().postClub(club);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("club 연결 비정상","error code"+response.code());
                    return;
                }
                Long clubId = PreferenceManager.getLong(context,"clubId");
                PreferenceManager.setLong(context,"clubId",response.body());
                String log = Long.toString(PreferenceManager.getLong(context,"clubId"));
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("club 연결 실패", t.getMessage());
            }
        });
    }

    public boolean check;
    public boolean codeCheck(String code){
        Log.d("코드 확인",code);
        Call<Boolean> call = RetrofitClient.getApiService().checkCode(code);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    Log.e("code check 연결 비정상","error code"+response.code());
                    return;
                }
                Log.d("code check성공",response.body().toString());;
                check = response.body();
                Log.d("확인",Boolean.toString(check));
                if (check) {
                    textView.setText("사용할 수 없는 가입코드입니다.");
                    textView.setTextColor(Color.parseColor("#FF0000"));
                    Log.d("사용불가","ㅌㅌ");
                }else {
                    textView.setText("사용할 수 있는 가입코드입니다.");
                    Log.d("가능","ㅇ");
                }
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("code check 연결 실패", t.getMessage());
            }
        });
        return check;
    }
}