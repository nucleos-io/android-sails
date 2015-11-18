package com.example.sails;

import android.content.Context;

import io.nucleos.sailsio.request.Interceptor;
import io.nucleos.sailsio.request.Param;
import io.nucleos.sailsio.request.Request;

/**
 * Created by cmarcano on 18/11/15.
 */
public class MyInterceptor implements Interceptor {

    private Context context;

    public  MyInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Request onRequest(Request.Builder builder) {
        final String accessToken = LocalStorage.getAccessToken(this.context);
        builder.addBodyParam(new Param("access_token", accessToken));
        return builder.build();
    }
}
