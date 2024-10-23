package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
            equipmentManager.switchWeapon(axe);  // Axe not in inventory
        });
    }


    

    

}
