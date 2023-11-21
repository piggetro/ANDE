package com.example.ande;

public class ThoughtRecyclerItem {
    private String thoughtId;
    private String thoughtText;

    public ThoughtRecyclerItem(String thoughtId, String thoughtText) {
        this.thoughtId = thoughtId;
        this.thoughtText = thoughtText;
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
}
