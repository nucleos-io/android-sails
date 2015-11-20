package com.example.sails;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.sails.model.Hire;
import com.example.sails.model.Hustler;
import com.example.sails.services.HustlerService;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.nucleos.sailsio.Callback;
import io.nucleos.sailsio.Response;

public class HustlerActivity extends SailsActivity {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Constants
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public final String TAG = "TAG_" + HustlerActivity.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Fields
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private HustlerService mHustlerService;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Methods
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hustler);
        ButterKnife.bind(this);
    }

    @Override
    protected void onConnect() {
        initializeServices();
    }

    @OnCheckedChanged(R.id.sw_on_off)
    public void onChangeHustlerStatus(CompoundButton compoundButton, boolean isChecked) {
        Log.d(TAG, "onChangeHustlerStatus, status: " + isChecked);
        int status = isChecked ? 1 : 0;
        changeStatus(status);
    }

    private void initializeServices() {
        mHustlerService = mSails.create(HustlerService.class);
        mHustlerService.listenHustler()
                .listen(new Callback<Hire>() {
                    @Override
                    public void onResponse(Response<Hire> response) {
                        Log.d(TAG, "onResponse: " + response);
                        if (response.getData() != null) {
                            final Hire hire = response.getData();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showHireDialog(hire);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void changeStatus(final int status) {
        Hustler hustler = new Hustler();
        hustler.setStatus(status);

        mHustlerService.updateHustlerStatus(hustler)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Response<Void> response) {
                            Log.d(TAG, "onResponse changeStatus");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d(TAG, "onFailure changeStatus");
                        }
                    });
    }

    private void showHireDialog(Hire hire) {
        final HireDialog hireDialog = new HireDialog();
        hireDialog.setHire(hire);
        hireDialog.setHustlerService(mHustlerService);
        hireDialog.show(getSupportFragmentManager(), TAG);
    }

}
