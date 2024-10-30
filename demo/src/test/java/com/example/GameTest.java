package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    private static final int INITIAL_MAP_WIDTH = 10;
    private static final int INITIAL_MAP_HEIGHT = 10;
    private static final String INITIAL_ENVIRONMENT = "normal";
    private static final int NEW_MAP_WIDTH = 12;
    private static final int NEW_MAP_HEIGHT = 12;
    private static final String NEW_ENVIRONMENT = "stormy";
    private static final int PLAYER_INITIAL_HEALTH = 100;
    private static final int PLAYER_INITIAL_STRENGTH = 10;
    private static final int LAVA_DAMAGE = 5;
    private static final int STORMY_STRENGTH_BOOST = 2;
    private static final int ICY_DAMAGE = 2;
    private static final int FOREST_HEAL = 3;
    private static final int SANDY_STRENGTH_REDUCTION = -1;

    private Game game;
    private Player player;
    private Map initialMap;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer", PLAYER_INITIAL_HEALTH, PLAYER_INITIAL_STRENGTH, Race.HUMAN);
        game = new Game(player); // map initializes in game constructor
        initialMap = game.getMap();
    }

    @Test
    public void testPlayerStartsAtMapCenter() {
        int expectedCenterX = INITIAL_MAP_WIDTH / 2;
        int expectedCenterY = INITIAL_MAP_HEIGHT / 2;
        int[] playerPosition = game.getPlayerPosition(); // [X, Y]

        assertEquals(expectedCenterX, playerPosition[0], "Player should start at the map's center X position.");
        assertEquals(expectedCenterY, playerPosition[1], "Player should start at the map's center Y position.");
    }

    @Test
    public void testInitialMapProperties() {
        assertNotNull(initialMap, "Initial map should be created.");
        assertEquals(INITIAL_ENVIRONMENT, initialMap.getEnvironmentType(),
                "Initial environment type should be normal.");
        assertEquals(INITIAL_MAP_WIDTH, initialMap.getWidth(), "Initial map width should be correct.");
        assertEquals(INITIAL_MAP_HEIGHT, initialMap.getHeight(), "Initial map height should be correct.");
    }

    @Test
    public void testMovementWithinBounds() {
        int[] startPosition = game.getPlayerPosition();

        int[] newPosition = game.handleMovement("W", startPosition[0], startPosition[1], INITIAL_MAP_WIDTH,
                INITIAL_MAP_HEIGHT);
        assertEquals(startPosition[1] - 1, newPosition[1], "Player Y-coordinate should decrease when moving up.");

        newPosition = game.handleMovement("A", startPosition[0], startPosition[1], INITIAL_MAP_WIDTH,
                INITIAL_MAP_HEIGHT);
        assertEquals(startPosition[0] - 1, newPosition[0], "Player X-coordinate should decrease when moving left.");

        newPosition = game.handleMovement("S", startPosition[0], startPosition[1], INITIAL_MAP_WIDTH,
                INITIAL_MAP_HEIGHT);
        assertEquals(startPosition[1] + 1, newPosition[1], "Player Y-coordinate should increase when moving down.");

        newPosition = game.handleMovement("D", startPosition[0], startPosition[1], INITIAL_MAP_WIDTH,
                INITIAL_MAP_HEIGHT);
        assertEquals(startPosition[0] + 1, newPosition[0], "Player X-coordinate should increase when moving right.");
    }

    @Test
    public void testMovementOutOfBounds() {
        int[] topBoundary = game.handleMovement("W", INITIAL_MAP_WIDTH / 2, 1, INITIAL_MAP_WIDTH, INITIAL_MAP_HEIGHT);
        assertEquals(1, topBoundary[1], "Player should not move above the top boundary.");

        int[] leftBoundary = game.handleMovement("A", 1, INITIAL_MAP_HEIGHT / 2, INITIAL_MAP_WIDTH, INITIAL_MAP_HEIGHT);
        assertEquals(1, leftBoundary[0], "Player should not move beyond the left boundary.");

        int[] bottomBoundary = game.handleMovement("S", INITIAL_MAP_WIDTH / 2, INITIAL_MAP_HEIGHT - 2,
                INITIAL_MAP_WIDTH, INITIAL_MAP_HEIGHT);
        assertEquals(INITIAL_MAP_HEIGHT - 2, bottomBoundary[1], "Player should not move below the bottom boundary.");

        int[] rightBoundary = game.handleMovement("D", INITIAL_MAP_WIDTH - 2, INITIAL_MAP_HEIGHT / 2, INITIAL_MAP_WIDTH,
                INITIAL_MAP_HEIGHT);
        assertEquals(INITIAL_MAP_WIDTH - 2, rightBoundary[0], "Player should not move beyond the right boundary.");
    }

    @Test
    public void testTransitionToNewMapOnDoorTile() {
        DoorTile doorTile = new DoorTile(NEW_ENVIRONMENT, NEW_MAP_WIDTH, NEW_MAP_HEIGHT);
        game.transitionToNewMap(doorTile);

        Map newMap = game.getMap();
        assertEquals(NEW_ENVIRONMENT, newMap.getEnvironmentType(), "New map environment should be stormy.");
        assertEquals(NEW_MAP_WIDTH, newMap.getWidth(), "New map width should match DoorTile property.");
        assertEquals(NEW_MAP_HEIGHT, newMap.getHeight(), "New map height should match DoorTile property.");
    }

    @Test
    public void testInvalidMovementInput() {
        int[] startPosition = game.getPlayerPosition();
        int[] resultPosition = game.handleMovement("X", startPosition[0], startPosition[1], INITIAL_MAP_WIDTH,
                INITIAL_MAP_HEIGHT);

        assertArrayEquals(startPosition, resultPosition, "Invalid input should not change the player position.");
    }

    @Test
    public void testQuitInput() {
        int[] result = game.handleMovement("Q", 5, 5, INITIAL_MAP_WIDTH, INITIAL_MAP_HEIGHT);

        assertNull(result, "Input 'Q' should signal game quit by returning null.");
    }

    @Test
    public void testApplyLavaEnvironmentEffect() {
        player.applyEnvironmentEffect("lava");
        assertEquals(PLAYER_INITIAL_HEALTH - LAVA_DAMAGE, player.getHealth(),
                "Player should take 5 damage in lava environment.");
        assertEquals(PLAYER_INITIAL_STRENGTH, player.getStrength(),
                "Player strength should remain unchanged in lava environment.");
    }

    @Test
    public void testApplyStormyEnvironmentEffect() {
        player.applyEnvironmentEffect("stormy");
        assertEquals(PLAYER_INITIAL_HEALTH, player.getHealth(),
                "Player health should remain unchanged in stormy environment.");
        assertEquals(PLAYER_INITIAL_STRENGTH + STORMY_STRENGTH_BOOST, player.getStrength(),
                "Player strength should increase by 2 in stormy environment.");
    }

    @Test
    public void testApplyIcyEnvironmentEffect() {
        player.applyEnvironmentEffect("icy");
        assertEquals(PLAYER_INITIAL_HEALTH - ICY_DAMAGE, player.getHealth(),
                "Player should take 2 damage in icy environment.");
        assertEquals(PLAYER_INITIAL_STRENGTH, player.getStrength(),
                "Player strength should remain unchanged in icy environment.");
    }

    @Test
    public void testApplyForestEnvironmentEffect() {
        player.applyEnvironmentEffect("forest");
        assertEquals(PLAYER_INITIAL_HEALTH + FOREST_HEAL, player.getHealth(),
                "Player should heal by 3 in forest environment.");
        assertEquals(PLAYER_INITIAL_STRENGTH, player.getStrength(),
                "Player strength should remain unchanged in forest environment.");
    }

    @Test
    public void testApplySandyEnvironmentEffect() {
        player.applyEnvironmentEffect("sandy");
        assertEquals(PLAYER_INITIAL_HEALTH, player.getHealth(),
                "Player health should remain unchanged in sandy environment.");
        assertEquals(PLAYER_INITIAL_STRENGTH + SANDY_STRENGTH_REDUCTION, player.getStrength(),
                "Player strength should decrease by 1 in sandy environment.");
    }

    @Test
    public void testApplyNormalEnvironmentEffect() {
        player.applyEnvironmentEffect("normal");
        assertEquals(PLAYER_INITIAL_HEALTH, player.getHealth(),
                "Player health should remain unchanged in normal environment.");
        assertEquals(PLAYER_INITIAL_STRENGTH, player.getStrength(),
                "Player strength should remain unchanged in normal environment.");
    }

    @Test
    public void testClearEnvironmentEffect() {
        player.applyEnvironmentEffect("stormy");
        assertEquals(PLAYER_INITIAL_STRENGTH + STORMY_STRENGTH_BOOST, player.getStrength(),
                "Player strength should increase in stormy environment.");

        player.clearEnvironmentEffect();
        assertEquals(PLAYER_INITIAL_STRENGTH, player.getStrength(),
                "Player strength should reset after clearing environment effect.");
    }
}
