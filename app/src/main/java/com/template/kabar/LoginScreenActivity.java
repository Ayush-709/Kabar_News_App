package com.template.kabar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginScreenActivity extends AppCompatActivity {
    TextView signUpLink, forgotPassLink;
    Button loginButton;
    TextInputEditText usernameInput, passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setTextColor();

        signUpLink = findViewById(R.id.signUpLinkLoginScreen);
        forgotPassLink = findViewById(R.id.forgotPasswordLoginScreen);
        loginButton = findViewById(R.id.loginButtonLoginScreen);
        usernameInput = findViewById(R.id.usernameInputLoginScreen);
        passwordInput = findViewById(R.id.passInputLoginScreen);

        signUpLink.setOnClickListener(v->{
            Intent intent = new Intent(LoginScreenActivity.this, SignupScreenActivity.class);
            startActivity(intent);
        });

    }

    private void setTextColor(){
        TextView welcome =  findViewById(R.id.welcomeText);
        String str ="Hello\nAgain!";
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.bluePrimary));
        spannableString.setSpan(blueSpan, 6, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        welcome.setText(spannableString);

        TextView user = findViewById(R.id.usernameHintLoginScreen);
        TextView pass = findViewById(R.id.passHintLoginScreen);
        SpannableString str1, str2;
        String username = "Username*";
        String password = "Password*";
        str1 = new SpannableString(username);
        str2 = new SpannableString(password);
        ForegroundColorSpan redspan = new ForegroundColorSpan(Color.RED);
        str1.setSpan(redspan,8,9,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str2.setSpan(redspan,8,9,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        user.setText(str1);
        pass.setText(str2);
    }
}