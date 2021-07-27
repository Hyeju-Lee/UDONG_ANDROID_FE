package org.techtown.club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class welcomegroupActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomegroup2);

        Button makegroupbutton = (Button) findViewById(R.id.makegroupbutton);

        makegroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(welcomegroupActivity2.this, welcomegroupActivity.class);
                welcomegroupActivity2.this.startActivity(registerIntent);
            }
        });

        final String[] job = {"총무","임원진"};

        Spinner groupSpinner = (Spinner)findViewById(R.id.spinner_group);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, job);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);


    }
}