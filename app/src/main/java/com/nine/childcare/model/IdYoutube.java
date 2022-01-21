package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

public class IdYoutube {
    @SerializedName("videoId")
    private String videoId;

    public IdYoutube() {
    }

    public IdYoutube(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
