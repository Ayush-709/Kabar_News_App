package com.template.kabar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.template.kabar.ForgotPass.ForgotPasswordActivity;
import com.template.kabar.HomeScreenFiles.HomePageActivity;
import com.template.kabar.SupportFiles.ReusableCodeForAll;

import java.util.Objects;

public class LoginScreenActivity extends AppCompatActivity {
    TextView signUpLink, forgotPassLink;
    Button loginButton;
    TextInputLayout emailInput, passwordInput;
    FirebaseAuth auth;
    String email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(LoginScreenActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish(); // Finish the LoginScreenActivity to prevent going back
            return; // Return to prevent executing the remaining code
        }

        setContentView(R.layout.activity_login_screen);
        setTextColor();

        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(v -> {
            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        signUpLink = findViewById(R.id.signUpLinkLoginScreen);
        forgotPassLink = findViewById(R.id.forgotPasswordLoginScreen);
        loginButton = findViewById(R.id.loginButtonLoginScreen);
        emailInput = findViewById(R.id.emailInputLoginScreen);
        passwordInput = findViewById(R.id.passInputLoginScreen);


        ProgressBar progressBar = findViewById(R.id.progressBarLoginScreen);
        progressBar.bringToFront();

        auth = FirebaseAuth.getInstance();
        signUpLink.setOnClickListener(v->{
            Intent intent = new Intent(LoginScreenActivity.this, SignupScreenActivity.class);
            startActivity(intent);
        });

        forgotPassLink.setOnClickListener(v->{
            Intent intent = new Intent(LoginScreenActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v->{
            email = Objects.requireNonNull(emailInput.getEditText()).getText().toString();
            pass = Objects.requireNonNull(passwordInput.getEditText()).getText().toString();

            if(checkForErrors()) {
                email = Objects.requireNonNull(emailInput.getEditText()).getText().toString().trim();
                pass = Objects.requireNonNull(passwordInput.getEditText()).getText().toString().trim();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(loginButton.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);


                if (checkForErrors()) {
                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener((Runnable) -> {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Intent intent = new Intent(LoginScreenActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }).addOnFailureListener((Runnable) -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        ReusableCodeForAll.ShowAlert(LoginScreenActivity.this, "ERROR", Runnable.getMessage());
                    });
                }
            }

        });
    }

    private void setTextColor(){
        TextView welcome =  findViewById(R.id.welcomeText);
        String str ="Hello\nAgain!";
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.bluePrimary));
        spannableString.setSpan(blueSpan, 6, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        welcome.setText(spannableString);

        TextView user = findViewById(R.id.emailHintLoginScreen);
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

    private boolean checkForErrors() {
        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean errorInput = false;
        boolean errorPass = false;
        passwordInput.setError("");
        passwordInput.setErrorEnabled(false);
        emailInput.setError("");
        emailInput.setErrorEnabled(false);

        if(TextUtils.isEmpty(email)){
            emailInput.setErrorEnabled(true);
            emailInput.setError("Not Empty Accepted");
        }else if(!email.matches(pattern)){
            emailInput.setErrorEnabled(true);
            emailInput.setError("Invalid");
        }else{
            errorInput = true;
        }


        if(TextUtils.isEmpty(pass)){
            passwordInput.setErrorEnabled(true);
            passwordInput.setError("Not Empty Accepted");
        }else if(pass.length()<8){
            passwordInput.setErrorEnabled(true);
            passwordInput.setError("Tooo Short");
        }else{
            errorPass = true;
        }

        return errorPass && errorInput;
    }
}