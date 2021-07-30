package org.techtown.club;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag2 extends Fragment{
    private View view;
    Button button1;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2,container, false);

        String[][] item = {{"+3000", "-5000", "2021-05-15"}, {"+5000", "-1000", "2021-04-15"},
                {"+9000", "-8000", "2021-06-15"}, {"+3000", "-7000", "2021-08-15"}};

        listView = (ListView)view.findViewById(R.id.listView);
        ArrayList<ReceiptListData> listViewData = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            ReceiptListData listData = new ReceiptListData(item[i][0],
                    item[i][1], item[i][2]);

            listViewData.add(listData);
        }

        ListAdapter customAdapter = new CustomListAdapter_Receipt(getContext(),listViewData);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickname = listViewData.get(i).plusText;
                Log.d("확인","플러스 텍스트는 "+ clickname);
            }
        });

        button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoneyActivity2.class);
                startActivity(intent);
            }

        });


        return view;
    }
}