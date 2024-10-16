package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TileTest {

    private static final String TERRAIN_FLOOR = "floor";
    private static final String TERRAIN_WALL = "wall";
    private static final String ROOM_TYPE_NORMAL = "normal";

    @Test
    public void testTileInitialization() {
        Tile tile = new Tile(true, TERRAIN_FLOOR, ROOM_TYPE_NORMAL); //(boolean walkable, String terrainType, String roomType)
        assertTrue(tile.isWalkable());
        assertEquals(TERRAIN_FLOOR, tile.getTerrainType());
        assertEquals(ROOM_TYPE_NORMAL, tile.getRoomType());
        assertFalse(tile.isVisible()); 
    }

    @Test
    public void testNonWalkableTile() {
        Tile tile = new Tile(false, TERRAIN_WALL, null);
        assertFalse(tile.isWalkable());
        assertEquals(TERRAIN_WALL, tile.getTerrainType());
        assertNull(tile.getRoomType());
        assertFalse(tile.isVisible()); 
    }

    @Test
    public void testTileVisibility() {
        Tile tile = new Tile(true, TERRAIN_FLOOR, ROOM_TYPE_NORMAL);
        assertFalse(tile.isVisible());

        tile.setVisible(true);
        assertTrue(tile.isVisible());

        tile.setVisible(false);
        assertFalse(tile.isVisible());
    }
}