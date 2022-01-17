package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullYoutube {
//    @SerializedName("nextPageToken")
//    private String nextPageToken;
    @SerializedName("items")
    private List<ItemYoutube> items;

    public FullYoutube() {
    }

    public FullYoutube(List<ItemYoutube> items) {
        this.items = items;
    }

    public List<ItemYoutube> getItems() {
        return items;
    }

    public void setItems(List<ItemYoutube> items) {
        this.items = items;
    }
}
