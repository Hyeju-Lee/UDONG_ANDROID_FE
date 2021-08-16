package org.techtown.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Button makegroupbutton = (Button) findViewById(R.id.makegroupbutton);

        makegroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity2.this, RegisterActivity1.class);
                RegisterActivity2.this.startActivity(registerIntent);
            }
        });

        final String[] job = {"총무","임원진"};

        Spinner groupSpinner = (Spinner)findViewById(R.id.spinner_group);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, job);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);


    }
}