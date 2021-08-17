package org.techtown.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.techtown.club.register.JoinClubActivity;
import org.techtown.club.register.ListViewAdapter_openClub;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OpenClubActivity extends AppCompatActivity {

    ListView listView1;
    ListViewAdapter_openClub adapter;
    ArrayList<String> listItem;

    EditText groupjob;
    Button jobaddbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openclub);

        groupjob = findViewById(R.id.groupjob);
        jobaddbutton = findViewById(R.id.jobaddbutton);
        listView1 = (ListView) findViewById(R.id.listView1);

        adapter = new ListViewAdapter_openClub(OpenClubActivity.this);
        listView1.setAdapter(adapter);

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
}