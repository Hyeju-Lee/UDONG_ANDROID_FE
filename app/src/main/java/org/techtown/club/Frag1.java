package org.techtown.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Frag1 extends Fragment {
    private View view;
    private FloatingActionButton floatingActionButton;

    private ListView noticeListView;
    private List<Notice> noticeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1,container, false);

        noticeListView = (ListView) view.findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("솔룩스 발표회 공지","김예나","2021-08-12","8월 21일 토요일에 발표회가 진행됩니다"));
        NoticeListAdapter adapter = new NoticeListAdapter(getContext(),noticeList);
        noticeListView.setAdapter(adapter);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), NoticeWriteActivity.class);
                startActivity(intent);
            }

        });

        return view;


    }
}