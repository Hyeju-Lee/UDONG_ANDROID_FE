package org.techtown.club.receipt;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.techtown.club.MainActivity;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.dto.Receipt;
import org.techtown.club.retrofit.RetrofitClient;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoneyAdd extends AppCompatActivity {

    ListView listView1;
    ListView listView2;
    ListViewDetailAdapter_MoneyAdd1 adapter;
    ListViewDetailAdapter_MoneyAdd2 adapter2;

    EditText what;
    EditText name;
    EditText money;
    Button addButton;

    EditText what2;
    EditText name2;
    EditText money2;
    Button addButton2;

    /*Button addButton3;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;*/

    Button TotalMoneyButton;
    EditText dateEditText;

    String useDate;

    final int PICTURE_REQUEST_CODE = 100;
    ArrayList<Receipt> receipts;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_add);
        mContext = this;

        dateEditText = findViewById(R.id.editTextDate);
        receipts = new ArrayList<>();

        what = findViewById(R.id.what);
        name = findViewById(R.id.name);
        money = findViewById(R.id.money);
        addButton = findViewById(R.id.addButton); //수입 + 버튼
        listView1 = (ListView) findViewById(R.id.listView1);

        what2 = findViewById(R.id.what2);
        name2 = findViewById(R.id.name2);
        money2 = findViewById(R.id.money2);
        addButton2 = findViewById(R.id.addButton2);
        listView2 = (ListView) findViewById(R.id.listView2);


        adapter = new ListViewDetailAdapter_MoneyAdd1(MoneyAdd.this);
        adapter2 = new ListViewDetailAdapter_MoneyAdd2(MoneyAdd.this);
        listView1.setAdapter(adapter);
        listView2.setAdapter(adapter2);

        TotalMoneyButton = findViewById(R.id.TotalMoneyButton);

        TotalMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < ListViewDetailAdapter_MoneyAdd1.listItems.size(); i++) {
                    ListItemDetail listItemDetail = ListViewDetailAdapter_MoneyAdd1.listItems.get(i);
                    String cost = listItemDetail.getMoney();
                    String title = listItemDetail.getWhat();
                    String content = listItemDetail.getName();
                    useDate = dateEditText.getText().toString();
                    Log.d("확인", cost + title + content + useDate);
                    Receipt receipt = new Receipt("+" + cost, title, content, useDate);
                    receipts.add(receipt);
                    Log.d("수입확인", content);
                }
                for (int i = 0; i < ListViewDetailAdapter_MoneyAdd2.listItems2.size(); i++) {
                    ListItemDetail listItemDetail = ListViewDetailAdapter_MoneyAdd2.listItems2.get(i);
                    String cost = listItemDetail.getMoney();
                    String title = listItemDetail.getWhat();
                    String content = listItemDetail.getName();
                    useDate = dateEditText.getText().toString();
                    Log.d("확인", cost + title + content + useDate);
                    Receipt receipt = new Receipt("-" + cost, title, content, useDate);
                    receipts.add(receipt);
                    Log.d("지출확인", content);
                }
                for (int i = 0; i < receipts.size(); i++) {
                    Receipt receipt = receipts.get(i);
                    Log.d("receipts확인", receipts.get(i).getContent());
                }
                postReceipt();
            }
        });

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

        /*addButton3.setOnClickListener(new View.OnClickListener() {
            private int RequestCode;

            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);
            }
        });*/
    }

    public void postReceipt() {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Long userId = PreferenceManager.getLong(mContext, "userId");
        Call<Void> call = RetrofitClient.getApiService().postReceipt(clubId, userId, receipts);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 receipt", "error code" + response.code());
                    return;
                }
                Log.d("연결 성공 receipt", "d");
                adapter.clear();
                adapter2.clear();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("연결 실패 receipt", t.getMessage());
            }
        });
    }
}



    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode == PICTURE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                //기존 이미지 지우기
                imageView1.setImageResource(0);
                imageView2.setImageResource(0);
                imageView3.setImageResource(0);
                imageView4.setImageResource(0);
                imageView5.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if(clipData!=null)
                {

                    for(int i = 0; i < 5; i++)
                    {
                        if(i<clipData.getItemCount()){
                            Uri urione =  clipData.getItemAt(i).getUri();
                            switch (i){
                                case 0:
                                    imageView1.setImageURI(urione);
                                    break;
                                case 1:
                                    imageView2.setImageURI(urione);
                                    break;
                                case 2:
                                    imageView3.setImageURI(urione);
                                    break;
                                case 3:
                                    imageView4.setImageURI(urione);
                                    break;
                                case 4:
                                    imageView5.setImageURI(urione);
                                    break;
                            }
                        }
                    }
                }
                else if(uri != null)
                {
                    imageView1.setImageURI(uri);
                }
            }
        }

    }
}*/