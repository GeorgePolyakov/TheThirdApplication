package com.example.thethirdapplication.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("articles")
    private List <Articles> articles;

    private String totalResults;

    public String getStatus() {
        return status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public List <Articles> getArticles() {
        return articles;
    }
}
