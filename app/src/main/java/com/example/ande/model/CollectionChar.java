package com.example.ande.model;

public class CollectionChar {
    String name;
    Integer image;

    Integer animalId;

    Integer currentPoints;

    public CollectionChar(String name, Integer image) {
        this.name = name;
        this.image = image;
    }

    public CollectionChar(int animalId, String name, Integer image, Integer currentPoints) {
        this.name = name;
        this.image = image;
        this.currentPoints = currentPoints;
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
    public void setCurrentPoints(Integer newPoints) {
        this.currentPoints = newPoints;
    }
    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public Integer getAnimalId() {
        return animalId;
    }
}
