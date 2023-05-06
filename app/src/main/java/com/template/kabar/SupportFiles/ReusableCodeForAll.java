package com.template.kabar.SupportFiles;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class ReusableCodeForAll {
    public static void ShowAlert(Context context, String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).setTitle(title).setMessage(message).show();
    }
}
