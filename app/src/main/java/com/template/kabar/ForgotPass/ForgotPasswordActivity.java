package com.template.kabar.ForgotPass;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.template.kabar.GetOtpActivity;
import com.template.kabar.R;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Button submitButton;
    ImageView backButton;
    FragmentManager manager;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        frameLayout = findViewById(R.id.forgotPassFrameLayout);
        submitButton = findViewById(R.id.submitButtonForgotPassScreen);
        backButton = findViewById(R.id.backArrowButton);
        type = 1;

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.forgotPassFrameLayout, new ForgotPassInputTypeFragment(), "inputType").commit();

        submitButton.setOnClickListener(v->{
            Fragment frag = getSupportFragmentManager().findFragmentByTag("inputType");
            if(frag!=null && frag.isVisible()){
                manager.beginTransaction().replace(R.id.forgotPassFrameLayout,new ForgotPassDetailInputFragment(),"details").commit();
            } else if (manager.findFragmentByTag("details")!=null
                    && Objects.requireNonNull(manager.findFragmentByTag("details")).isVisible()) {
                Intent intent = new Intent(ForgotPasswordActivity.this, GetOtpActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public Button getSubmitButton(){
        return submitButton;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}