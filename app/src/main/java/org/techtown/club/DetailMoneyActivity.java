package org.techtown.club;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;



import java.util.ArrayList;


public class DetailMoneyActivity extends AppCompatActivity {

    private Context mContext1;
    private ListView mListView1;
    private Adapter adapter1;

    String[][] item = new String[][]{
            {"지각","S", "+2000"}, {"지각", "A", "+2000"}, {"지각", "C", "+2000"},
         {"미제출", "A", "+2000"}, {"미흡제출", "D", "+3000"}, {"미흡제출", "Z", "+3000"}, {"지출", "10조 강의 구매", "-30000"}
    };

    private ArrayList<String>tmp1;
    private ArrayList<String>tmp2;
    private ArrayList<String>tmp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ... 코드 계속
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_money);
        this.mContext1 = getApplicationContext();
        this.mContext1 = getApplicationContext();
        mListView1 = (ListView) findViewById(R.id.listView1);


        tmp1 = new ArrayList<>();
        tmp2 = new ArrayList<>();
        tmp3 = new ArrayList<>();
        for (int i=0; i<item.length; i++) {
            tmp1.add(item[i][0]);
            tmp2.add(item[i][1]);
            tmp3.add(item[i][2]);
        }


        adapter1 = new Adapter(mContext1, tmp1, tmp2, tmp3);
        mListView1.setAdapter(adapter1);
//        adapter2 = new Adapter(mContext2, tmp2);
//        mListView1.setAdapter(adapter2);
//        adapter3 = new Adapter(mContext3, tmp3);
//        mListView1.setAdapter(adapter3);

    }

}