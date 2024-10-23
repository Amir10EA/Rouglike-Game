package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentManagerTest {
    private EquipmentManager equipmentManager;
    private Weapon sword;
    private Weapon axe;
    private Weapon mace;

    @BeforeEach
    public void setUp() {
        equipmentManager = new EquipmentManager();

        Map<String, Integer> swordMaterials = new HashMap<>();
        swordMaterials.put("Iron", 2);
        Cost swordCost = new Cost(10.0, swordMaterials);
        sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);

        Map<String, Integer> axeMaterials = new HashMap<>();
        axeMaterials.put("Steel", 3);
        Cost axeCost = new Cost(15.0, axeMaterials);
        axe = new Weapon("Axe", 20, 80, 25, 1.2, WeaponType.AXE, axeCost);

        Map<String, Integer> maceMaterials = new HashMap<>();
        maceMaterials.put("Iron", 5);
        Cost maceCost = new Cost(20.0, maceMaterials);
        mace = new Weapon("Mace", 25, 90, 30, 1.0, WeaponType.MACE, maceCost);
    }

    @Test
    public void testAddWeapon() {
        equipmentManager.addWeapon(sword);
        assertEquals(1, equipmentManager.getWeapons().size(), "Weapon list should contain one weapon after adding.");
        assertEquals(sword, equipmentManager.getWeapons().get(0), "The added weapon should be the sword.");
    }

    @Test
    public void testAddAxeAndMace() {
        equipmentManager.addWeapon(axe);
        equipmentManager.addWeapon(mace);

        assertEquals(2, equipmentManager.getWeapons().size(), "Weapon list should contain two weapons after adding Axe and Mace.");
        assertEquals(axe, equipmentManager.getWeapons().get(0), "The first weapon should be the Axe.");
        assertEquals(mace, equipmentManager.getWeapons().get(1), "The second weapon should be the Mace.");
    }

    @Test
    public void testAddNullWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.addWeapon(null);
        }, "Adding a null weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testAddDuplicateWeapon() {
        equipmentManager.addWeapon(sword);
        
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.addWeapon(sword);
        }, "Adding the same weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testAddMultipleWeapons() {
        equipmentManager.addWeapon(sword);
        equipmentManager.addWeapon(axe);
        equipmentManager.addWeapon(mace);

        assertEquals(3, equipmentManager.getWeapons().size(), "Weapon list should contain three weapons after adding Sword, Axe, and Mace.");
    }

    @Test
    public void testEquipThenRemoveWeapon() {
        equipmentManager.addWeapon(sword);
        equipmentManager.equipWeapon(sword);
        assertEquals(sword, equipmentManager.getActiveWeapon(), "Active weapon should be sword.");

        equipmentManager.removeWeapon(sword);
        assertNull(equipmentManager.getActiveWeapon(), "Active weapon should be null after removal.");
    }

    @Test
    public void testRemoveLastWeapon() {
        equipmentManager.addWeapon(sword);
        equipmentManager.equipWeapon(sword);
        assertEquals(sword, equipmentManager.getActiveWeapon(), "Active weapon should be the sword.");

        equipmentManager.removeWeapon(sword);
        assertEquals(0, equipmentManager.getWeapons().size(), "Weapon list should be empty after removing the last weapon.");
        assertNull(equipmentManager.getActiveWeapon(), "Active weapon should be null after removing the only weapon.");
    }

    @Test
    public void testSwitchAndRemoveWeaponSimultaneously() {
        equipmentManager.addWeapon(sword);
        equipmentManager.addWeapon(axe);
        equipmentManager.equipWeapon(sword);

        equipmentManager.switchWeapon(axe);
        equipmentManager.removeWeapon(sword);

        assertEquals(axe, equipmentManager.getActiveWeapon(), "Active weapon should be axe after switching.");
        assertFalse(equipmentManager.getWeapons().contains(sword));
    }

    @Test
    public void testRapidSwitchingBetweenWeapons() {
        equipmentManager.addWeapon(sword);
        equipmentManager.addWeapon(axe);
        equipmentManager.equipWeapon(sword);

        for (int i = 0; i < 100; i++) {
            equipmentManager.switchWeapon(axe);
            assertEquals(axe, equipmentManager.getActiveWeapon());
            equipmentManager.switchWeapon(sword);
            assertEquals(sword, equipmentManager.getActiveWeapon());
        }
    }

    @Test
    public void testSwitchToNonExistentWeapon() {
        equipmentManager.addWeapon(sword);
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.switchWeapon(axe);
        });
    }   


    @Test
    public void testRemoveNonExistentWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.removeWeapon(axe);
        }, "Removing a non-existent weapon should throw an IllegalArgumentException.");
    }

    @Test
    public void testEquipNonExistentWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            equipmentManager.equipWeapon(axe);
        }, "Equipping a non-existent weapon should throw an IllegalArgumentException.");
    }

    @Test
