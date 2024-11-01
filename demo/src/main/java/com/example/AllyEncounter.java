package com.example;

import java.util.Scanner;

public class AllyEncounter {
    private Player player;
    private Enemy friendlyEnemy;

    public AllyEncounter(Player player, Enemy friendlyEnemy) {
        this.player = player;
        this.friendlyEnemy = friendlyEnemy;
    }

    public void startEncounter() {
        System.out.println("Encounter with friendly " + friendlyEnemy.getName() + "!");
        System.out.println("Choose an action:");
        System.out.println("1. Collect resources");
        System.out.println("2. Receive quest progression");

        // Simulera användarens val
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            collectResources();
        } else if (choice == 2) {
            progressQuest();
        } else {
            System.out.println("Invalid choice, you move on without any action.");
        }
    }

    void collectResources() {
        System.out.println("You collected resources from " + friendlyEnemy.getName());
        player.addItem(new Item("Armor Stone", 1, ItemType.ARMOR_STONE));
    }

    void progressQuest() {
        System.out.println("You gained quest progression without battling.");
        player.getCurrentQuest().enemyDefeated(player);;
    }
}
