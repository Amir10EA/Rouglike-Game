package com.example;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    private Tile[][] tiles;
    private EnvironmentType environmentType;
    private static final int MIN_SIZE = 3;

    public Map(int width, int height, EnvironmentType environmentType) {
        if (width < MIN_SIZE || height < MIN_SIZE) {
            throw new IllegalArgumentException("Map size must be at least " + MIN_SIZE + "x" + MIN_SIZE);
        }
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.environmentType = environmentType;
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
                EnvironmentType newEnvironment = EnvironmentType.getRandomEnvironment();
                int nextMapWidth = getRandomMapDimension();
                int nextMapHeight = getRandomMapDimension();
                tiles[doorX][doorY] = new DoorTile(newEnvironment, nextMapWidth, nextMapHeight);
            }
        }
    }

    private String getRandomEnvironment() {
        String[] environments = {"stormy", "sandy", "lava", "forest", "icy", "normal"};
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

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
