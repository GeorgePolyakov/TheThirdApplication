package com.example.thethirdapplication;

import com.google.gson.annotations.SerializedName;

class PhotoModel {
    @SerializedName("title")
    String tvtitle;

    @SerializedName("url")
    String fullUrl;

    public String getTitle() {
        return tvtitle;
    }

    public void setTitle(String title) {
        this.tvtitle = title;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }
}
