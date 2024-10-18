package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class WeaponTest {

    private static final int DEFAULT_DAMAGE = 50;
    private static final double DEFAULT_ATTACK_SPEED = 1.5;
    private static final int DEFAULT_DURABILITY = 100;
    private static final double DEFAULT_WEIGHT = 10.0;
    private static final Weapon.WeaponType DEFAULT_TYPE = Weapon.WeaponType.SWORD;
    private static final Equipment.Rarity DEFAULT_RARITY = Equipment.Rarity.COMMON;

    private static final int NEGATIVE_VALUE = -10;
    private static final double NEGATIVE_WEIGHT = -10.0;
    private static final double ZERO_ATTACK_SPEED = 0.0;
    private static final double MINIMUM_POSITIVE_ATTACK_SPEED = 0.1;
    private static final int MAXIMUM_DURABILITY = Integer.MAX_VALUE;

    private static final int EXPECTED_DPS = (int) (DEFAULT_DAMAGE * DEFAULT_ATTACK_SPEED);

    @Test
    public void testAllWeaponTypesCreation() {
        for (Weapon.WeaponType type : Weapon.WeaponType.values()) {
            Weapon weapon = new Weapon("TestWeapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED, type);

            assertNotNull(weapon);
            assertEquals("TestWeapon", weapon.getName());
            assertEquals(DEFAULT_WEIGHT, weapon.getWeight());
            assertEquals(DEFAULT_DURABILITY, weapon.getDurability());
            assertEquals(DEFAULT_DAMAGE, weapon.getDamage());
            assertEquals(DEFAULT_ATTACK_SPEED, weapon.getAttackSpeed());
            assertEquals(type, weapon.getType());
        }
    }

    
    
    private Weapon createWeapon() {
        return new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE);
    }

    private Weapon createMagicalWeapon() {
        return new Weapon("Staff of Fire", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                Weapon.WeaponType.STAFF);
    }

    @Test
    public void testInitialDurabilityAndBrokenState() {
        Weapon physicalWeapon = createWeapon();
        assertFalse(physicalWeapon.isBroken(), "Physical weapon should not be broken initially");
        assertEquals(DEFAULT_DURABILITY, physicalWeapon.getDurability(),
                "Physical weapon durability should be " + DEFAULT_DURABILITY);
    }

    @Test
    public void testRepairDurabilityWithoutExceedingInitialValue() {
        Weapon physicalWeapon = createWeapon();
        final int REPAIR_AMMOUNT = 20;
        physicalWeapon.repair(REPAIR_AMMOUNT);
        assertEquals(DEFAULT_DURABILITY + REPAIR_AMMOUNT, physicalWeapon.getDurability(), "Repair should not exceed initial durability");
    }

    @Test
    public void testIncreaseDurabilityAfterRepair() {
        Weapon physicalWeapon = createWeapon();
        physicalWeapon.repair(10);
        assertEquals(DEFAULT_DURABILITY + 10, physicalWeapon.getDurability(),
                "Durability should increase correctly after repair");
    }

    @Test
    public void testDurabilityReductionAndBrokenState() {
        Weapon physicalWeapon = createWeapon();
        for (int i = 0; i < 10; i++) {
            physicalWeapon.reduceDurability(10);
        }
        assertTrue(physicalWeapon.isBroken(), "Physical weapon should be broken after durability reaches 0");
    }

    @Test
    public void testWeaponUse() {
        Weapon physicalWeapon = createWeapon();
        Weapon magicalWeapon = createMagicalWeapon();
        assertEquals("You use the Excalibur!", physicalWeapon.use(),
                "Using the weapon should return the correct message.");
        assertEquals("You use the Staff of Fire!", magicalWeapon.use(),
                "Using the weapon should return the correct message.");
    }

    @Test
    public void testCalculateDamage() {
        Weapon weapon = createWeapon();
        assertEquals(EXPECTED_DPS, weapon.calculateDamage(),
                "Calculated damage should consider attack speed and durability.");
    }

    @Test
    public void testUseReducesDurability() {
        Weapon physicalWeapon = createWeapon();
        int initialDurability = physicalWeapon.getDurability();
        physicalWeapon.use();
        assertEquals(initialDurability - 1, physicalWeapon.getDurability(),
                "Using the weapon should reduce durability by 1.");
    }

    @Test
    public void testInvalidDamage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, NEGATIVE_VALUE, DEFAULT_ATTACK_SPEED,
                    DEFAULT_TYPE);
        });
        assertEquals("Damage cannot be negative", exception.getMessage());
    }

    @Test
    public void testInvalidAttackSpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, NEGATIVE_VALUE,
                    DEFAULT_TYPE);
        });
        assertEquals("Attack speed must be positive", exception.getMessage());
    }

    @Test
    public void testMinimumPositiveAttackSpeed() {
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                MINIMUM_POSITIVE_ATTACK_SPEED, DEFAULT_TYPE);
        assertEquals(MINIMUM_POSITIVE_ATTACK_SPEED, weapon.getAttackSpeed(),
                "Weapon attack speed should be the minimum positive value");
    }

    @Test
    public void testZeroAttackSpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, ZERO_ATTACK_SPEED,
                    DEFAULT_TYPE);
        });
        assertEquals("Attack speed must be positive", exception.getMessage());
    }

    @Test
    public void testCalculateDPS() {
        Weapon weapon = createWeapon();
        double expectedDPS = DEFAULT_DAMAGE * DEFAULT_ATTACK_SPEED;
        assertEquals(expectedDPS, weapon.calculateDPS(), "DPS should be calculated correctly");
    }

    @Test
