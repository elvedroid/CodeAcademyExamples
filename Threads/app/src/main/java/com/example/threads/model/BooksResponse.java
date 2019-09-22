package com.example.threads.model;

import java.util.List;

public class BooksResponse {
    private String kind;
    private int totalItems;
    private List<Book> items;

    public BooksResponse() {
    }

    public BooksResponse(String kind, int totalItems, List<Book> items) {
        this.kind = kind;
        this.totalItems = totalItems;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
