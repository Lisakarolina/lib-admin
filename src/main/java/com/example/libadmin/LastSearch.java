package com.example.libadmin;

public enum LastSearch {
    INSTANCE;
    String lastSearchURL = "http://localhost:8080/list";

    public String getLastSearchURL() {
        return lastSearchURL;
    }

    public void setLastSearchURL(String url) {
        this.lastSearchURL = url;
    }
}
