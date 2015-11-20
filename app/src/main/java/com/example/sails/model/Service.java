package com.example.sails.model;

/**
 * Created by cmarcano on 20/11/15.
 */
public class Service {

    private String id;
    private Skill skill;
    private float hourly;
    private float flatFee;
    private boolean favorite;

    public String getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public float getHourly() {
        return hourly;
    }

    public float getFlatFee() {
        return flatFee;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
