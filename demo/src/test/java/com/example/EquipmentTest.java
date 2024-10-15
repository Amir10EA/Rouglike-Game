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
    private static final double NEGATIVE_WEIGHT = -1.0;
    private static final int NEGATIVE_VALUE = -1;
    private static final int MAXIMUM_DURABILITY = Integer.MAX_VALUE;

    @Test
    public void testValidEquipmentCreation() {
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);
        assertEquals("Excalibur", weapon.getName());
        assertEquals(DEFAULT_WEIGHT, weapon.getWeight());
        assertEquals(DEFAULT_DURABILITY, weapon.getDurability());
        assertEquals(DEFAULT_DAMAGE, weapon.getDamage());
        assertEquals(DEFAULT_ATTACK_SPEED, weapon.getAttackSpeed());
        assertEquals(Weapon.WeaponType.SWORD, weapon.getType());
        assertEquals(Rarity.UNCOMMON, weapon.getRarity()); // Assuming default rarity based on DPS
        assertFalse(weapon.isBroken());
    }

    @Test
    public void testNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon(null, DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    Weapon.WeaponType.SWORD);
        });
    }

    @Test
    public void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    Weapon.WeaponType.SWORD);
        });
    }

    @Test
    public void testRepair() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);
        weapon.reduceDurability(10);
        assertTrue(weapon.isBroken());
        weapon.repair(5);
        assertFalse(weapon.isBroken());
        assertEquals(5, weapon.getDurability());
    }

    @Test
    public void testInvalidDurability() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, NEGATIVE_VALUE, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    Weapon.WeaponType.SWORD);
        });
    }

    @Test
    public void testMaximumDurability() {
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, MAXIMUM_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);
        assertEquals(MAXIMUM_DURABILITY, weapon.getDurability(), "Weapon durability should be at maximum value");
    }

    @Test
    public void testZeroDurability() {
        final int ZERO_DURABILITY = 0;
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, ZERO_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                Weapon.WeaponType.SWORD);
        assertTrue(weapon.isBroken(), "Weapon should be broken when durability is zero");
    }

    @Test
    public void testNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", NEGATIVE_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    Weapon.WeaponType.SWORD);
        });
    }

    @Test
    public void testZeroWeight() {
        final double ZERO_WEIGHT = 0.0;
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", ZERO_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                    Weapon.WeaponType.SWORD);
        });
    }

    @Test
    public void testRepairWithNegativeArgument() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);

        assertThrows(IllegalArgumentException.class, () -> {
            weapon.repair(NEGATIVE_VALUE);
        });
    }

    @Test
    public void reduceDurabilityWithNegativeArgument() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);

        assertThrows(IllegalArgumentException.class, () -> {
            weapon.reduceDurability(NEGATIVE_VALUE);
        });
    }

    @Test
    public void testIsBroken() {
        Weapon weapon = new Weapon("Test Weapon", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);
        weapon.reduceDurability(10);
        assertTrue(weapon.isBroken(), "Weapon should be broken when durability is zero");
    }

    @Test
    public void testReduceDurability() {
        Weapon weapon = new Weapon("Fragile Sword", DEFAULT_WEIGHT, TEN_HP_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, Weapon.WeaponType.SWORD);

        for (int i = 0; i < TEN_HP_DURABILITY; i++) {
            weapon.reduceDurability(1);
        }

        assertTrue(weapon.isBroken(), "Weapon should be broken after durability reaches 0");
        assertEquals(0, weapon.getDurability(), "Weapon durability should be 0 after being used until broken");
    }
}