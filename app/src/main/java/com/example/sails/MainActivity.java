package com.example.sails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sails.model.Auth;
import com.example.sails.services.AuthService;
import com.example.sails.model.User;
import com.example.sails.storage.LocalStorage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Constants
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public static final String TAG = "TAG_" + MainActivity.class.getSimpleName();
    public static final String KEY_USER = "user";

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Fields
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private AuthService mLoginService;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- UI references
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    @Bind(R.id.edit_code)
    EditText mCodeEditText;

    @Bind(R.id.edit_phone)
    EditText mPhoneEditText;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Methods
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeServices();
    }

    @OnClick({R.id.btn_send_code, R.id.btn_request_code})
    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.btn_request_code:
                onClickRequestCode();
                break;
            case R.id.btn_send_code:
                onClickSendCode();
                break;
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Private methods
    // ---------------------------------------------------------------------------------------------

    private void initializeServices() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mLoginService = retrofit.create(AuthService.class);
    }

    private void onClickRequestCode() {
        Toast.makeText(MainActivity.this, TAG + "onClickRequestCode", Toast.LENGTH_SHORT).show();
        final String phone = mPhoneEditText.getText().toString();

        mLoginService.requestCode(new Auth.Body(phone))
            .enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Toast.makeText(MainActivity.this, "onResponse request code. SUCCESS. StatusCode: " + response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "onResponse request code. ERROR. StatusCode: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(MainActivity.this, "onFailure request code", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.getMessage());
                    t.printStackTrace();
                }
            });
    }

    private void onClickSendCode() {
        Toast.makeText(MainActivity.this, "onClickSendCode", Toast.LENGTH_SHORT).show();
        final String phone = mPhoneEditText.getText().toString();
        final String code = mCodeEditText.getText().toString();

        mLoginService.sendCode(code, new Auth.Body(phone))
                    .enqueue(new Callback<Auth.Response>() {
                        @Override
                        public void onResponse(Response<Auth.Response> response, Retrofit retrofit) {
                            if (response.isSuccess()){
                                Toast.makeText(MainActivity.this, "onResponse send code. SUCCESS. StatusCode: " + response.code(), Toast.LENGTH_SHORT).show();

                                Auth.Response authResponse = response.body();
                                Log.d(TAG, "authResponse: " + authResponse.toString());
                                User user = authResponse.getUser();

                                // save accessToken
                                LocalStorage.putAccessToken(authResponse.getAccessToken(), MainActivity.this);

                                // Launch activity
                                Intent intent;
                                if (user.getHustler() == null) {
                                    intent = new Intent(MainActivity.this, UserActivity.class);
                                } else {
                                    intent = new Intent(MainActivity.this, HustlerActivity.class);
                                }

                                intent.putExtra(KEY_USER, user);
                                startActivity(intent);

                            } else {
                                Toast.makeText(MainActivity.this, "onResponse send code. ERROR. StatusCode: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(MainActivity.this, "onFailure send code", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                            Log.e(TAG, t.getMessage());
                        }
                    });
    }
}
