package org.techtown.club.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.club.MainActivity;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.Club;
import org.techtown.club.dto.Role;
import org.techtown.club.retrofit.RetrofitClient;

import java.lang.reflect.Type;
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
    List<Role> roles;
    CheckBox authCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openclub);
        context = this;
        roles = new ArrayList<>();

        groupjob = findViewById(R.id.groupjob);
        jobaddbutton = findViewById(R.id.jobaddbutton);
        listView1 = (ListView) findViewById(R.id.listView1);

        groupName = findViewById(R.id.groupname);
        groupCode = (EditText)findViewById(R.id.groupcode);
        groupNum = findViewById(R.id.groupnumber);

        clubInfo = (TextView)findViewById(R.id.clubInfo);
        checkBtn = findViewById(R.id.checkBtn);

        textView = findViewById(R.id.textView);
        authCheck = findViewById(R.id.authCheck);

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
                adapter.addItem(groupjob.getText().toString(), authCheck.isChecked());
                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                groupjob.setText("");
                authCheck.setChecked(false);
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
                for (int i = 0; i < ListViewAdapter_openClub.listItems.size();i++) {
                    ListItemDetail_register detail_register = ListViewAdapter_openClub.listItems.get(i);
                    String roleName = detail_register.getWhat();
                    boolean checked = detail_register.isAuth();
                    Log.d("확인", roleName + checked);
                    Role role = new Role(roleName, checked);
                    roles.add(role);
                }
                Role r2 = new Role("총무", true);
                roles.add(r2);
                String name = groupName.getText().toString();
                int generation = Integer.parseInt(groupNum.getText().toString());
                String code = groupCode.getText().toString();
                String info = clubInfo.getText().toString();
                Log.d("이름 확인",name+code+info+generation);
                Club club = new Club(name, generation, info, code);
                openClub(club);
            }
        });


    }

    public void sendRole() {
        Long clubId = PreferenceManager.getLong(context,"clubId");
        Call<Void> call = RetrofitClient.getApiService().sendRoles(clubId, roles);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 send role", "error code" + response.code());
                    return;
                }
                Log.d("연결 성공 send role", "성공");
                addLeaderRole();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("연결 실패 send role", t.getMessage());
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
                //Long clubId = PreferenceManager.getLong(context,"clubId");
                PreferenceManager.setLong(context,"clubId",response.body());
                String log = Long.toString(PreferenceManager.getLong(context,"clubId"));
                Log.d("clubId 확인",log);
                sendRole();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("club 연결 실패", t.getMessage());
            }
        });
    }

    public void addLeaderRole() {
        Role role = new Role("회장", true);
        Call<Long> call = RetrofitClient.getApiService()
                .addLeaderRole(PreferenceManager.getLong(context,"clubId"), role);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 setReader","error code"+response.code());
                    return;
                }
                Long clubRoleId = response.body();
                PreferenceManager.setLong(context, "clubRoleId",clubRoleId);
                Log.d("연결성공 setLeader",response.body().toString());
                setRole(PreferenceManager.getLong(context,"clubRoleId"));
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("연결 실패 setLeader", t.getMessage());
            }
        });
    }

    public void registerUser() { //user를 동아리에 가입시키는 function
        Long userId = PreferenceManager.getLong(context,"userId");
        Long clubId = PreferenceManager.getLong(context, "clubId"); //나중에 클럽 여러개 가입 가능하면 바꾸기!!!!
        Call<Long> call = RetrofitClient.getApiService().registerUserToClub(clubId,userId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 register user","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 register user",response.body().toString());
                Intent registerIntent = new Intent(OpenClubActivity.this, MainActivity.class);
                OpenClubActivity.this.startActivity(registerIntent);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결 실패 register user", t.getMessage());
            }
        });
    }

    public void setRole(Long clubRoleId) {
        Long userId = PreferenceManager.getLong(context, "userId");
        Call<Long> call = RetrofitClient.getApiService().setUserRole(userId, clubRoleId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 set role","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 set role",response.body().toString());
                registerUser();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결 실패 set role", t.getMessage());
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
                    textView.setTextColor(Color.parseColor("#00FF00"));
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