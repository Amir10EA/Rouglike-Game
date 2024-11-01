package com.example;

import java.util.List;
import java.util.Scanner;

public class Game {
    private Player player;
    private Map map;
    private int playerX;
    private int playerY;
    private static final int INITIAL_HEALTH = 100;
    private static final int INITIAL_MAP_WIDTH = 10;
    private static final int INITIAL_MAP_HEIGHT = 10;
    private static final EnvironmentType INITIAL_ENVIRONMENT = EnvironmentType.NORMAL;

    public Game(Player player) {
        this.player = player;
        resetGame();
    }

    void resetGame() {
        System.out.println("Starting a new game...");
        this.map = initializeMap(INITIAL_MAP_WIDTH, INITIAL_MAP_HEIGHT, INITIAL_ENVIRONMENT);
        player.setHealth(INITIAL_HEALTH);
        player.cancelCurrentQuest(); // Reset quest status
        centerPlayerOnMap();
    }

    private Map initializeMap(int width, int height, EnvironmentType environmentType) {
        Map map = new Map(width, height, environmentType);
        System.out.println("Entered a new map with environment: " + environmentType);
        player.applyEnvironmentEffect(environmentType);
        return map;
    }

    private void centerPlayerOnMap() {
        this.playerX = map.getWidth() / 2;
        this.playerY = map.getHeight() / 2;
        System.out.println("Player starting at (" + playerX + ", " + playerY + ")");
    }

    public void exploreMap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter direction (WASD) to move, or 'Q' to quit:");
            String input = scanner.nextLine().toUpperCase();

            // Handle movement
            int[] newCoordinates = handleMovement(input, playerX, playerY, map.getWidth(), map.getHeight());
            if (newCoordinates == null) {
                scanner.close();
                System.out.println("Exiting game.");
                return;
            }
            playerX = newCoordinates[0];
            playerY = newCoordinates[1];

            System.out.println("Player moved to (" + playerX + ", " + playerY + ")");

            // Check if the player is on a DoorTile and handle the transition
            Tile tile = map.getTile(playerX, playerY);
            handleTile(tile);
        }
    }

    void handleTile(Tile tile) {
        if (tile instanceof DoorTile) {
            transitionToNewMap((DoorTile) tile);
            centerPlayerOnMap();
        } else if (tile instanceof QuestGiverTile) {
            QuestGiverTile questTile = (QuestGiverTile) tile;
            questTile.getQuestGiver().interact(player);
        } else if (tile instanceof EnemyTile) {
            EnemyTile enemyTile = (EnemyTile) tile;
            Enemy enemy = enemyTile.getEnemy();
            if (enemy.getRace() != player.getRace()) {
                startBattle(enemy);
            } else {
                startAllyEncounter(enemy);
            }
        } else {
            System.out.println("Current terrain type: " + tile.getTerrainType());
        }
    }

    void startAllyEncounter(Enemy friendlyEnemy) {
        AllyEncounter encounter = new AllyEncounter(player, friendlyEnemy);
        encounter.startEncounter();
    }

    void startBattle(Enemy enemy) {
        Battle battle = new Battle(player, enemy);
        boolean playerSurvived = battle.startBattle();
        if (playerSurvived) {
            System.out.println("Enemy defeated!");
            progressQuestIfApplicable();
        } else {
            System.out.println("Player was defeated! Respawning...");
            resetGame(); // Full game reset on death
        }
    }

    void progressQuestIfApplicable() {
        if (player.getCurrentQuest() != null) {
            player.getCurrentQuest().enemyDefeated(player);
            System.out.println("Quest progression: " + player.getCurrentQuest().getEnemiesDefeated() + "/" + player.getCurrentQuest().getTargetEnemies());
        } else {
            System.out.println("No active quest to progress.");
        }
    }

    public void exploreMapForTest(List<String> movements) {
        for (String input : movements) {
            input = input.toUpperCase();

            //Handle movement
            int[] newCoordinates = handleMovement(input, playerX, playerY, map.getWidth(), map.getHeight());
            if (newCoordinates == null) {
                System.out.println("Exiting game.");
                return;
            }
            playerX = newCoordinates[0];
            playerY = newCoordinates[1];

            System.out.println("Player moved to (" + playerX + ", " + playerY + ")");

            //Check if the player is on a DoorTile and handle the transition
            Tile tile = map.getTile(playerX, playerY);
            if (tile instanceof DoorTile doorTile) {
                transitionToNewMap(doorTile);
                centerPlayerOnMap();
            } else {
                System.out.println("Current terrain type: " + tile.getTerrainType());
            }
        }
    }

    public int[] handleMovement(String input, int playerX, int playerY, int mapWidth, int mapHeight) {
        switch (input) {
            case "W": if (playerY > 1) playerY--; break;
            case "A": if (playerX > 1) playerX--; break;
            case "S": if (playerY < mapHeight - 2) playerY++; break;
            case "D": if (playerX < mapWidth - 2) playerX++; break;
            case "Q": return null; // Exit signal
            default:
                System.out.println("Invalid input. Use W, A, S, D to move or Q to quit.");
                return new int[] {playerX, playerY}; // No movement
        }
        return new int[] {playerX, playerY};
    }

    void transitionToNewMap(DoorTile doorTile) {
        System.out.println("Found a door leading to: " + doorTile.getEnvironmentType() + " environment.");
        map = initializeMap(doorTile.getNextMapWidth(), doorTile.getNextMapHeight(), doorTile.getEnvironmentType());
    }

    public int[] getPlayerPosition() {
        return new int[] { playerX, playerY };
    }

    public Map getMap() {
        return map;
    }
}