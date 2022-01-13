package com.nine.childcare.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String uid, name, email, address;
    private double longitude, latitude;
    private int quesId;

    public User(String uid, String name, String email, String address, double longitude, double latitude, int quesId) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.quesId = quesId;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getQuesId() {
        return quesId;
    }
}
