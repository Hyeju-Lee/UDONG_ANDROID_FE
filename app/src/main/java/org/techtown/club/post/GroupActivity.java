package org.techtown.club.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.club.dto.Notice;
import org.techtown.club.NoticeListAdapter2;
import org.techtown.club.R;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton2;
    private ListView noticeListView;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        noticeListView = findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        /*noticeList.add(new Notice("33333","김예나","2021-08-12",
                        " \uD83D\uDC99프로젝트 발표회 안내\uD83D\uDC99 \n" +
                        "\n" +
                        "26기 여러분! 여름방학 잘 보내고 계신가요?\n" +
                        "\n" +
                        "어느덧 대략 한 학기 동안 준비하신 프로젝트를 끝을 향해가고 있습니다. 얼마 남지 않은 만큼 조금만 더 힘을 내주세요~\n" +
                        "이번 1학기 프로젝트 발표회는 8월 21일(토) 14:00~17:00에 진행하려고 합니다\uD83C\uDF89\uD83C\uDF89\n")
        );*/

        NoticeListAdapter2 adapter = new NoticeListAdapter2(getBaseContext(),noticeList);
        noticeListView.setAdapter(adapter);

        floatingActionButton2 = findViewById(R.id.floatingActionButton2);

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(GroupActivity.this, GroupWriteActivity.class);
                GroupActivity.this.startActivity(registerIntent);
            }
        });
    }
}