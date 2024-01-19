package com.example.ande.model;

public class Animal {
    private String name;
    private int imageResourceId;

    public Animal(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
