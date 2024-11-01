package com.example;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    private Tile[][] tiles;
    private EnvironmentType environmentType;
    private static final int MIN_SIZE = 5;

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
        placeQuestGiver();
        placeEnemies(3);
    }

    private void placeDoors() {
        Random random = new Random();
        int numDoors = random.nextInt(2) + 2; // 2-3 dörrar

        for (int i = 0; i < numDoors; i++) {
            int doorX = random.nextInt(width - 2) + 1; // innanför väggarna
            int doorY = random.nextInt(height - 2) + 1;

            if (tiles[doorX][doorY].getTerrainType().equals("floor")) {
                EnvironmentType newEnvironment = EnvironmentType.getRandomEnvironment();
                int nextMapWidth = getRandomMapDimension();
                int nextMapHeight = getRandomMapDimension();
                tiles[doorX][doorY] = new DoorTile(newEnvironment, nextMapWidth, nextMapHeight);
            }
        }
    }

    private void placeQuestGiver() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(width - 2) + 1;
            y = random.nextInt(height - 2) + 1;
        } while (tiles[x][y] instanceof DoorTile || tiles[x][y] instanceof QuestGiverTile);
    
        tiles[x][y] = new QuestGiverTile(new QuestGiver("Quest Giver"));
        System.out.println("Placed Quest Giver at (" + x + ", " + y + ")");
    }
    

    private void placeEnemies(int numberOfEnemies) {
        Random random = new Random();
        for (int i = 0; i < numberOfEnemies; i++) {
            int x, y;
            do {
                x = random.nextInt(width - 2) + 1;
                y = random.nextInt(height - 2) + 1;
            } while (tiles[x][y] instanceof DoorTile || tiles[x][y] instanceof QuestGiverTile || tiles[x][y] instanceof EnemyTile);
    
            tiles[x][y] = new EnemyTile(new Enemy("Enemy " + (i + 1), 50, 10, 1, Race.getRandomRace(), 10));
            System.out.println("Placed Enemy " + (i + 1) + " at (" + x + ", " + y + ")");
        }
    }
    

    private int getRandomMapDimension() {
        return new Random().nextInt(11) + 5; // 5-15
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    public void setTile(int x, int y, Tile tile) { // metod används endast för test
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = tile;
        } else {
            throw new IndexOutOfBoundsException("Tile coordinates out of bounds.");
        }
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
