package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullYoutube {
    @SerializedName("items")
    private List<ItemYoutube> items;

    @SerializedName("nextPageToken")
    private String nextPageToken;

    public FullYoutube() {
    }

    public FullYoutube(List<ItemYoutube> items, String nextPageToken) {
        this.items = items;
        this.nextPageToken = nextPageToken;
    }

    public List<ItemYoutube> getItems() {
        return items;
    }

    public void setItems(List<ItemYoutube> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
