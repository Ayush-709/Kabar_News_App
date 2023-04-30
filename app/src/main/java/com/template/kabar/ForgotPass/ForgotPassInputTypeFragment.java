package com.template.kabar.ForgotPass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.template.kabar.R;

import java.util.Objects;

public class ForgotPassInputTypeFragment extends Fragment {
    RadioButton currentCheckedRadioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgot_pass_input_type, container, false);
        RadioButton mailRadio = view.findViewById(R.id.mailRadio);
        RadioButton smsRadio = view.findViewById(R.id.phoneRadio);
        ForgotPasswordActivity activity = (ForgotPasswordActivity) getActivity();

        Button submitButton = Objects.requireNonNull(activity).getSubmitButton();
        mailRadio.setOnClickListener(v -> {

            if (currentCheckedRadioButton != null) {
                currentCheckedRadioButton.setChecked(false);
            }

            mailRadio.setChecked(true);
            currentCheckedRadioButton = mailRadio;

            submitButton.setBackgroundResource(R.drawable.primary_button_design);
            submitButton.setClickable(true);
        });

        smsRadio.setOnClickListener(v -> {

            if (currentCheckedRadioButton != null) {
                currentCheckedRadioButton.setChecked(false);
            }
            submitButton.setBackgroundResource(R.drawable.primary_button_design);
            submitButton.setClickable(true);

            smsRadio.setChecked(true);
            currentCheckedRadioButton = smsRadio;
        });

        if( !( mailRadio.isChecked() || smsRadio.isChecked())){
            submitButton.setBackgroundResource(R.drawable.disabled_button_design);
            submitButton.setClickable(false);
        }
        return view;
    }
}