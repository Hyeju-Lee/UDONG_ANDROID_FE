package org.techtown.club.notice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.techtown.club.MainActivity;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.Notice;
import org.techtown.club.retrofit.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.InputStream;

public class NoticeWriteActivity extends AppCompatActivity {

    Button submitButton;
    ImageView imageView;
    //Button addImageButton;
    EditText titleText;
    EditText contentText;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_write);
        mContext = this;


        submitButton = findViewById(R.id.submitButton);
        imageView = findViewById(R.id.imageView);
        //addImageButton = findViewById(R.id.addImageButton);
        titleText = findViewById(R.id.noticeTitle);
        contentText = findViewById(R.id.noticeContent);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNotice();
                Intent registerIntent = new Intent(NoticeWriteActivity.this, MainActivity.class);
                NoticeWriteActivity.this.startActivity(registerIntent);
            }
        });


     /*   addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });*/
    }

    public void postNotice() {
        Long userId = PreferenceManager.getLong(mContext,"userId");
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Log.d("id들 확인(notice)",userId+"//"+clubId);
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        Notice notice = new Notice(title, content);
        Log.d("title확인",notice.getTitle()+notice.getContent());
        Call<Long> call = RetrofitClient.getApiService().postNotice(clubId, userId,notice);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 post notice","error code"+response.code());
                    return;
                }
                Log.d("연결 성공 post notice",Long.toString(response.body()));
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결 실패 post notice", t.getMessage());
            }
        });

    }

       /* @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
            // Check which request we're responding to
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    try {
                        // 선택한 이미지에서 비트맵 생성
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        // 이미지 표시
                        imageView.setImageBitmap(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/
    }