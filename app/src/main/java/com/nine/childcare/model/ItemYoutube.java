package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

public class ItemYoutube {
    @SerializedName("id")
    private IdYoutube id;
    @SerializedName("snippet")
    private SnippetYoutube snippet;

    public ItemYoutube() {
    }

    public ItemYoutube(IdYoutube id, SnippetYoutube snippet) {
        this.id = id;
        this.snippet = snippet;
    }

    public SnippetYoutube getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetYoutube snippet) {
        this.snippet = snippet;
    }

    public IdYoutube getId() {
        return id;
    }

    public void setId(IdYoutube id) {
        this.id = id;
    }
}
