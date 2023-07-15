package com.template.kabar.HomeScreenFiles;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.template.kabar.R;

public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bottomNavigationView= findViewById(R.id.homePageBottomNav);
        FrameLayout frameLayout = findViewById(R.id.homePageFrameLayout);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.homePageFrameLayout, new HomeFragment()).commit();


    }

}