package com.pcy.memo.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pcy.memo.R;
import com.pcy.memo.View.Checklist_frag;
import com.pcy.memo.View.Memo_frag;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Memo_frag memo_frag;
    private Checklist_frag checklist_frag;
    private Search_frag search_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_note:
                        setFrag(0);
                        break;
                    case R.id.menu_check_list:
                        setFrag(1);
                        break;
                    case R.id.menu_search:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        memo_frag = new Memo_frag();
        checklist_frag = new Checklist_frag();
        search_frag = new Search_frag();
        setFrag(0); //  첫 프래그먼트 화면 지정
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, memo_frag);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, checklist_frag);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, search_frag);
                ft.commit();
                break;
        }
    }
}