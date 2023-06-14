package com.template.kabar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean hasShownWelcomeScreen = sharedPreferences.getBoolean("hasShownWelcomeScreen", false);
        Log.println(Log.DEBUG, "showWelcomeScreenPlease", String.valueOf(hasShownWelcomeScreen));

        Intent i;
        if (hasShownWelcomeScreen) {
            // The welcome screen has already been shown, navigate to the login page
            i = navigateToLoginPage();
        } else {
            // The app is being launched for the first time, navigate to the welcome screen
            i = navigateToWelcomeScreen();

            // Set the flag to indicate that the welcome screen has been shown
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("hasShownWelcomeScreen", true);
            editor.apply();
        }

        new Handler().postDelayed(() -> {
            startActivity(i);
            finish();
        }, 1300);

    }

    private Intent navigateToLoginPage() {
        return new Intent(MainActivity.this, LoginScreenActivity.class);
    }

    private Intent navigateToWelcomeScreen() {
        return new Intent(MainActivity.this, EntryInfoActivity.class);
    }

}