package org.techtown.club.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.techtown.club.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PostFragment extends Fragment {
    private View view;
    Button groupAddButton;
    ListView listView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post,container, false);

        String[][] item = {{"1조", "김수경 이혜주 김예나"}, {"2조", "김수경 이혜주 김예나"},
                {"3조", "김수경 이혜주 김예나"}};

        listView2 = (ListView)view.findViewById(R.id.listView2);
        ArrayList<ListItemDetail_Frag3> listViewData2 = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            ListItemDetail_Frag3 listData2 = new ListItemDetail_Frag3(item[i][0],
                    item[i][1]);

            listViewData2.add(listData2);
        }

        ListAdapter customAdapter2 = new GroupListAdapter_Frag3(getContext(),listViewData2);
        listView2.setAdapter(customAdapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getActivity(), GroupActivity.class);
                startActivity(intent);
            }
        });


        groupAddButton = view.findViewById(R.id.groupAddButton);
        groupAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), groupAdd.class);
                startActivity(intent);
            }

        });

        return view;
    }
}