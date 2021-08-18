package org.techtown.club.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.club.MainActivity;
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


    public boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openclub);

        groupjob = findViewById(R.id.groupjob);
        jobaddbutton = findViewById(R.id.jobaddbutton);
        listView1 = (ListView) findViewById(R.id.listView1);

        groupName = findViewById(R.id.groupname);
        groupCode = findViewById(R.id.groupcode);
        groupNum = findViewById(R.id.groupnumber);

        clubInfo = (TextView)findViewById(R.id.clubInfo);
        checkBtn = findViewById(R.id.checkBtn);

        adapter = new ListViewAdapter_openClub(OpenClubActivity.this);
        listView1.setAdapter(adapter);

        String name = groupName.getText().toString();
        int generation = Integer.parseInt(groupNum.getText().toString());
        String code = groupCode.getText().toString();
        String info = clubInfo.getText().toString();

        Club club = new Club(name, generation, info, code);
        openClub(club);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codeCheck(info)) {
                    //중복이라 사용 불가
                    TextView textView = (TextView)findViewById(R.id.textView);
                    textView.setText("사용할 수 없는 가입코드입니다.");
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    //사용 가능
                    TextView textView = (TextView)findViewById(R.id.textView);
                    textView.setText("사용할 수 있는 가입코드입니다.");
                    textView.setVisibility(View.VISIBLE);
                }
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

        Button makegroupbutton2 = (Button) findViewById(R.id.makegroupbutton2);

        makegroupbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(OpenClubActivity.this, MainActivity.class);
                OpenClubActivity.this.startActivity(registerIntent);
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
                List<Long> clubId = LoginActivity.clubId;
                clubId.add(response.body());
                Log.d("클럽 연결 완료",clubId.get(0).toString());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("club 연결 실패", t.getMessage());
            }
        });
    }

    private boolean codeCheck(String code){
        Call<Boolean> call = RetrofitClient.getApiService().checkCode(code);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상","error code"+response.code());
                    return;
                }
                Log.d("성공",response.body().toString());
                check = response.body();
                Log.d("확인",Boolean.toString(check));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결 실패", t.getMessage());
            }
        });
        return check;
    }
}