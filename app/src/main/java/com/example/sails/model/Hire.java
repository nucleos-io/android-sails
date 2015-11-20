package com.example.sails.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cmarcano on 19/11/15.
 */
public class Hire {

    private String id;
    private int status;

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private Service service;
    @SerializedName("hustler")
    private String idHustler;
    private int timeTotal;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getIdHustler() {
        return idHustler;
    }

    public void setIdHustler(String idHustler) {
        this.idHustler = idHustler;
    }

    public int getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(int timeTotal) {
        this.timeTotal = timeTotal;
    }

    @Override
    public String toString() {
        return "Hire{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", service=" + service +
                ", idHustler='" + idHustler + '\'' +
                ", timeTotal=" + timeTotal +
                '}';
    }
}
