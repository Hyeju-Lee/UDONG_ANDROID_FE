package org.techtown.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class groupAdd extends AppCompatActivity {
    ListView peopleNameListView;
    Button nameButton;
    EditText nameAddView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);

        String[] emoticonList = {"\uD83D\uDE00","\uD83D\uDE3A", "\uD83D\uDE0D"};

        Spinner emoticonSpinner = (Spinner)findViewById(R.id.emoticonSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                emoticonList);
        emoticonSpinner.setAdapter(adapter);
        emoticonSpinner.setSelection(0);


        nameAddView = findViewById(R.id.nameAddView);
        nameButton = findViewById(R.id.nameButton);
        ListViewDetailAdapter_GroupAdd adapter3 = new ListViewDetailAdapter_GroupAdd(groupAdd.this);
        peopleNameListView = (ListView) findViewById(R.id.peopleNameListView);
        peopleNameListView.setAdapter(adapter3);


        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter3.addItem(nameAddView.getText().toString());
                adapter3.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                nameButton.setText("");
            }
        });


        Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(groupAdd.this, MainActivity.class);
                groupAdd.this.startActivity(registerIntent);
            }
        });


    }
}