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
        noticeList.add(new Notice("솔룩스 발표회 공지","김예나","2021-08-12","8월 21일 토요일에 발표회가 진행됩니다." + "\n" +
                " \uD83D\uDC99프로젝트 발표회 안내\uD83D\uDC99 \n" +
                "\n" +
                "26기 여러분! 여름방학 잘 보내고 계신가요?\n" +
                "\n" +
                "어느덧 대략 한 학기 동안 준비하신 프로젝트를 끝을 향해가고 있습니다. 얼마 남지 않은 만큼 조금만 더 힘을 내주세요~\n" +
                "이번 1학기 프로젝트 발표회는 8월 21일(토) 14:00~17:00에 진행하려고 합니다\uD83C\uDF89\uD83C\uDF89\n" +
                "\n" +
                "이번 프로젝트 발표회는 기획 발표와는 달리 80% (2시간 20분) 이상 불참하실 경우 벌점이 부여됩니다. 따라서 먼저 구글폼으로 참석여부를 받으려고 합니다. 8월 19일(목) 자정까지 제출해주세요."

                )
        );
        NoticeListAdapter_Frag1 adapter = new NoticeListAdapter_Frag1(getContext(),noticeList);
        noticeListView.setAdapter(adapter);

        floatingActionButton = view.findViewById(R.id.floatingActionButton2);
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