package com.template.kabar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.template.kabar.FetchUserDetail.GetUserDetailActivity;
import com.template.kabar.HomeScreenFiles.HomePageActivity;
import com.template.kabar.SupportFiles.ReusableCodeForAll;

import java.util.HashMap;
import java.util.Objects;

public class SignupScreenActivity extends AppCompatActivity {
    TextInputLayout emailInput, passwordInput;
    Button signupButton;
    TextView loginLink;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String email, pass;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        View rootView = findViewById(android.R.id.content);

// Set up the click listener for the root layout
        rootView.setOnClickListener(v -> {
            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        loginLink = findViewById(R.id.loginLinkSignupScreen);
        bar = findViewById(R.id.progressBarSignupScreen);
        bar.bringToFront();

        loginLink.setOnClickListener(v->{
            Intent intent = new Intent(SignupScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInputSignupScreen);
        passwordInput = findViewById(R.id.passInputSignupScreen);
        signupButton = findViewById(R.id.signupButtonSignupScreen);

        signupButton.setOnClickListener(v->{
            //hide keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(signupButton.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            email = Objects.requireNonNull(emailInput.getEditText()).getText().toString();
            pass= Objects.requireNonNull(passwordInput.getEditText()).getText().toString();

            if(checkForErrors()){
                bar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        String userid =Objects.requireNonNull(auth.getCurrentUser()).getUid();
                        DatabaseReference reference = database.getReference("Users").child(userid);
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("email", email);
                        map.put("password", pass);
                        reference.setValue(map).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreenActivity.this);
                                builder.setMessage("You have successfully Registered!");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Ok", (dialog, which) -> {
                                    dialog.dismiss();
                                    bar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    if(checkUserDetailExists()){
                                        Intent intent = new Intent(SignupScreenActivity.this, HomePageActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }else{
                                        Intent intent = new Intent(SignupScreenActivity.this, GetUserDetailActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }else{
                                bar.setVisibility(View.INVISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                ReusableCodeForAll.ShowAlert(SignupScreenActivity.this, "Error", Objects.requireNonNull(task1.getException()).getMessage());
                            }
                        });
                    }else{
                        bar.setVisibility(View.INVISIBLE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(SignupScreenActivity.this, "Error "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean checkUserDetailExists() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String uuid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        final boolean[] result = {false};
        DatabaseReference ref = db.getReference("Users").child(uuid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    result[0] = snapshot.child("userName").exists();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return result[0];
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