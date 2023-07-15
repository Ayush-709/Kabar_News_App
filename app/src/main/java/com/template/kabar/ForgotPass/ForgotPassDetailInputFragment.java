package com.template.kabar.ForgotPass;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.template.kabar.R;

import java.util.Objects;

public class ForgotPassDetailInputFragment extends Fragment {
    EditText inputET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_forgot_pass_detail_input, container, false);
        inputET = view.findViewById(R.id.inputMailPhone);

        ForgotPasswordActivity activity = (ForgotPasswordActivity) getActivity();
        Button submitButton = Objects.requireNonNull(activity).getSubmitButton();
        submitButton.setClickable(false);
        submitButton.setBackgroundResource(R.drawable.disabled_button_design);
        inputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    submitButton.setClickable(false);
                    submitButton.setBackgroundResource(R.drawable.disabled_button_design);
                }else {
                    submitButton.setClickable(false);
                    submitButton.setBackgroundResource(R.drawable.disabled_button_design);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;

    }
}