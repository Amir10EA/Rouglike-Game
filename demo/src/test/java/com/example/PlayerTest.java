package com.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class PlayerTest {

    private Player player;
    private Race race;
    private Weapon sword;
    private Weapon axe;
    private Weapon mace;

    @BeforeEach
    public void setUp() {
        player = new Player("Hero", 100, 10, race);

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
        player.addWeapon(sword);
        assertEquals(1, player.getWeapons().size());
        assertEquals(sword, player.getWeapons().get(0));
    }

    @Test
    public void testRemoveWeapon() {
        player.addWeapon(sword);
        player.removeWeapon(sword);
        assertEquals(0, player.getWeapons().size());
    }

    @Test
    public void testEquipWeapon() {
        player.addWeapon(sword);
        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());
    }

    @Test
    public void testSwitchWeapon() {
        player.addWeapon(sword);
        player.switchWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());
    }
    
    @Test
    public void testAddMultipleWeaponsWithDifferentNames() {
        player.addWeapon(sword);
        player.addWeapon(axe);
        player.addWeapon(mace);

        assertEquals(3, player.getWeapons().size());
        assertTrue(player.getWeapons().contains(sword));
        assertTrue(player.getWeapons().contains(axe));
        assertTrue(player.getWeapons().contains(mace));
    }

    @Test
    public void testRemoveAllWeapons() {
        player.addWeapon(sword);
        player.addWeapon(axe);
        player.addWeapon(mace);

        player.removeWeapon(sword);
        player.removeWeapon(axe);
        player.removeWeapon(mace);

        assertEquals(0, player.getWeapons().size());
    }

    @Test
    public void testAddAndEquipMultipleWeapons() {
        player.addWeapon(sword);
        player.addWeapon(axe);
        player.addWeapon(mace);

        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());

        player.equipWeapon(axe);
        assertEquals(axe, player.getActiveWeapon());

        player.equipWeapon(mace);
        assertEquals(mace, player.getActiveWeapon());
    }

    @Test
    public void testRemoveActiveWeaponAndEquipAnother() {
        player.addWeapon(sword);
        player.addWeapon(axe);

        player.equipWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());

        player.removeWeapon(sword);
        assertNull(player.getActiveWeapon());

        player.equipWeapon(axe);
        assertEquals(axe, player.getActiveWeapon());
    }


    @Test
    public void testEquipWeaponAlreadyEquipped() {
        player.addWeapon(sword);
        player.equipWeapon(sword);
        player.equipWeapon(sword); // Equip the same weapon again
        assertEquals(sword, player.getActiveWeapon());
    }

    @Test
    public void testSwitchBetweenMultipleWeapons() {
        player.addWeapon(sword);
        player.addWeapon(axe);
        player.addWeapon(mace);

        player.switchWeapon(sword);
        assertEquals(sword, player.getActiveWeapon());

        player.switchWeapon(axe);
        assertEquals(axe, player.getActiveWeapon());

        player.switchWeapon(mace);
        assertEquals(mace, player.getActiveWeapon());
    }


}
