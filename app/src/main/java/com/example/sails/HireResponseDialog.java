package com.example.sails;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.sails.model.Hire;

/**
 * Created by cmarcano on 20/11/15.
 */
public class HireResponseDialog extends DialogFragment {

    private Hire mHire;

    public void setHire(Hire hire) {
        mHire = hire;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Hire response");
        String message = mHire.getStatus() == 1 ? "REJECTED" : "ACCEPTED";
        builder.setMessage(message);
        return builder.create();
    }
}
