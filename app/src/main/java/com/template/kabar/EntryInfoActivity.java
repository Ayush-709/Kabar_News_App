package com.template.kabar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class EntryInfoActivity extends AppCompatActivity {
    Button backButton, nextButton;
    ImageView flow1, flow2, flow3;
    FrameLayout frameLayout;
    FragmentManager manager;
    int acitve = 1;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        backButton = findViewById(R.id.infoWindowBackButton);
        nextButton = findViewById(R.id.infoWindowNextButton);
        flow1 = findViewById(R.id.fragmentFlow1);
        flow2 = findViewById(R.id.fragmentFlow2);
        flow3 = findViewById(R.id.fragmentFlow3);
        frameLayout = findViewById(R.id.infoWindowFrame);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.infoWindowFrame, new FirstInfoFragment()).commit();
        nextButton.setOnClickListener(v->{
            if(acitve==1){
                replaceFragment(new SecondInfoFragment());
                flow1.setImageDrawable(getResources().getDrawable(R.drawable.fragment_flow_dots, getTheme()));
                flow2.setImageDrawable(getResources().getDrawable(R.drawable.active_fragment_flow, getTheme()));
                acitve++;
            } else if (acitve==2) {
                replaceFragment(new ThirdInfoFragment());
                flow2.setImageDrawable(getResources().getDrawable(R.drawable.fragment_flow_dots, getTheme()));
                flow3.setImageDrawable(getResources().getDrawable(R.drawable.active_fragment_flow, getTheme()));
                acitve++;
                nextButton.setText("Get Started");
            } else if (acitve==3) {
                startActivity(new Intent(EntryInfoActivity.this, LoginScreenActivity.class));
                finish();
            }
        });
        backButton.setOnClickListener(v->{
            if(acitve==3){
                replaceFragment(new SecondInfoFragment());
                flow3.setImageDrawable(getResources().getDrawable(R.drawable.fragment_flow_dots, getTheme()));
                flow2.setImageDrawable(getResources().getDrawable(R.drawable.active_fragment_flow, getTheme()));
                acitve--;
                nextButton.setText("Next");
            } else if (acitve==2) {
                replaceFragment(new FirstInfoFragment());
                flow2.setImageDrawable(getResources().getDrawable(R.drawable.fragment_flow_dots, getTheme()));
                flow1.setImageDrawable(getResources().getDrawable(R.drawable.active_fragment_flow, getTheme()));
                acitve--;
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.infoWindowFrame, fragment);
        transaction.commit();
    }


}