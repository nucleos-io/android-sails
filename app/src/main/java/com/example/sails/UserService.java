package com.example.sails;

import com.example.sails.response.NoContent;

import io.nucleos.sailsio.Call;
import io.nucleos.sailsio.annotation.Body;
import io.nucleos.sailsio.annotation.PUT;

/**
 * Created by cmarcano on 18/11/15.
 */
public interface UserService {

    @PUT("/hustlers/me/status")
    Call<NoContent> updateStatus(@Body UserStatus status);

}

class UserStatus {
    int status;
}
