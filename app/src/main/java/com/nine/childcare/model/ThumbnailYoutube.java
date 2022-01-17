package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

public class ThumbnailYoutube {
    @SerializedName("medium")
    private MediumYoutube medium;

    public ThumbnailYoutube() {
    }

    public ThumbnailYoutube(MediumYoutube medium) {
        this.medium = medium;
    }

    public MediumYoutube getMedium() {
        return medium;
    }

    public void setMedium(MediumYoutube medium) {
        this.medium = medium;
    }
}
