package org.techtown.club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class welcomegroupActivity extends AppCompatActivity {

    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

    EditText groupjob;
    Button jobaddbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomegroup);

        Button searchgroupbutton = (Button) findViewById(R.id.searchgroupbutton);

        groupjob = findViewById(R.id.groupjob);
        jobaddbutton = findViewById(R.id.jobaddbutton);

        listItem = new ArrayList<String>();
        listItem.add("회장");
        listItem.add("총무");

        searchgroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(welcomegroupActivity.this, welcomegroupActivity2.class);
                welcomegroupActivity.this.startActivity(registerIntent);
            }
        });

        Button makegroupbutton2 = (Button) findViewById(R.id.makegroupbutton2);

        makegroupbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(welcomegroupActivity.this, MainActivity.class);
                welcomegroupActivity.this.startActivity(registerIntent);
            }
        });

        jobaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listItem.add(groupjob.getText().toString());
                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                groupjob.setText("");
            }
        });

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, listItem);
        listView1 = findViewById(R.id.listView1);
        listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView1.setAdapter(adapter);
    }
}