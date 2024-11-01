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

    @Test
    public void testQuestGiverTileInitialization() {
        QuestGiver questGiver = new QuestGiver("Test Quest Giver");
        QuestGiverTile questGiverTile = new QuestGiverTile(questGiver);
        assertTrue(questGiverTile.isWalkable(), "QuestGiverTile should be walkable.");
        assertEquals("questgiver", questGiverTile.getTerrainType(), "QuestGiverTile terrain type should be questgiver.");
        assertNotNull(questGiverTile.getQuestGiver(), "QuestGiverTile should have a QuestGiver.");
        assertEquals("Test Quest Giver", questGiverTile.getQuestGiver().getName(), "QuestGiver name should match.");
    }

    @Test
    public void testEnemyTileInitialization() {
        Enemy enemy = new Enemy("Test Enemy", 50, 10, 1, Race.GOBLIN, 10);
        EnemyTile enemyTile = new EnemyTile(enemy);
        assertTrue(enemyTile.isWalkable(), "EnemyTile should be walkable.");
        assertEquals("enemy", enemyTile.getTerrainType(), "EnemyTile terrain type should be enemy.");
        assertNotNull(enemyTile.getEnemy(), "EnemyTile should have an Enemy.");
        assertEquals("Test Enemy", enemyTile.getEnemy().getName(), "Enemy name should match.");
        assertEquals(50, enemyTile.getEnemy().getHealth(), "Enemy health should match.");
        assertEquals(Race.GOBLIN, enemyTile.getEnemy().getRace(), "Enemy race should be GOBLIN.");
    }
}
