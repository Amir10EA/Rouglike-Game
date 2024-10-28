package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main mainApp;
    private Player player;
    private Weapon sword;

    @BeforeEach
    void setUp() {
        mainApp = new Main();
        player = new Player("Noor", 100, 10, Race.HUMAN);
        List<Item> swordMaterials = new ArrayList<>();
        swordMaterials.add(new Item("Iron", 2, ItemType.SMITHING_STONE));
        Cost swordCost = new Cost(10.0, swordMaterials);
        sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);
    }

    @Test
    void testPlayerStartsWithoutWeapon() {
        assertTrue(player.getEquipmentManager().getInventory().isEmpty(), "Player inventory should be empty at the start");
        assertNull(player.getActiveWeapon(), "Player should not have an active weapon at the start");
    }

    @Test
    void testAddWeaponToInventory() {
        mainApp.addWeaponToInventory(player, sword);
        assertTrue(player.getEquipmentManager().getInventory().contains(sword), "Weapon should be in the inventory after adding");
    }

    @Test
    void testEquipWeaponToPlayer() {
        mainApp.addWeaponToInventory(player, sword);
        mainApp.equipWeaponToPlayer(player, sword);
        assertEquals(sword, player.getActiveWeapon(), "Weapon should be equipped as active weapon after equipping");
    }

    @Test
    void testInitializeGameProcess() {
        assertTrue(player.getEquipmentManager().getInventory().isEmpty(), "Player inventory should be empty at the start");
        assertNull(player.getActiveWeapon(), "Player should not have an active weapon at the start");
    }
}
