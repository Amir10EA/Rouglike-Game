package com.example;

public class Tile {
    private boolean walkable;
    private boolean visible;
    private String terrainType;

    public Tile(boolean walkable, String terrainType) {
        this.walkable = walkable;
        this.terrainType = terrainType;
        this.visible = false;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTerrainType() {
        return terrainType;
    }
}
