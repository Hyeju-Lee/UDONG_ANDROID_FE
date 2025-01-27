package org.techtown.club.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.MainActivity;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.retrofit.RetrofitClient;
import org.techtown.club.sendServerData.IdTokenObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    public List<String> clubName;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        clubName = new ArrayList<>();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
/*
        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, OpenClubActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(Intent);
            }
        });*/
    }

    private void refreshIdToken() {
        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        handleSignInResult(task);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //이미 로그인 된 사용자일 때
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            sendIdTokenToServer(idToken);
            updateUI(account);
        }catch (ApiException e) {
            Log.w("signInActivity", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void sendIdTokenToServer(String idToken) {
        IdTokenObject idTokenObject = new IdTokenObject(idToken);
        Call<String> call = RetrofitClient.getApiService().sendTokenToServer(idTokenObject);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 send token","error code"+response.code());
                    return;
                }
                Long userId = Long.parseLong(response.body());
                PreferenceManager.setLong(mContext, "userId", userId);
                Log.d("연결 성공 send token",Long.toString(userId));
                getClubList();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("연결 실패 send token", t.getMessage());
            }
        });
    }

    private void getClubList() {
        Long userId = PreferenceManager.getLong(mContext,"userId");
        Call<ResponseBody> call = RetrofitClient.getApiService().getClubList(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get club list","error code"+response.code());
                    return;
                }
                try{
                    String result = response.body().string();
                    Log.d("연결 완료 club list", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        PreferenceManager.setLong(mContext,"clubId",jsonObject.getLong("id"));
                        clubName.add(jsonObject.getString("name"));
                        String cl = Long.toString(PreferenceManager.getLong(mContext,"clubId"));
                        Log.d("*********clubId=",cl);
                        Log.d("*********club name=",clubName.get(0));
                    }
                    if (PreferenceManager.getLong(mContext,"clubId") == -1L) {
                        Intent intent = new Intent(mContext, OpenClubActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }
                }catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("연결 실패 club list", t.getMessage());
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() { //탈퇴
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            String idToken = account.getIdToken();
            sendIdTokenToServer(idToken);
        }
    }
}