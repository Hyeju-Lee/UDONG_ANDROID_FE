package org.techtown.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NoticeWriteActivity extends AppCompatActivity {

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_write);
        submitButton = findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(NoticeWriteActivity.this, MainActivity.class);
                NoticeWriteActivity.this.startActivity(registerIntent);
            }
        });
    }
}