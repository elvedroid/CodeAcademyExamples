package com.example.threads.model;

import java.util.List;

public class VolumeInfo {

    private String title;
    private List<String> authors;
    private String description;

    public VolumeInfo() {
    }

    public VolumeInfo(String title, List<String> authors, String description) {
        this.title = title;
        this.authors = authors;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
