package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Race race = Race.HUMAN;
        Player player = new Player("Noor", 100, 10, race);
        System.out.println("Player created: " + player.getName());
        List<Item> swordMaterials = new ArrayList<>();
        swordMaterials.add(new Item("Iron", 2, ItemType.SMITHING_STONE));
        Cost swordCost = new Cost(10.0, swordMaterials);
        Weapon sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);
        List<Item> axeMaterials = new ArrayList<>();
        axeMaterials.add(new Item("Steel", 3, ItemType.SMITHING_STONE));
        Cost axeCost = new Cost(15.0, axeMaterials);
        Weapon axe = new Weapon("Axe", 20, 80, 25, 1.2, WeaponType.AXE, axeCost);
        player.addItem(sword);
        player.addItem(axe);
        System.out.println("Weapons added to inventory.");
        player.equipWeapon(sword);
        System.out.println("Weapon equipped: " + player.getActiveWeapon().getName());
        System.out.println("Player Info:");
        System.out.println("Name: " + player.getName());
        System.out.println("Health: " + player.getHealth());
        System.out.println("Strength: " + player.getStrength());
        System.out.println("Equipped Weapon: " + player.getActiveWeapon().getName());
    }
}