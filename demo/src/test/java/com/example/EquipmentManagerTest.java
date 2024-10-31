package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipmentManagerTest {
    private EquipmentManager equipmentManager;
    private Weapon sword;
    private Weapon axe;
    private Armor helmet;
    private Armor chestplate;
    private Armor leggings;
    private Armor boots;

    @BeforeEach
    public void setUp() {
        equipmentManager = new EquipmentManager();

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
        new Weapon("Mace", 25, 90, 30, 1.0, WeaponType.MACE, maceCost);

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

        List<Item> leggingsMaterials = List.of(
            new Item("Iron", 3, ItemType.ARMOR_STONE)
        );
        Cost leggingsCost = new Cost(15.0, leggingsMaterials);
        leggings = new Armor("Leggings", 10, 80, 20, 10, ArmorType.LEGGINGS, leggingsCost);

        List<Item> bootsMaterials = List.of(
            new Item("Leather", 2, ItemType.ARMOR_STONE)
        );
        Cost bootsCost = new Cost(10.0, bootsMaterials);
        boots = new Armor("Boots", 5, 60, 15, 5, ArmorType.BOOTS, bootsCost);
    }

    @Test
    public void testAddWeapon() {
        equipmentManager.addItem(sword);
        assertEquals(1, equipmentManager.getInventory().size(), "Inventory should contain one item after adding.");
        assertEquals(sword, equipmentManager.getInventory().get(0), "The added item should be the sword.");
    }

    @Test
    public void testAddArmor() {
        equipmentManager.addItem(helmet);
        assertEquals(1, equipmentManager.getInventory().size(), "Inventory should contain one item after adding.");
        assertEquals(helmet, equipmentManager.getInventory().get(0), "The added item should be the helmet.");
    }

    @Test
    public void testAddMultipleItems() {
        equipmentManager.addItem(sword);
        equipmentManager.addItem(helmet);
        assertEquals(2, equipmentManager.getInventory().size(), "Inventory should contain two items after adding sword and helmet.");
    }

    @Test
    public void testEquipWeapon() {
        equipmentManager.addItem(sword);
        equipmentManager.equipWeapon(sword);
        assertEquals(sword, equipmentManager.getActiveWeapon(), "Active weapon should be sword.");
    }

    @Test
    public void testEquipArmor() {
        equipmentManager.addItem(chestplate);
        equipmentManager.equipArmor(chestplate);
        assertTrue(equipmentManager.getEquippedArmor().contains(chestplate), "Equipped armor should contain chestplate.");
    }

    @Test
    public void testEquipArmorReplacesExisting() {
        equipmentManager.addItem(helmet);

        List<Item> advancedHelmetMaterials = List.of(
            new Item("Steel", 3, ItemType.ARMOR_STONE)
        );
        Cost advancedHelmetCost = new Cost(15.0, advancedHelmetMaterials);
        Armor advancedHelmet = new Armor("Advanced Helmet", 6, 60, 15, 10, ArmorType.HELMET, advancedHelmetCost);

        equipmentManager.addItem(advancedHelmet);

        equipmentManager.equipArmor(helmet);
        equipmentManager.equipArmor(advancedHelmet);

        assertFalse(equipmentManager.getEquippedArmor().contains(helmet), "Equipped armor should not contain the old helmet after replacing.");
        assertTrue(equipmentManager.getEquippedArmor().contains(advancedHelmet), "Equipped armor should contain the new helmet.");
        assertEquals(1, equipmentManager.getEquippedArmor().size(), "Equipped armor should contain exactly 1 item of type HELMET.");
    }

    @Test
    public void testEquipMultipleArmorTypes() {
        equipmentManager.addItem(helmet);
        equipmentManager.addItem(chestplate);
        equipmentManager.addItem(leggings);
        equipmentManager.addItem(boots);

        equipmentManager.equipArmor(helmet);
        equipmentManager.equipArmor(chestplate);
        equipmentManager.equipArmor(leggings);
        equipmentManager.equipArmor(boots);

        assertTrue(equipmentManager.getEquippedArmor().contains(helmet), "Equipped armor should contain helmet.");
        assertTrue(equipmentManager.getEquippedArmor().contains(chestplate), "Equipped armor should contain chestplate.");
        assertTrue(equipmentManager.getEquippedArmor().contains(leggings), "Equipped armor should contain leggings.");
        assertTrue(equipmentManager.getEquippedArmor().contains(boots), "Equipped armor should contain boots.");
        assertEquals(4, equipmentManager.getEquippedArmor().size(), "Equipped armor should contain exactly 4 items.");
    }

    @Test
    public void testRemoveWeapon() {
        equipmentManager.addItem(sword);
        equipmentManager.removeItem(sword);
        assertEquals(0, equipmentManager.getInventory().size(), "Inventory should be empty after removing the sword.");
    }

    @Test
    public void testRemoveArmor() {
        equipmentManager.addItem(helmet);
        equipmentManager.removeItem(helmet);
        assertEquals(0, equipmentManager.getInventory().size(), "Inventory should be empty after removing the helmet.");
    }

    @Test
    public void testEquipThenRemoveWeapon() {
        equipmentManager.addItem(sword);
        equipmentManager.equipWeapon(sword);
        assertEquals(sword, equipmentManager.getActiveWeapon(), "Active weapon should be sword.");

        equipmentManager.removeItem(sword);
        assertNull(equipmentManager.getActiveWeapon(), "Active weapon should be null after removal.");
    }

    @Test
    public void testEquipThenRemoveArmor() {
        equipmentManager.addItem(chestplate);
        equipmentManager.equipArmor(chestplate);
        assertTrue(equipmentManager.getEquippedArmor().contains(chestplate), "Equipped armor should contain chestplate.");

        equipmentManager.removeItem(chestplate);
        assertFalse(equipmentManager.getEquippedArmor().contains(chestplate), "Equipped armor should not contain chestplate after removal.");
    }

    @Test
    public void testSwitchWeapon() {
        equipmentManager.addItem(sword);
        equipmentManager.addItem(axe);
        equipmentManager.equipWeapon(sword);
        equipmentManager.equipWeapon(axe);
        assertEquals(axe, equipmentManager.getActiveWeapon(), "Active weapon should be axe after switching.");
    }

    @Test
    public void testSwitchToNonExistentWeapon() {
        equipmentManager.addItem(sword);
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.equipWeapon(axe);
        });
    }

    @Test
    public void testRemoveNonExistentItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.removeItem(axe);
        }, "Removing a non-existent item should throw an IllegalArgumentException.");
    }

    @Test
    public void testEquipNonExistentWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.equipWeapon(axe);
        }, "Equipping a non-existent weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testEquipNonExistentArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.equipArmor(chestplate);
        }, "Equipping a non-existent armor should throw an IllegalArgumentException.");
    }

    @Test
    public void testAddItemsWithSameName() {
        equipmentManager.addItem(sword);

        List<Item> duplicateSwordMaterials = List.of(
            new Item("Iron", 2, ItemType.SMITHING_STONE)
        );
        Cost duplicateSwordCost = new Cost(10.0, duplicateSwordMaterials);
        Weapon duplicateSword = new Weapon("Sword", 10, 50, 15, 1.0, WeaponType.SWORD, duplicateSwordCost);

        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.addItem(duplicateSword);
        }, "Adding an item with the same name should throw an IllegalArgumentException.");
    }

    @Test
    public void testSwitchToSameWeapon() {
        equipmentManager.addItem(sword);
        equipmentManager.equipWeapon(sword);
        equipmentManager.equipWeapon(sword);
        assertEquals(sword, equipmentManager.getActiveWeapon(), "Switching to the same weapon should not change the active weapon.");
    }

    @Test
    public void testRemoveAllItemsInReverseOrder() {
        for (int i = 0; i < 10; i++) {
            List<Item> materials = List.of(
                new Item("Material" + i, i + 1, ItemType.SMITHING_STONE)
            );
            Cost cost = new Cost(10.0, materials);
            Weapon weapon = new Weapon("Weapon" + i, 10 + i, 50 + i, 15 + i, 1.0 + (i % 3), WeaponType.SWORD, cost);
            equipmentManager.addItem(weapon);
        }
        for (int i = 9; i >= 0; i--) {
            Weapon weapon = (Weapon) equipmentManager.getInventory().get(i);
            equipmentManager.removeItem(weapon);
            assertEquals(i, equipmentManager.getInventory().size(), "Inventory should contain " + i + " items after removing.");
        }
    }

    @Test
    public void testAddItemsWithVaryingAttributes() {
        for (int i = 0; i < 10; i++) {
            List<Item> materials = List.of(
                new Item("Material" + i, i + 1, ItemType.SMITHING_STONE)
            );
            Cost cost = new Cost(10.0, materials);
            Weapon weapon = new Weapon("Weapon" + i, 10 + i, 50 + i, 15 + i, 1.0 + (i % 3), WeaponType.SWORD, cost);
            equipmentManager.addItem(weapon);
            assertEquals(i + 1, equipmentManager.getInventory().size(), "Inventory should contain " + (i + 1) + " items after adding.");
        }
    }

    @Test
    public void testRemoveItemsInRandomOrder() {
        List<Weapon> weaponsToAdd = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Item> materials = List.of(
                new Item("Material" + i, i + 1, ItemType.SMITHING_STONE)
            );
            Cost cost = new Cost(10.0, materials);
            Weapon weapon = new Weapon("Weapon" + i, 10 + i, 50 + i, 15 + i, 1.0 + (i % 3), WeaponType.SWORD, cost);
            weaponsToAdd.add(weapon);
            equipmentManager.addItem(weapon);
        }

        Collections.shuffle(weaponsToAdd);

        for (int i = 0; i < weaponsToAdd.size(); i++) {
            Weapon weapon = weaponsToAdd.get(i);
            equipmentManager.removeItem(weapon);
            assertEquals(weaponsToAdd.size() - i - 1, equipmentManager.getInventory().size(), 
                "Inventory should contain " + (weaponsToAdd.size() - i - 1) + " items after removing.");
        }
    }

    @Test
    public void testUnequipWeapon() {
        equipmentManager.addItem(sword);
        equipmentManager.equipWeapon(sword);
        equipmentManager.unequipWeapon();
        assertNull(equipmentManager.getActiveWeapon(), "Active weapon should be null after unequipping.");
        assertTrue(equipmentManager.getInventory().contains(sword), "Inventory should contain the sword after unequipping.");
    }
    
    @Test
    public void testUnequipArmor() {
        equipmentManager.addItem(chestplate);
        equipmentManager.equipArmor(chestplate);
        equipmentManager.unequipArmor(chestplate);
        assertFalse(equipmentManager.getEquippedArmor().contains(chestplate), "Equipped armor should not contain chestplate after unequipping.");
        assertTrue(equipmentManager.getInventory().contains(chestplate), "Inventory should contain the chestplate after unequipping.");
    }

    @Test
    public void testEquipNullWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.equipWeapon(null);
        }, "Equipping a null weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testEquipNullArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.equipArmor(null);
        }, "Equipping a null armor should throw an IllegalArgumentException.");
    }

    @Test
    public void testUnequipNullArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.unequipArmor(null);
        }, "Unequipping a null armor should throw an IllegalArgumentException.");
    }

    @Test
    public void testUnequipNonEquippedArmor() {
        equipmentManager.addItem(chestplate);
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.unequipArmor(chestplate);
        }, "Unequipping an armor that is not equipped should throw an IllegalArgumentException.");
    }

    @Test
    public void testGetEquippedArmor() {
        equipmentManager.addItem(helmet);
        equipmentManager.equipArmor(helmet);
        List<Armor> equippedArmor = equipmentManager.getEquippedArmor();
        assertEquals(1, equippedArmor.size(), "Equipped armor list should contain one item.");
        assertEquals(helmet, equippedArmor.get(0), "Equipped armor should contain the helmet.");
    }

    @Test
    public void testAddNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.addItem(null);
        }, "Adding a null item should throw an IllegalArgumentException.");
    }
    
    @Test
    public void testRemoveNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.removeItem(null);
        }, "Removing a null item should throw an IllegalArgumentException.");
    }
    
}