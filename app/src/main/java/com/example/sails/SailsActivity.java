package com.example.sails;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sails.model.User;
import com.example.sails.storage.LocalStorage;

import io.nucleos.sailsio.Options;
import io.nucleos.sailsio.SailsIO;
import io.nucleos.sailsio.event.RawEvent;
import io.nucleos.sailsio.request.Interceptor;
import io.nucleos.sailsio.request.Param;
import io.nucleos.sailsio.request.Request;

/**
 * Created by cmarcano on 19/11/15.
 */
public abstract class SailsActivity extends AppCompatActivity {

    private static final String TAG = "TAG_" + SailsActivity.class.getSimpleName();
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Fields
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    protected SailsIO mSails;
    protected User mCurrentUser;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Methods
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate . . . . .");

        mCurrentUser = getIntent().getExtras().getParcelable(MainActivity.KEY_USER);

        Options options = new Options();
        options.query = Constant.QUERY_SAILS;

        mSails = new SailsIO.Builder()
                .autoConnect(true)
                .baseUrl(Constant.BASE_URL)
                .interceptor(new MyInterceptor(this))
                .options(options)
                .build();

        mSails.onConnect(new RawEvent.OnConnect() {
            @Override
            public void onEvent(Object... args) {
                Log.d(TAG, "Socket onConnect . . . . . . . . . . . .");
                onConnect();
            }
        });

        mSails.onReconnect(new RawEvent.OnReconnect() {
            @Override
            public void onEvent(Object... args) {
                Log.d(TAG, "Socket onReconnect . . . . . . . . . . . .");
            }
        });

        mSails.onDisconnect(new RawEvent.OnDisconnect() {
            @Override
            public void onEvent(Object... args) {
                Log.d(TAG, "Socket onDisconnect . . . . . . . . . . . .");
            }
        });

        mSails.onReconnectAttempt(new RawEvent.OnReconnectAttempt() {
            @Override
            public void onEvent(Object... args) {
                Log.d(TAG, "Socket onReconnectAttempt . . . . . . . . . . . .");
            }
        });

        mSails.onReconnectFailed(new RawEvent.OnReconnectFailed() {
            @Override
            public void onEvent(Object... args) {
                Log.d(TAG, "Socket onReconnectFailed . . . . . . . . . . . .");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mSails != null) {
            mSails.disconnect();
        }
    }

    protected abstract void onConnect();

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ----------------- Class
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    private class MyInterceptor implements Interceptor {

        private Context mContext;

        public  MyInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Request onRequest(Request.Builder builder) {
            final String accessToken = LocalStorage.getAccessToken(mContext);
            builder.addBodyParam(new Param("access_token", accessToken));
            return builder.build();
        }
    }
}
