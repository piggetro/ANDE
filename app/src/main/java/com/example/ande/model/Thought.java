package com.example.ande.model;

public class Thought {
    private int userId;
    private String thoughtId;
    private String thoughtText;
    private String date;
    private String time;

    public Thought() {
    }

    public Thought(String thoughtId, String thoughtText) {
        this.thoughtId = thoughtId;
        this.thoughtText = thoughtText;
    }

    public Thought(int userId, String thoughtId, String thoughtText, String date, String time) {
        this.userId = userId;
        this.thoughtId = thoughtId;
        this.thoughtText = thoughtText;
        this.date = date;
        this.time = time;
    }

    public String getThoughtId() {
        return thoughtId;
    }

    public void setThoughtId(String thoughtId) {
        this.thoughtId = thoughtId;
    }

    public String getThoughtText() {
        return thoughtText;
    }

    public void setThoughtText(String thoughtText) {
        this.thoughtText = thoughtText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
