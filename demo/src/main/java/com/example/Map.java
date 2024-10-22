package com.example;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    private Tile[][] tiles;
    private String environmentType;

    public Map(int width, int height, String environmentType) {
        this.width = width;
        this.height = height;
        this.environmentType = environmentType;
        this.tiles = new Tile[width][height];
        initializeMap(); 
    }

    public void initializeMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    tiles[x][y] = new Tile(false, "wall"); 
                } else {
                    tiles[x][y] = new Tile(true, "floor");
                }
            }
        }
        placeDoors();
    }

    private void placeDoors() {
        Random random = new Random();
        int numDoors = random.nextInt(2) + 2; //2-3 dörrar

        for (int i = 0; i < numDoors; i++) {
            int doorX = random.nextInt(width - 2) + 1; //innanför väggarna 
            int doorY = random.nextInt(height - 2) + 1; 

            if (tiles[doorX][doorY].getTerrainType().equals("floor")) {
                String newEnvironment = getRandomEnvironment();
                int nextMapWidth = getRandomMapDimension();
                int nextMapHeight = getRandomMapDimension();
                tiles[doorX][doorY] = new DoorTile(newEnvironment, nextMapWidth, nextMapHeight);
            }
        }
    }

    private String getRandomEnvironment() {
        String[] environments = {"stormy", "sandy", "lava", "forest", "icy"};
        return environments[new Random().nextInt(environments.length)];
    }

    private int getRandomMapDimension() {
        return new Random().nextInt(11) + 5; //5-15
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null; 
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
