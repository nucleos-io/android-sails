package com.example.sails.services;

import com.example.sails.model.Hire;
import com.example.sails.model.Hustler;

import java.util.List;

import io.nucleos.sailsio.Call;
import io.nucleos.sailsio.annotation.Body;
import io.nucleos.sailsio.annotation.GET;
import io.nucleos.sailsio.annotation.ON;
import io.nucleos.sailsio.annotation.POST;

/**
 * Created by cmarcano on 18/11/15.
 */
public interface UserService {

    @POST("/hire")
    Call<Void> hire(@Body Hire hire);

    @GET("/hustlers")
    Call<List<Hustler>> listHustlers();

    @ON("hire")
    Call<Hire> listenHire();
}
