package com.example.sails.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cmarcano on 18/11/15.
 */
public class LocalStorage {

    private static final String PREFERENCES_NAME = "local_storage";
    private static final String ACCESS_TOKEN = "access_token";

    public static void putAccessToken(String accessToken, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.edit()
        .putString(ACCESS_TOKEN, accessToken)
        .apply();

    }

    public static String getAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(ACCESS_TOKEN, null);
    }

}
