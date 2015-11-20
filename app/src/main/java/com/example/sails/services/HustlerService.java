package com.example.sails.services;

import com.example.sails.model.Hire;
import com.example.sails.model.Hustler;

import java.util.Objects;

import io.nucleos.sailsio.Call;
import io.nucleos.sailsio.annotation.Body;
import io.nucleos.sailsio.annotation.ON;
import io.nucleos.sailsio.annotation.PUT;
import io.nucleos.sailsio.annotation.Path;

/**
 * Created by cmarcano on 19/11/15.
 */
public interface HustlerService {

    @PUT("/hustlers/me/status")
    Call<Void> updateHustlerStatus(@Body Hustler hustler);

    @PUT("/hustlers/me/hires/{idHire}")
    Call<Void> updateHire(@Path("idHire") String idHire, @Body Hire hire);

    @ON("hustler")
    Call<Hire> listenHustler();
}
