package com.example.sails;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.sails.model.Hire;
import com.example.sails.services.HustlerService;

import io.nucleos.sailsio.Callback;
import io.nucleos.sailsio.Response;

/**
 * Created by cmarcano on 20/11/15.
 */
public class HireDialog extends DialogFragment {

    public static final String TAG = "TAG_" + HireDialog.class.getSimpleName();
    private Hire mHire;
    private HustlerService mHustlerService;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setCancelable(false);
        builder.setTitle("Hire");
        builder.setMessage(mHire.toString());
        builder.setPositiveButton("accept", new OnClickButtonDialog(2));
        builder.setNegativeButton("reject", new OnClickButtonDialog(1));

        return builder.create();
    }

    public void setHire(Hire hire) {
        mHire = hire;
    }

    public void setHustlerService(HustlerService hustlerService) {
        mHustlerService = hustlerService;
    }

    private void responseHire(String idHire, Hire hire) {
        mHustlerService.updateHire(idHire, hire)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response) {
                        Log.d(TAG, "onResponse updateHire: " + response);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure updateHire: " + t.getMessage());
                    }
                });
    }

    private class OnClickButtonDialog implements DialogInterface.OnClickListener {

        private int mStatus;
        public OnClickButtonDialog(int status) {
            mStatus = status;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            final String idHire = mHire.getId();
            final Hire newHire = new Hire();
            newHire.setStatus(mStatus);
            responseHire(idHire, newHire);
            dismiss();
        }
    }
}