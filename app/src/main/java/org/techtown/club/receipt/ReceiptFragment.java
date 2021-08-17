package org.techtown.club.receipt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.techtown.club.ListItemDetail_Frag2;
import org.techtown.club.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ReceiptFragment extends Fragment{
    private View view;
    Button button1;
    Button button_add;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt,container, false);

        String[][] item = {{"+3000", "-5000", "2021-05-15"}, {"+5000", "-1000", "2021-04-15"},
                {"+9000", "-8000", "2021-06-15"}, {"+3000", "-7000", "2021-08-15"}};

        listView = (ListView)view.findViewById(R.id.listView);
        ArrayList<ListItemDetail_Frag2> listViewData = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            ListItemDetail_Frag2 listData = new ListItemDetail_Frag2(item[i][0],
                    item[i][1], item[i][2]);

            listViewData.add(listData);
        }

        ListAdapter customAdapter = new MoneyListAdapter_Frag2(getContext(),listViewData);
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
                //Intent intent = new Intent(getActivity(), MoneyActivity2.class);
                Intent intent= new Intent(getActivity(), DetailMoneyActivity.class);
                startActivity(intent);
            }

        });

        button_add = view.findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MoneyAdd.class);
                startActivity(intent);
            }

        });


        return view;
    }
}