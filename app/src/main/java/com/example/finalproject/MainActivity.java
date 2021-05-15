package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navi;
    HomeFragment homeFragment;
    PointFragment pointFragment;
    CameraFragment cameraFragment;
    RankFragment rankFragment;
    MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navi=findViewById(R.id.btm_nav);
        homeFragment = new HomeFragment();
        pointFragment = new PointFragment();
        cameraFragment = new CameraFragment();
        rankFragment = new RankFragment();
        myPageFragment = new MyPageFragment();

        executeFragment(homeFragment);

        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_home){
                    executeFragment(homeFragment);
                }else if(item.getItemId() == R.id.item_point){
                    executeFragment(pointFragment);
                }else if(item.getItemId() == R.id.item_camera){
                    executeFragment(cameraFragment);
                }else if(item.getItemId() == R.id.item_rank){
                    executeFragment(rankFragment);
                }else if(item.getItemId() == R.id.item_mypage){
                    executeFragment(myPageFragment);
                }

                //true >> 클릭한 메뉴아이템 포커싱.. 선택이 되어진걸로 보이게
                return true;
            }
        });




    }
    private void executeFragment(Fragment fragment) {
        //replace(FrameLayout id명, Fragment객체): FrameLayout에 Fragment화면을 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
    }
}
