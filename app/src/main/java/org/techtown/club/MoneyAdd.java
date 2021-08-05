package org.techtown.club;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class MoneyAdd extends AppCompatActivity {

    ListView listView1;
    ListView listView2;
    ListViewDetailAdapter adapter;
    ListViewDetailAdapter2 adapter2;

    EditText what;
    EditText name;
    EditText money;
    Button addButton;

    EditText what2;
    EditText name2;
    EditText money2;
    Button addButton2;

    Button addButton3;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_add);

        what = findViewById(R.id.what);
        name = findViewById(R.id.name);
        money = findViewById(R.id.money);
        addButton = findViewById(R.id.addButton);
        listView1 = (ListView) findViewById(R.id.listView1);

        what2 = findViewById(R.id.what2);
        name2 = findViewById(R.id.name2);
        money2 = findViewById(R.id.money2);
        addButton2 = findViewById(R.id.addButton2);
        listView2 = (ListView) findViewById(R.id.listView2);

        addButton3 = findViewById(R.id.addButton3);
        imageView = (ImageView)findViewById(R.id.image);

        adapter = new ListViewDetailAdapter(MoneyAdd.this);
        adapter2 = new ListViewDetailAdapter2(MoneyAdd.this);
        listView1.setAdapter(adapter);
        listView2.setAdapter(adapter2);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.addItem(what.getText().toString(), name.getText().toString(), money.getText().toString());
                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                what.setText("");
                name.setText("");
                money.setText("");
            }
        });

        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter2.addItem(what2.getText().toString(), name2.getText().toString(), money2.getText().toString());
                adapter2.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                what2.setText("");
                name2.setText("");
                money2.setText("");
            }
        });

        addButton3.setOnClickListener(new View.OnClickListener() {
            private int RequestCode;

            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    }
}