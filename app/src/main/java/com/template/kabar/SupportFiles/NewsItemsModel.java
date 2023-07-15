package com.template.kabar.SupportFiles;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class NewsItemsModel {
    @SerializedName("status")
    String status;

    @SerializedName("totalResults")
    int totalResults;

    @SerializedName("articles")
    List<Articles> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