public void testDetermineRarity() {
    final double ATTACK_SPEED = 1.0;

    final int COMMON_DAMAGE = 10; // DPS = 10
    final int UNCOMMON_DAMAGE = 25; // DPS = 25
    final int RARE_DAMAGE = 50; // DPS = 50
    final int EPIC_DAMAGE = 75; // DPS = 60
    final int LEGENDARY_DAMAGE = 100; // DPS = 100

    Weapon commonWeapon = new Weapon("Common Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, COMMON_DAMAGE,
            ATTACK_SPEED, DEFAULT_TYPE);
    assertEquals(Equipment.Rarity.COMMON, commonWeapon.getRarity(), "Weapon with low DPS should be COMMON");

    Weapon uncommonWeapon = new Weapon("Uncommon Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, UNCOMMON_DAMAGE,
            ATTACK_SPEED, DEFAULT_TYPE);
    assertEquals(Equipment.Rarity.UNCOMMON, uncommonWeapon.getRarity(),
            "Weapon with moderate DPS should be UNCOMMON");

    Weapon rareWeapon = new Weapon("Rare Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, RARE_DAMAGE,
            ATTACK_SPEED, DEFAULT_TYPE);
    assertEquals(Equipment.Rarity.RARE, rareWeapon.getRarity(), "Weapon with moderate-high DPS should be RARE");

    Weapon epicWeapon = new Weapon("Epic Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, EPIC_DAMAGE,
            ATTACK_SPEED, DEFAULT_TYPE);
    assertEquals(Equipment.Rarity.EPIC, epicWeapon.getRarity(), "Weapon with high DPS should be EPIC");

    Weapon legendaryWeapon = new Weapon("Legendary Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, LEGENDARY_DAMAGE,
            ATTACK_SPEED, DEFAULT_TYPE);
    assertEquals(Equipment.Rarity.LEGENDARY, legendaryWeapon.getRarity(),
            "Weapon with very high DPS should be LEGENDARY");
}

    @Test
    public void testUseWeaponUntilBroken() {
        final int INITIAL_DURABILITY = 5;
        Weapon weapon = new Weapon("Fragile Sword", DEFAULT_WEIGHT, INITIAL_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, DEFAULT_TYPE);

        for (int i = 0; i < INITIAL_DURABILITY; i++) {
            weapon.use();
        }

        assertTrue(weapon.isBroken(), "Weapon should be broken after being used until durability reaches 0");
        assertEquals(0, weapon.getDurability(), "Weapon durability should be 0 after being used until broken");
    }
}