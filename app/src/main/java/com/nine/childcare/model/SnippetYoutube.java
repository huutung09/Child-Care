package com.nine.childcare.model;

import com.google.gson.annotations.SerializedName;

public class SnippetYoutube {
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnails")
    private ThumbnailYoutube thumbnails;

    public SnippetYoutube() {
    }

    public SnippetYoutube(String publishedAt, String title, String description, ThumbnailYoutube thumbnails) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThumbnailYoutube getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbnailYoutube thumbnails) {
        this.thumbnails = thumbnails;
    }
}
