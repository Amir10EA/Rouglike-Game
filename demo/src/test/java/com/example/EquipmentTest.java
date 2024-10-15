package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.Equipment.Rarity;

public class EquipmentTest {
    private static final double DEFAULT_WEIGHT = 5.0;
    private static final int DEFAULT_DURABILITY = 100;
    private static final int TEN_HP_DURABILITY = 10;
    private static final int DEFAULT_DAMAGE = 10;
    private static final double DEFAULT_ATTACK_SPEED = 1.5;
    private static final String DEFAULT_TYPE = "Sword";
    private static final Rarity DEFAULT_RARITY = Rarity.COMMON;
    private static final double NEGATIVE_WEIGHT = -1.0;
    private static final int NEGATIVE_VALUE = -1;
    private static final int MAXIMUM_DURABILITY = 1000;


    @Test
    public void testInvalidDurability() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, NEGATIVE_VALUE, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    DEFAULT_TYPE, DEFAULT_RARITY);
        });
        assertEquals("Durability cannot be negative", exception.getMessage());
    }

    @Test
    public void testMaximumDurability() {
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, MAXIMUM_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, DEFAULT_TYPE, Rarity.EPIC);
        assertEquals(MAXIMUM_DURABILITY, weapon.getDurability(), "Weapon durability should be at maximum value");
    }

    @Test
    public void testZeroDurability() {
        final int ZERO_DURABILITY = 0;
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, ZERO_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE, Rarity.EPIC);
        assertTrue(weapon.isBroken(), "Weapon should be broken when durability is zero");
    }

    @Test
    public void testNegativeWeight() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", NEGATIVE_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    DEFAULT_TYPE, DEFAULT_RARITY);
        });
        assertEquals("Weight cannot be negative", exception.getMessage());
    }

    @Test
    public void testZeroWeight() {
        final double ZERO_WEIGHT = 0.0;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", ZERO_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    DEFAULT_TYPE, DEFAULT_RARITY);
        });
        assertEquals("Weight cannot be negative", exception.getMessage());
    }

    @Test
    public void testRepair() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE, DEFAULT_RARITY);
        weapon.reduceDurability(10);
        assertTrue(weapon.isBroken());
        weapon.repair(5);
        assertFalse(weapon.isBroken());
        assertEquals(5, weapon.getDurability());
    }

    @Test
    public void testRepairWithNegativeArgument() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE, DEFAULT_RARITY);
                
                assertThrows(IllegalArgumentException.class, () -> {
                    weapon.repair(NEGATIVE_VALUE);
                });
    }

    @Test
    public void reduceDurabilityWithNegativeArgument() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE, DEFAULT_RARITY);
                
                assertThrows(IllegalArgumentException.class, () -> {
                    weapon.reduceDurability(NEGATIVE_VALUE);
                });
    }

    @Test
    public void testIsBroken() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE, DEFAULT_RARITY);
        weapon.reduceDurability(10);
        assertTrue(weapon.isBroken(), "Weapon should be broken when durability is zero");
    }


    @Test
    public void testReduceDurability() {
        Weapon weapon = new Weapon("Fragile Sword", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, DEFAULT_TYPE, DEFAULT_RARITY);

        for (int i = 0; i < TEN_HP_DURABILITY; i++) {
            weapon.reduceDurability(1);
        }

        assertTrue(weapon.isBroken(), "Weapon should be broken after durability reaches 0");
        assertEquals(0, weapon.getDurability(), "Weapon durability should be 0 after being used until broken");
    }
}