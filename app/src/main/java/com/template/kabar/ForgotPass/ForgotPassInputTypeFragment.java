package com.template.kabar.ForgotPass;

import android.content.Context;
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
    Context context;

    public ForgotPassInputTypeFragment(Context context) {
        this.context = context;
    }

    RadioButton currentCheckedRadioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgot_pass_input_type, container, false);
        RadioButton mailRadio = view.findViewById(R.id.mailRadio);
        RadioButton smsRadio = view.findViewById(R.id.phoneRadio);
        ForgotPasswordActivity activity = (ForgotPasswordActivity) getActivity();
        Button btn = Objects.requireNonNull(activity).getSubmitButton();

        mailRadio.setOnClickListener(v -> {

            if (currentCheckedRadioButton != null) {
                currentCheckedRadioButton.setChecked(false);
            }

            mailRadio.setChecked(true);
            currentCheckedRadioButton = mailRadio;

            btn.setBackgroundResource(R.drawable.primary_button_design);

        });

        smsRadio.setOnClickListener(v -> {

            if (currentCheckedRadioButton != null) {
                currentCheckedRadioButton.setChecked(false);
            }
            btn.setBackgroundResource(R.drawable.primary_button_design);

            smsRadio.setChecked(true);
            currentCheckedRadioButton = smsRadio;
        });

        if( !( mailRadio.isChecked() || smsRadio.isChecked())){
            btn.setBackgroundResource(R.drawable.disabled_button_design);
        }
        return view;
    }
}