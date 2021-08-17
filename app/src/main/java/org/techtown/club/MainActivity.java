package org.techtown.club;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.club.notice.NoticeFragment;
import org.techtown.club.post.PostFragment;
import org.techtown.club.receipt.ReceiptFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private NoticeFragment frag1;
    private ReceiptFragment frag2;
    private PostFragment frag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_notice:
                        setFrag(0);
                        break;
                    case R.id.action_money:
                        setFrag(1);
                        break;
                    case R.id.action_group:
                        setFrag(2);
                        break;

                }
                return true;
            }
        });
        frag1 = new NoticeFragment();
        frag2 = new ReceiptFragment();
        frag3 = new PostFragment();
        setFrag(0);

    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;

        }
    }
}