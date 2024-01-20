package com.example.ande.model;

public class Animal {
    private String type;
    private int maxPoints;
    private int imageResourceId;
    private int animalId;



    public Animal(String type, int maxPoints , int imageResourceId) {
        this.type = type;
        this.imageResourceId = imageResourceId;
        this.maxPoints = maxPoints;
    }

    public Animal(int animalId, String type, int maxPoints , int imageResourceId) {
        this.animalId = animalId;
        this.type = type;
        this.imageResourceId = imageResourceId;
        this.maxPoints = maxPoints;
    }

    public String getType() {
        return type;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getAnimalId() {
        return animalId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
