package com.example;

public class DoorTile extends Tile {
    private EnvironmentType environmentType;
    private int nextMapWidth;
    private int nextMapHeight;

    public DoorTile(EnvironmentType environmentType, int nextMapWidth, int nextMapHeight) {
        super(true, "door"); // Door tiles are walkable and of type "door"
        this.environmentType = environmentType;
        this.nextMapWidth = nextMapWidth;
        this.nextMapHeight = nextMapHeight;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public int getNextMapWidth() {
        return nextMapWidth;
    }

    public int getNextMapHeight() {
        return nextMapHeight;
    }
}

