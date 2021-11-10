package com.example.mytodoapplication;

public class Item {
    private String Title;
    private String details;
    private boolean done;

    public Item(String title, String details, boolean done) {
        Title = title;
        this.details = details;
        this.done = done;
    }

    public Item(String title, String details) {
        this(title, details, false);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Title='" + Title + '\'' +
                ", details='" + details + '\'' +
                ", done=" + done +
                '}';
    }
}
