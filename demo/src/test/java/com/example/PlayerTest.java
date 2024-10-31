package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class PlayerTest {

    private Player player;
    private Race race;
    private Weapon sword;
    private Weapon axe;
    private Weapon mace;
    private Armor helmet;
    private Armor chestplate;

    @BeforeEach
    public void setUp() {
        player = new Player("Hero", 100, 10, 1, race);

        List<Item> swordMaterials = List.of(
            new Item("Iron", 2, ItemType.SMITHING_STONE)
        );
        Cost swordCost = new Cost(10.0, swordMaterials);
        sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);

        List<Item> axeMaterials = List.of(
            new Item("Steel", 3, ItemType.SMITHING_STONE)
        );
        Cost axeCost = new Cost(15.0, axeMaterials);
        axe = new Weapon("Axe", 20, 80, 25, 1.2, WeaponType.AXE, axeCost);

        List<Item> maceMaterials = List.of(
            new Item("Iron", 5, ItemType.SMITHING_STONE)
        );
        Cost maceCost = new Cost(20.0, maceMaterials);
        mace = new Weapon("Mace", 25, 90, 30, 1.0, WeaponType.MACE, maceCost);

        List<Item> helmetMaterials = List.of(
            new Item("Leather", 2, ItemType.ARMOR_STONE)
        );
        Cost helmetCost = new Cost(5.0, helmetMaterials);
        helmet = new Armor("Helmet", 5, 50, 10, 5, ArmorType.HELMET, helmetCost);

        List<Item> chestplateMaterials = List.of(
            new Item("Iron", 4, ItemType.ARMOR_STONE)
        );
        Cost chestplateCost = new Cost(20.0, chestplateMaterials);
        chestplate = new Armor("Chestplate", 15, 100, 30, 20, ArmorType.CHESTPLATE, chestplateCost);
    }

    @Test
    public void testPlayerCreation() {
        assertNotNull(player);
        assertEquals("Hero", player.getName());
        assertEquals(100, player.getHealth());
        assertEquals(100, player.getMaxHealth());
        assertEquals(10, player.getStrength());
        assertEquals(0, player.getExperience());
    }

    @Test
    public void testGainExperience() {
        player.gainExperience(50);
        assertEquals(50, player.getExperience());
        player.gainExperience(30);
        assertEquals(80, player.getExperience());
    }

    @Test
    public void testGainNegativeExperience() {
        player.gainExperience(-10);
        assertEquals(0, player.getExperience());
    }

    @Test
    public void testAddWeapon() {
        player.addItem(sword);
        assertEquals(1, player.getEquipmentManager().getInventory().size());
        assertEquals(sword, player.getEquipmentManager().getInventory().get(0));
    }

    @Test
    public void testRemoveWeapon() {
        player.addItem(sword);
        player.removeItem(sword);
        assertEquals(0, player.getEquipmentManager().getInventory().size());
    }

    @Test
    public void testEquipWeapon() {
        player.addItem(sword);
        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());
        assertTrue(player.getEquipmentManager().getInventory().contains(sword), "Inventory should contain the sword after equipping.");
    }

    @Test
    public void testEquipWeaponAlreadyEquipped() {
        player.addItem(sword);
        player.equipWeapon(sword);
        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());
        assertTrue(player.getEquipmentManager().getInventory().contains(sword), "Inventory should contain the sword after equipping.");
    }

    @Test
    public void testRemoveActiveWeaponAndEquipAnother() {
        player.addItem(sword);
        player.addItem(axe);

        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());

        player.removeItem(sword);
        assertNull(player.getActiveWeapon());

        player.equipWeapon(axe);
        assertEquals(axe, player.getActiveWeapon());
        assertTrue(player.getEquipmentManager().getInventory().contains(axe), "Inventory should contain the axe after equipping.");
    }

    @Test
    public void testAddMultipleWeaponsWithDifferentNames() {
        player.addItem(sword);
        player.addItem(axe);
        player.addItem(mace);

        assertEquals(3, player.getEquipmentManager().getInventory().size());
        assertTrue(player.getEquipmentManager().getInventory().contains(sword));
        assertTrue(player.getEquipmentManager().getInventory().contains(axe));
        assertTrue(player.getEquipmentManager().getInventory().contains(mace));
    }

    @Test
    public void testRemoveAllWeapons() {
        player.addItem(sword);
        player.addItem(axe);
        player.addItem(mace);

        player.removeItem(sword);
        player.removeItem(axe);
        player.removeItem(mace);

        assertEquals(0, player.getEquipmentManager().getInventory().size());
    }

    @Test
    public void testAddAndEquipMultipleWeapons() {
        player.addItem(sword);
        player.addItem(axe);
        player.addItem(mace);

        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());

        player.equipWeapon(axe);
        assertEquals(axe, player.getActiveWeapon());

        player.equipWeapon(mace);
        assertEquals(mace, player.getActiveWeapon());
    }

    @Test
    public void testEquipNonExistentWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.equipWeapon(axe);
        }, "Equipping a non-existent weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testEquipArmor() {
        player.addItem(helmet);
        player.equipArmor(helmet);
        assertTrue(player.getEquippedArmor().contains(helmet), "Equipped armor should contain the helmet.");
        assertTrue(player.getEquipmentManager().getInventory().contains(helmet), "Inventory should contain the helmet after equipping.");
    }

    @Test
    public void testUnequipArmor() {
        player.addItem(helmet);
        player.equipArmor(helmet);
        player.unequipArmor(helmet);
        assertFalse(player.getEquippedArmor().contains(helmet), "Equipped armor should not contain the helmet after unequipping.");
        assertTrue(player.getEquipmentManager().getInventory().contains(helmet), "Inventory should contain the helmet after unequipping.");
    }

    @Test
    public void testEquipNullWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.equipWeapon(null);
        }, "Equipping a null weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testEquipNullArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.equipArmor(null);
        }, "Equipping a null armor should throw an IllegalArgumentException.");
    }

    @Test
    public void testUnequipNullArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.unequipArmor(null);
        }, "Unequipping a null armor should throw an IllegalArgumentException.");
    }

    @Test
    public void testUnequipNonEquippedArmor() {
        player.addItem(helmet);
        assertThrows(IllegalArgumentException.class, () -> {
            player.unequipArmor(helmet);
        }, "Unequipping an armor that is not equipped should throw an IllegalArgumentException.");
    }

    @Test
    public void testGetEquippedArmor() {
        player.addItem(helmet);
        player.equipArmor(helmet);
        List<Armor> equippedArmor = player.getEquippedArmor();
        assertEquals(1, equippedArmor.size(), "Equipped armor list should contain one item.");
        assertEquals(helmet, equippedArmor.get(0), "Equipped armor should contain the helmet.");
    }
}