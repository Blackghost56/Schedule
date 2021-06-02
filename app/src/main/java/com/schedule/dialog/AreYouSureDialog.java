package com.schedule.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.schedule.R;

import java.util.Objects;

public class AreYouSureDialog extends DialogFragment {

    public interface OnClickListener{
        void onYesClicked();
    }


    private final OnClickListener onClickListener;
    private final String message;

    public AreYouSureDialog(String msg, OnClickListener clickListener){
        message = msg;
        onClickListener = clickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.Theme_Schedule_Dialog);
        builder.setTitle("warning");
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("yes", (dialog, which) -> onClickListener.onYesClicked());
        builder.setNegativeButton("no", (dialog, which) -> dismiss());
        return builder.create();
    }

}