public void testAddWeaponsWithSameName() {
    equipmentManager.addWeapon(sword);
    
    Map<String, Integer> duplicateSwordMaterials = new HashMap<>();
    duplicateSwordMaterials.put("Iron", 2);
    Cost duplicateSwordCost = new Cost(10.0, duplicateSwordMaterials);
    Weapon duplicateSword = new Weapon("Sword", 10, 50, 15, 1.0, WeaponType.SWORD, duplicateSwordCost);
    
    assertThrows(IllegalArgumentException.class, () -> {
        equipmentManager.addWeapon(duplicateSword);
    }, "Adding a weapon with the same name should throw an IllegalArgumentException.");
}

    @Test
    public void testSwitchToSameWeapon() {
        equipmentManager.addWeapon(sword);
        equipmentManager.equipWeapon(sword);
        equipmentManager.switchWeapon(sword);
        assertEquals(sword, equipmentManager.getActiveWeapon(), "Switching to the same weapon should not change the active weapon.");
    }

    @Test
    public void testRemoveAllWeaponsInReverseOrder() {
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> materials = new HashMap<>();
            materials.put("Material" + i, i + 1);
            Cost cost = new Cost(10.0, materials);
            Weapon weapon = new Weapon("Weapon" + i, 10 + i, 50 + i, 15 + i, 1.0 + (i % 3), WeaponType.SWORD, cost);
            equipmentManager.addWeapon(weapon);
        }
    
        for (int i = 9; i >= 0; i--) {
            Weapon weapon = equipmentManager.getWeapons().get(i);
            equipmentManager.removeWeapon(weapon);
            assertEquals(i, equipmentManager.getWeapons().size(), "Weapon list should contain " + i + " weapons after removing.");
        }
    }

    @Test
    public void testAddWeaponsWithVaryingAttributes() {
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> materials = new HashMap<>();
            materials.put("Material" + i, i + 1);
            Cost cost = new Cost(10.0, materials);
            Weapon weapon = new Weapon("Weapon" + i, 10 + i, 50 + i, 15 + i, 1.0 + (i % 3), WeaponType.SWORD, cost);
            equipmentManager.addWeapon(weapon);
            assertEquals(i + 1, equipmentManager.getWeapons().size(), "Weapon list should contain " + (i + 1) + " weapons after adding.");
        }
    }

    @Test
    public void testRemoveWeaponsInRandomOrder() {
        List<Weapon> weaponsToAdd = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> materials = new HashMap<>();
            materials.put("Material" + i, i + 1);
            Cost cost = new Cost(10.0, materials);
            Weapon weapon = new Weapon("Weapon" + i, 10 + i, 50 + i, 15 + i, 1.0 + (i % 3), WeaponType.SWORD, cost);
            weaponsToAdd.add(weapon);
            equipmentManager.addWeapon(weapon);
        }

        // Shuffle the list to randomize the order of removal
        Collections.shuffle(weaponsToAdd);

        for (int i = 0; i < weaponsToAdd.size(); i++) {
            Weapon weapon = weaponsToAdd.get(i);
            equipmentManager.removeWeapon(weapon);
            assertEquals(weaponsToAdd.size() - i - 1, equipmentManager.getWeapons().size(), 
                "Weapon list should contain " + (weaponsToAdd.size() - i - 1) + " weapons after removing.");
        }
    }    

}
