package com.template.kabar.ForgotPass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.template.kabar.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Button submitButton;
    ImageView backButton;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        frameLayout = findViewById(R.id.forgotPassFrameLayout);
        submitButton = findViewById(R.id.submitButtonForgotPassScreen);
        backButton = findViewById(R.id.backArrowButton);


        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.forgotPassFrameLayout, new ForgotPassInputTypeFragment(this)).commit();

    }

    public Button getSubmitButton(){
        return submitButton;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}