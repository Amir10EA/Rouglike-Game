package com.example;

import java.util.Scanner;

public class Game {
    private Player player;
    private Map map;
    private int playerX;
    private int playerY;

    public Game(Player player) {
        this.player = player;
        this.map = initializeMap(10, 10, EnvironmentType.NORMAL); 
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
            if (tile instanceof DoorTile) {
                DoorTile doorTile = (DoorTile) tile;
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