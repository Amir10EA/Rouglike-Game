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
}
