package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.initializeGame();
    }

    public void initializeGame() {
        Player player = new Player("Noor", 100, 10, Race.HUMAN);
        System.out.println("Player created: " + player.getName());
        List<Item> swordMaterials = new ArrayList<>();
        swordMaterials.add(new Item("Iron", 2, ItemType.SMITHING_STONE));
        Cost swordCost = new Cost(10.0, swordMaterials);
        Weapon sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);
        System.out.println("Weapon created: " + sword.getName());
    }

    public void addWeaponToInventory(Player player, Weapon weapon) {
        player.addItem(weapon);
        System.out.println("Weapon added to inventory.");
    }

    public void equipWeaponToPlayer(Player player, Weapon weapon) {
        player.equipWeapon(weapon);
        System.out.println("Weapon equipped: " + player.getActiveWeapon().getName());
    }
}
