package com.example.aircomms.Bookmark;

public class BookmarkItem {

    private String title;
    private String description;
    // Add any other properties you need for your bookmark item

    public BookmarkItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
