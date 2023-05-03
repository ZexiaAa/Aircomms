package com.example.aircomms.History;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryItem {
    private String title;
    private String description;
    private long lastViewedTime;

    public HistoryItem(String title, long lastViewedTime) {
        this.title = title;
        this.lastViewedTime = lastViewedTime;
    }

    public String getTitle() {
        return title;
    }


    public long getLastViewedTime() {
        return lastViewedTime;
    }

    public void setLastViewedTime(long lastViewedTime) {
        this.lastViewedTime = lastViewedTime;
    }

    public static String formatDate(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy h:mm a");
        return dateFormat.format(new Date(timestamp));
    }

    

}
