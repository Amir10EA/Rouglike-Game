package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class EquipmentManagerTest {
    private EquipmentManager equipmentManager;
    private Weapon sword;
    private Weapon axe;
    private Weapon bow;

    @BeforeEach
    public void setUp() {
        equipmentManager = new EquipmentManager();
        sword = new Weapon("Sword", 15);
        axe = new Weapon("Axe", 20);
        bow = new Weapon("Bow", 10);
    }

    @Test
    public void testAddWeapon() {
        equipmentManager.addWeapon(sword);
        equipmentManager.addWeapon(axe);
        List<Weapon> weapons = equipmentManager.getWeapons();

        assertEquals(2, weapons.size());
        assertTrue(weapons.contains(sword));
        assertTrue(weapons.contains(axe));
    }

}
