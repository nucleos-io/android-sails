package com.example.sails.services;

import com.example.sails.model.Auth;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by cmarcano on 18/11/15.
 */
public interface AuthService {

    @POST("/auth/sms")
    Call<Void> requestCode(@Body Auth.Body body);

    @POST("/auth/sms/{code}")
    Call<Auth.Response> sendCode(@Path("code") String code, @Body Auth.Body body);
}