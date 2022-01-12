package com.nine.childcare.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String uid, name, email, address;
    private double longitude, latitude;

    public User(String uid, String name, String email, String address, double longitude, double latitude) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
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
}
