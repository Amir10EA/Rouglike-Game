package com.example;

public class DoorTile extends Tile {
    private String environmentType;
    private int nextMapWidth;
    private int nextMapHeight;

    public DoorTile(String environmentType, int nextMapWidth, int nextMapHeight) {
        super(true, "door"); // Door tiles are walkable and of type "door"
        this.environmentType = environmentType;
        this.nextMapWidth = nextMapWidth;
        this.nextMapHeight = nextMapHeight;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public int getNextMapWidth() {
        return nextMapWidth;
    }

    public int getNextMapHeight() {
        return nextMapHeight;
    }
}

