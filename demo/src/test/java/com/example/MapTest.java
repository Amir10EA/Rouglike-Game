package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MapTest {

    private static final int NORMAL_MAP_WIDTH = 10;
    private static final int NORMAL_MAP_HEIGHT = 10;
    private static final int MIN_MAP_SIZE = 3;
    private static final int TOO_SMALL_MAP_SIZE = 2;
    private static final int LARGE_MAP_SIZE = 1000;
    private static final int NON_SQUARE_MAP_WIDTH = 3;
    private static final int NON_SQUARE_MAP_HEIGHT = 5;
    private static final int MIN_DOORS = 2;
    private static final int MAX_DOORS = 3;

    @Test
    public void testMapInitialization() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        assertEquals(NORMAL_MAP_WIDTH, map.getWidth());
        assertEquals(NORMAL_MAP_HEIGHT, map.getHeight());
    }

    @Test
    public void testRandomMapGeneration() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        assertNotNull(map.getTile(0, 0));
        assertNotNull(map.getTile(NORMAL_MAP_WIDTH - 1, NORMAL_MAP_HEIGHT - 1));
    }

    // min map storlek: 3x3.
    @Test
    public void testMinimumSizeMap() {
        Map map = new Map(MIN_MAP_SIZE, MIN_MAP_SIZE);
        assertEquals(MIN_MAP_SIZE, map.getWidth());
        assertEquals(MIN_MAP_SIZE, map.getHeight());
    }

    @Test
    public void testTooSmallMapThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Map(TOO_SMALL_MAP_SIZE, TOO_SMALL_MAP_SIZE);
        });
        assertEquals("Map size must be at least 3x3", exception.getMessage());
    }

    @Test
    public void testLargeMapCreation() {
        Map largeMap = new Map(LARGE_MAP_SIZE, LARGE_MAP_SIZE);
        assertEquals(LARGE_MAP_SIZE, largeMap.getWidth());
        assertEquals(LARGE_MAP_SIZE, largeMap.getHeight());
    }

    @Test
    public void testNonSquareMap() {
        Map map = new Map(NON_SQUARE_MAP_WIDTH, NON_SQUARE_MAP_HEIGHT);
        assertEquals(NON_SQUARE_MAP_WIDTH, map.getWidth());
        assertEquals(NON_SQUARE_MAP_HEIGHT, map.getHeight());
    }

    @Test
    public void testTileAttributes() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        Tile tile = map.getTile(0, 0);
        assertNotNull(tile);
        assertNotNull(tile.getTerrainType());
        assertFalse(tile.isVisible()); // ska ej vara visible från början
    }

    @Test
    public void testOutOfBoundsTile() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        assertNull(map.getTile(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT));
    }

    @Test
    public void testTerrainGeneration() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        boolean foundWall = false;
        boolean foundFloor = false; // floor: terrain player kan gå på

        for (int x = 0; x < NORMAL_MAP_WIDTH; x++) {
            for (int y = 0; y < NORMAL_MAP_HEIGHT; y++) {
                Tile tile = map.getTile(x, y);
                if (tile.getTerrainType().equals("wall")) {
                    foundWall = true;
                }
                if (tile.getTerrainType().equals("floor")) {
                    foundFloor = true;
                }
            }
        }

        assertTrue(foundWall, "At least one wall should be generated");
        assertTrue(foundFloor, "At least one floor should be generated");
    }

    @Test
    public void testWallsSurroundMap() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);

        for (int x = 0; x < NORMAL_MAP_WIDTH; x++) {
            assertFalse(map.getTile(x, 0).isWalkable());
            assertFalse(map.getTile(x, NORMAL_MAP_HEIGHT - 1).isWalkable());
        }

        for (int y = 0; y < NORMAL_MAP_HEIGHT; y++) {
            assertFalse(map.getTile(0, y).isWalkable());
            assertFalse(map.getTile(NORMAL_MAP_WIDTH - 1, y).isWalkable());
        }
    }

    @Test
    public void testDoorPlacement() { // ska alltid finnas 2-3 dörrar i ett rum
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        int doorCount = 0;

        for (int x = 1; x < NORMAL_MAP_WIDTH - 1; x++) { //inside the walls
            for (int y = 1; y < NORMAL_MAP_HEIGHT - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile.getTerrainType().equals("door")) {
                    doorCount++;
                    assertNotNull(tile.getRoomType());
                }
            }
        }

        assertTrue(doorCount >= MIN_DOORS && doorCount <= MAX_DOORS, "There should be 2-3 doors.");
    }

    @Test
    public void testObjectPlacement() {
        Map map = new Map(NORMAL_MAP_WIDTH, NORMAL_MAP_HEIGHT);
        boolean foundRockOrTree = false;

        for (int x = 1; x < NORMAL_MAP_WIDTH - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_HEIGHT - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile.getTerrainType().equals("rock") || tile.getTerrainType().equals("tree")) {
                    foundRockOrTree = true;
                    assertEquals("normal", tile.getRoomType());
                }
            }
        }

        assertTrue(foundRockOrTree, "There should be at least one rock or tree in normal roomms.");
    }

}