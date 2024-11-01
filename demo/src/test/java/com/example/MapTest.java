package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MapTest {

    private static final int NORMAL_MAP_SIZE = 10;
    private static final int MIN_MAP_SIZE = 5;
    private static final int TOO_SMALL_MAP_SIZE = 2;
    private static final int LARGE_MAP_SIZE = 1000;
    private static final int NON_SQUARE_MAP_WIDTH = 8;
    private static final int NON_SQUARE_MAP_HEIGHT = 5;
    private static final int MIN_DOORS = 2;
    private static final int MAX_DOORS = 3;
    private static final int NUMBER_OF_ENEMIES = 3;

    @Test
    public void testMapInitialization() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        assertEquals(NORMAL_MAP_SIZE, map.getWidth());
        assertEquals(NORMAL_MAP_SIZE, map.getHeight());
    }

    @Test
    public void testRandomMapGeneration() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        assertNotNull(map.getTile(0, 0));
        assertNotNull(map.getTile(NORMAL_MAP_SIZE - 1, NORMAL_MAP_SIZE - 1));
    }

    // min map storlek: 5x5.
    @Test
    public void testMinimumSizeMap() {
        Map map = new Map(MIN_MAP_SIZE, MIN_MAP_SIZE, EnvironmentType.NORMAL);
        assertEquals(MIN_MAP_SIZE, map.getWidth());
        assertEquals(MIN_MAP_SIZE, map.getHeight());
    }

    @Test
    public void testTooSmallMapWidthThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Map(TOO_SMALL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        });
        assertEquals("Map size must be at least 5x5", exception.getMessage());
    }

    @Test
    public void testTooSmallMapHeightThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Map(NORMAL_MAP_SIZE, TOO_SMALL_MAP_SIZE, EnvironmentType.NORMAL);
        });
        assertEquals("Map size must be at least 5x5", exception.getMessage());
    }

    @Test
    public void testLargeMapCreation() {
        Map largeMap = new Map(LARGE_MAP_SIZE, LARGE_MAP_SIZE, EnvironmentType.NORMAL);
        assertEquals(LARGE_MAP_SIZE, largeMap.getWidth());
        assertEquals(LARGE_MAP_SIZE, largeMap.getHeight());
    }

    @Test
    public void testNonSquareMap() {
        Map map = new Map(NON_SQUARE_MAP_WIDTH, NON_SQUARE_MAP_HEIGHT, EnvironmentType.NORMAL);
        assertEquals(NON_SQUARE_MAP_WIDTH, map.getWidth());
        assertEquals(NON_SQUARE_MAP_HEIGHT, map.getHeight());
    }

    @Test
    public void testSetTile() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        DoorTile doorTile = new DoorTile(EnvironmentType.FOREST, 8, 8);

        map.setTile(5, 5, doorTile);

        Tile retrievedTile = map.getTile(5, 5);
        assertInstanceOf(DoorTile.class, retrievedTile, "Tile at (5,5) should be a DoorTile.");
        DoorTile retrievedDoorTile = (DoorTile) retrievedTile;
        assertEquals(EnvironmentType.FOREST, retrievedDoorTile.getEnvironmentType(),
                "DoorTile environment type should be FOREST.");
        assertEquals(8, retrievedDoorTile.getNextMapWidth(), "DoorTile's next map width should be 8.");
        assertEquals(8, retrievedDoorTile.getNextMapHeight(), "DoorTile's next map height should be 8.");
    }

    @Test
    public void testSetOutOfBoundsTile() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        DoorTile doorTile = new DoorTile(EnvironmentType.LAVA, 10, 10);

        assertThrows(IndexOutOfBoundsException.class, () -> map.setTile(NORMAL_MAP_SIZE, 0, doorTile),
                "Expected IndexOutOfBoundsException when x is out of bounds (x >= width)");
        assertThrows(IndexOutOfBoundsException.class, () -> map.setTile(0, NORMAL_MAP_SIZE, doorTile),
                "Expected IndexOutOfBoundsException when y is out of bounds (y >= height)");
        assertThrows(IndexOutOfBoundsException.class, () -> map.setTile(-1, 0, doorTile),
                "Expected IndexOutOfBoundsException when x is out of bounds (x < 0)");
        assertThrows(IndexOutOfBoundsException.class, () -> map.setTile(0, -1, doorTile),
                "Expected IndexOutOfBoundsException when y is out of bounds (y < 0)");
    }

    @Test
    public void testTileAttributes() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        Tile tile = map.getTile(0, 0);
        assertNotNull(tile);
        assertNotNull(tile.getTerrainType());
        assertFalse(tile.isVisible()); // ska ej vara visible från början
    }

    @Test
    public void testGetOutOfBoundsTile() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);

        assertNull(map.getTile(NORMAL_MAP_SIZE, 0), "Expected null when x is out of bounds (x >= width)");
        assertNull(map.getTile(0, NORMAL_MAP_SIZE), "Expected null when y is out of bounds (y >= height)");
        assertNull(map.getTile(-1, 0), "Expected null when x is out of bounds (x < 0)");
        assertNull(map.getTile(0, -1), "Expected null when y is out of bounds (y < 0)");
    }

    @Test
    public void testTerrainGeneration() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        boolean foundWall = false;
        boolean foundFloor = false;

        for (int x = 0; x < NORMAL_MAP_SIZE; x++) {
            for (int y = 0; y < NORMAL_MAP_SIZE; y++) {
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
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);

        for (int x = 0; x < NORMAL_MAP_SIZE; x++) {
            assertFalse(map.getTile(x, 0).isWalkable());
            assertFalse(map.getTile(x, NORMAL_MAP_SIZE - 1).isWalkable());
        }

        for (int y = 0; y < NORMAL_MAP_SIZE; y++) {
            assertFalse(map.getTile(0, y).isWalkable());
            assertFalse(map.getTile(NORMAL_MAP_SIZE - 1, y).isWalkable());
        }
    }

    @Test
    public void testQuestGiverPlacement() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        boolean questGiverFound = false;

        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile instanceof QuestGiverTile) {
                    questGiverFound = true;
                    assertInstanceOf(QuestGiverTile.class, tile, "Quest Giver Tile should be placed correctly.");
                    break;
                }
            }
        }
        assertTrue(questGiverFound, "There should be a Quest Giver on the map.");
    }

    @Test
    public void testEnemiesPlacement() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        int enemyCount = 0;

        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile instanceof EnemyTile) {
                    enemyCount++;
                    assertInstanceOf(EnemyTile.class, tile, "EnemyTile should be placed correctly.");
                    assertNotNull(((EnemyTile) tile).getEnemy(), "Enemy should be present in EnemyTile.");
                }
            }
        }
        assertEquals(NUMBER_OF_ENEMIES, enemyCount,
                "There should be exactly " + NUMBER_OF_ENEMIES + " enemies on the map.");
    }

    @Test
    public void testQuestGiverDoesNotOverlapWithDoors() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        Tile questGiverTile = null;

        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile instanceof QuestGiverTile) {
                    questGiverTile = tile;
                    break;
                }
            }
        }

        assertNotNull(questGiverTile, "QuestGiverTile should exist.");
        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                if (map.getTile(x, y) instanceof DoorTile) {
                    assertNotSame(map.getTile(x, y), questGiverTile,
                            "QuestGiverTile should not overlap with DoorTile.");
                }
            }
        }
    }

    @Test
    public void testEnemyDoesNotOverlapWithQuestGiverOrDoor() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);

        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile instanceof EnemyTile) {
                    assertFalse(tile instanceof QuestGiverTile, "EnemyTile should not overlap with QuestGiverTile.");
                    assertFalse(tile instanceof DoorTile, "EnemyTile should not overlap with DoorTile.");
                }
            }
        }
    }

    @Test
    public void testDoorPlacement() { // ska alltid finnas 2-3 dörrar i ett rum
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);
        int doorCount = 0;

        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile instanceof DoorTile) {
                    doorCount++;
                    DoorTile doorTile = (DoorTile) tile;
                    assertNotNull(doorTile.getEnvironmentType());
                    assertTrue(doorTile.getNextMapWidth() >= MIN_MAP_SIZE);
                    assertTrue(doorTile.getNextMapHeight() >= MIN_MAP_SIZE);
                }
            }
        }

        assertTrue(doorCount >= MIN_DOORS && doorCount <= MAX_DOORS, "There should be 2-3 doors.");
    }

    @Test
    public void testMapTransitionFromDoor() {
        Map map = new Map(NORMAL_MAP_SIZE, NORMAL_MAP_SIZE, EnvironmentType.NORMAL);

        // skapar en map från första hittade dörren
        for (int x = 1; x < NORMAL_MAP_SIZE - 1; x++) {
            for (int y = 1; y < NORMAL_MAP_SIZE - 1; y++) {
                Tile tile = map.getTile(x, y);
                if (tile instanceof DoorTile) {
                    DoorTile doorTile = (DoorTile) tile;
                    Map newMap = new Map(doorTile.getNextMapWidth(), doorTile.getNextMapHeight(),
                            doorTile.getEnvironmentType());
                    assertEquals(doorTile.getNextMapWidth(), newMap.getWidth());
                    assertEquals(doorTile.getNextMapHeight(), newMap.getHeight());
                    assertEquals(doorTile.getEnvironmentType(), newMap.getEnvironmentType());
                }
            }
        }
    }
}
