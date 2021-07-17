package org.techtown.club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    }
}