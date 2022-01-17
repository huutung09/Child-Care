package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

public class MediumYoutube {
    @SerializedName("url")
    private String url;

    public MediumYoutube() {
    }

    public MediumYoutube(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
