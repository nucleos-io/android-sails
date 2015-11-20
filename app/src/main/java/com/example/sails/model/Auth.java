package com.example.sails.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cmarcano on 19/11/15.
 */
public class Auth {

    public static class Body {
        @SerializedName("client_id")
        String clientId;
        @SerializedName("client_secret")
        String clientSecret;
        String phone;

        public Body(String phone) {
            this.clientId = "123456";
            this.clientSecret = "123456";
            this.phone = phone;
        }
    }

    public static class Response {
        String accessToken;
        User user;

        public String getAccessToken() {
            return accessToken;
        }

        public User getUser() {
            return user;
        }

        @Override
        public String toString() {
            return "LoginResponse{" +
                    "accessToken='" + accessToken + '\'' +
                    ", user=" + user +
                    '}';
        }
    }

}
