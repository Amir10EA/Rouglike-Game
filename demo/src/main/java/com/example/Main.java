package com.example;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Race race = Race.HUMAN;
        Player player = new Player("Noor", 100, 10, race);
        System.out.println("Player created: " + player.getName());
        Map<String, Integer> swordMaterials = new HashMap<>();
        swordMaterials.put("Iron", 2);
        Cost swordCost = new Cost(10.0, swordMaterials);
        Weapon sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);
        Map<String, Integer> axeMaterials = new HashMap<>();
        axeMaterials.put("Steel", 3);
        Cost axeCost = new Cost(15.0, axeMaterials);
        Weapon axe = new Weapon("Axe", 20, 80, 25, 1.2, WeaponType.AXE, axeCost);
        player.addWeapon(sword);
        player.addWeapon(axe);
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
