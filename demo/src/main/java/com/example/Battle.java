package com.example;

import java.util.List;
import java.util.Scanner;

public class Battle {
    private Player player;
    private Enemy enemy;
    private static final int PLAYER_ATTACK_OPTION = 1;
    private List<Integer> testMoves; // Optional input for test moves

    // Constructor for normal gameplay (uses Scanner)
    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    // Overloaded constructor for test input (uses predefined moves)
    public Battle(Player player, Enemy enemy, List<Integer> testMoves) {
        this(player, enemy);
        this.testMoves = testMoves;
    }

    public boolean startBattle() {
        if (testMoves != null) {
            return startBattle(testMoves); // Use predefined moves for testing
        } else {
            return startBattleWithScanner(); // Interactive mode with Scanner
        }
    }

    private boolean startBattleWithScanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("A wild " + enemy.getName() + " appears! Battle begins!");

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\nChoose your action:");
            System.out.println(PLAYER_ATTACK_OPTION + ". Attack");

            int choice = scanner.nextInt();

            if (choice == PLAYER_ATTACK_OPTION) {
                playerAttack();
                if (enemy.isAlive()) {
                    enemyAttack();
                }
            } else {
                System.out.println("Invalid choice. Please select " + PLAYER_ATTACK_OPTION + " to attack.");
            }
        }
        return player.isAlive();
    }

    // New method to simulate battle with predefined moves for testing
    private boolean startBattle(List<Integer> moves) {
        System.out.println("A wild " + enemy.getName() + " appears! Battle begins!");

        for (int choice : moves) {
            if (!player.isAlive() || !enemy.isAlive()) break;

            if (choice == PLAYER_ATTACK_OPTION) {
                playerAttack();
                if (enemy.isAlive()) {
                    enemyAttack();
                }
            } else {
                System.out.println("Invalid move in test sequence: " + choice);
            }
        }
        return player.isAlive();
    }

    void playerAttack() {
        int damage = player.calculateDamage();
        enemy.takeDamage(damage);
        System.out.println("You attacked " + enemy.getName() + " for " + damage + " damage!");
        if (!enemy.isAlive()) {
            System.out.println(enemy.getName() + " has been defeated!");
        }
    }

    void enemyAttack() {
        int damage = enemy.calculateDamage();
        player.takeDamage(damage);
        System.out.println(enemy.getName() + " attacked you for " + damage + " damage!");
        if (!player.isAlive()) {
            System.out.println("You have been defeated!");
        }
    }
}
