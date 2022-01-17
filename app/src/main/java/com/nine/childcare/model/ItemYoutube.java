package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

public class ItemYoutube {
//    @SerializedName("id")
//    private IdYoutube id;
    @SerializedName("snippet")
    private SnippetYoutube snippet;

    public ItemYoutube() {
    }

    public ItemYoutube(SnippetYoutube snippet) {
        this.snippet = snippet;
    }

    public SnippetYoutube getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetYoutube snippet) {
        this.snippet = snippet;
    }
}
