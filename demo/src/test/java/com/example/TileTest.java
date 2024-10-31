package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TileTest {

    private static final String TERRAIN_FLOOR = "floor";
    private static final String TERRAIN_WALL = "wall";
    private static final EnvironmentType NEW_ENVIRONMENT = EnvironmentType.LAVA;

    @Test
    public void testTileInitialization() {
        Tile tile = new Tile(true, TERRAIN_FLOOR);
        assertTrue(tile.isWalkable());
        assertEquals(TERRAIN_FLOOR, tile.getTerrainType());
        assertFalse(tile.isVisible());
    }

    @Test
    public void testNonWalkableTile() {
        Tile tile = new Tile(false, TERRAIN_WALL);
        assertFalse(tile.isWalkable());
        assertEquals(TERRAIN_WALL, tile.getTerrainType());
        assertFalse(tile.isVisible());
    }

    @Test
    public void testTileVisibility() {
        Tile tile = new Tile(true, TERRAIN_FLOOR);
        assertFalse(tile.isVisible());

        tile.setVisible(true);
        assertTrue(tile.isVisible());

        tile.setVisible(false);
        assertFalse(tile.isVisible());
    }

    @Test
    public void testDoorTileInitialization() {
        DoorTile doorTile = new DoorTile(NEW_ENVIRONMENT, 7, 8);
        assertTrue(doorTile.isWalkable());
        assertEquals("door", doorTile.getTerrainType());
        assertEquals(NEW_ENVIRONMENT, doorTile.getEnvironmentType());
        assertEquals(7, doorTile.getNextMapWidth());
        assertEquals(8, doorTile.getNextMapHeight());
    }
}
