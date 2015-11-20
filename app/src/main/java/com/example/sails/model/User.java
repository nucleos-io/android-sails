package com.example.sails.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cmarcano on 19/11/15.
 */
public class User implements Parcelable {

    String name;
    String email;
    String id;
    Hustler hustler;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Hustler getHustler() {
        return hustler;
    }

    public void setHustler(Hustler hustler) {
        this.hustler = hustler;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", hustler=" + hustler +
                '}';
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        id = in.readString();
    }
}
