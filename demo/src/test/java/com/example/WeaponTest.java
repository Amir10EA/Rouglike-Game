package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WeaponTest {

    private static final int DEFAULT_DAMAGE = 50;
    private static final double DEFAULT_ATTACK_SPEED = 1.5;
    private static final int DEFAULT_DURABILITY = 100;
    private static final double DEFAULT_WEIGHT = 10.0;
    private static final WeaponType DEFAULT_TYPE = WeaponType.SWORD;
    private static final Rarity DEFAULT_RARITY = Rarity.COMMON;

    private static final int NEGATIVE_VALUE = -10;
    private static final double NEGATIVE_WEIGHT = -10.0;
    private static final double ZERO_ATTACK_SPEED = 0.0;
    private static final double MINIMUM_POSITIVE_ATTACK_SPEED = 0.1;
    private static final int MAXIMUM_DURABILITY = Integer.MAX_VALUE;

    private static final int EXPECTED_DPS = (int) (DEFAULT_DAMAGE * DEFAULT_ATTACK_SPEED);

    private Weapon createPhysicalWeapon() {
        return new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                DEFAULT_TYPE, Rarity.EPIC);
    }

    private Weapon createMagicalWeapon() {
        return new Weapon("Staff of Fire", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                WeaponType.STAFF, Rarity.LEGENDARY);
    }

    @Test
    public void testPhysicalWeaponAttributes() {
        Weapon physicalWeapon = createPhysicalWeapon();
        assertEquals(DEFAULT_DAMAGE, physicalWeapon.getDamage(), "Physical weapon damage should be " + DEFAULT_DAMAGE);
        assertEquals(DEFAULT_ATTACK_SPEED, physicalWeapon.getAttackSpeed(),
                "Physical weapon attack speed should be " + DEFAULT_ATTACK_SPEED);
        assertEquals(DEFAULT_TYPE, physicalWeapon.getType(), "Physical weapon type should be " + DEFAULT_TYPE);
        assertEquals(Rarity.EPIC, physicalWeapon.getRarity(), "Physical weapon rarity should be EPIC");
        assertEquals(DEFAULT_DAMAGE, physicalWeapon.attack(), "Physical weapon attack should return damage value");
    }

    @Test
    public void testMagicalWeaponAttributes() {
        Weapon magicalWeapon = createMagicalWeapon();
        assertEquals(DEFAULT_DAMAGE, magicalWeapon.getDamage(), "Magical weapon damage should be " + DEFAULT_DAMAGE);
        assertEquals(DEFAULT_ATTACK_SPEED, magicalWeapon.getAttackSpeed(),
                "Magical weapon attack speed should be " + DEFAULT_ATTACK_SPEED);
        assertEquals(WeaponType.STAFF, magicalWeapon.getType(), "Magical weapon type should be STAFF");
        assertEquals(Rarity.LEGENDARY, magicalWeapon.getRarity(), "Magical weapon rarity should be LEGENDARY");
        assertEquals(DEFAULT_DAMAGE, magicalWeapon.attack(), "Magical weapon attack should return damage value");
    }

    @Test
    public void testInitialDurabilityAndBrokenState() {
        Weapon physicalWeapon = createPhysicalWeapon();
        assertFalse(physicalWeapon.isBroken(), "Physical weapon should not be broken initially");
        assertEquals(DEFAULT_DURABILITY, physicalWeapon.getDurability(),
                "Physical weapon durability should be " + DEFAULT_DURABILITY);
    }

    @Test
    public void testRepairDurabilityWithoutExceedingInitialValue() {
        Weapon physicalWeapon = createPhysicalWeapon();
        physicalWeapon.repair(20);
        assertEquals(DEFAULT_DURABILITY, physicalWeapon.getDurability(), "Repair should not exceed initial durability");
    }

    @Test
    public void testIncreaseDurabilityAfterRepair() {
        Weapon physicalWeapon = createPhysicalWeapon();
        physicalWeapon.repair(10);
        assertEquals(DEFAULT_DURABILITY + 10, physicalWeapon.getDurability(),
                "Durability should increase correctly after repair");
    }

    @Test
    public void testDurabilityReductionAndBrokenState() {
        Weapon physicalWeapon = createPhysicalWeapon();
        for (int i = 0; i < 10; i++) {
            physicalWeapon.reduceDurability(10);
        }
        assertTrue(physicalWeapon.isBroken(), "Physical weapon should be broken after durability reaches 0");
    }

    @Test
    public void testWeaponUse() {
        Weapon physicalWeapon = createPhysicalWeapon();
        Weapon magicalWeapon = createMagicalWeapon();
        assertEquals("You use the Excalibur!", physicalWeapon.use(),
                "Using the weapon should return the correct message.");
        assertEquals("You use the Staff of Fire!", magicalWeapon.use(),
                "Using the weapon should return the correct message.");
    }

    @Test
    public void testCalculateDamage() {
        Weapon physicalWeapon = createPhysicalWeapon();
        assertEquals(EXPECTED_DPS, physicalWeapon.calculateDamage(),
                "Calculated damage should consider attack speed and durability.");
    }

    @Test
    public void testUseReducesDurability() {
        Weapon physicalWeapon = createPhysicalWeapon();
        int initialDurability = physicalWeapon.getDurability();
        physicalWeapon.use();
        assertEquals(initialDurability - 1, physicalWeapon.getDurability(),
                "Using the weapon should reduce durability by 1.");
    }

    @Test
    public void testInvalidDamage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, NEGATIVE_VALUE, DEFAULT_ATTACK_SPEED,
                    DEFAULT_TYPE, DEFAULT_RARITY);
        });
        assertEquals("Damage cannot be negative", exception.getMessage());
    }

    @Test
    public void testInvalidAttackSpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, NEGATIVE_VALUE,
                    DEFAULT_TYPE, DEFAULT_RARITY);
        });
        assertEquals("Attack speed must be positive", exception.getMessage());
    }

    // @Test
    // public void testMaximumDurability() {
    //     Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, MAXIMUM_DURABILITY, DEFAULT_DAMAGE,
    //             DEFAULT_ATTACK_SPEED, DEFAULT_TYPE, Rarity.EPIC);
    //     assertEquals(MAXIMUM_DURABILITY, weapon.getDurability(), "Weapon durability should be at maximum value");
    // }

    @Test
    public void testMinimumPositiveAttackSpeed() {
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                MINIMUM_POSITIVE_ATTACK_SPEED, DEFAULT_TYPE, Rarity.EPIC);
        assertEquals(MINIMUM_POSITIVE_ATTACK_SPEED, weapon.getAttackSpeed(),
                "Weapon attack speed should be the minimum positive value");
    }

    // @Test
    // public void testZeroDurability() {
    //     final int ZERO_DURABILITY = 0;
    //     Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, ZERO_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
    //             DEFAULT_TYPE, Rarity.EPIC);
    //     assertTrue(weapon.isBroken(), "Weapon should be broken when durability is zero");
    // }

    // @Test
    // public void testInvalidDurability() {
    //     Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    //         new Weapon("Invalid Weapon", DEFAULT_WEIGHT, NEGATIVE_VALUE, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
    //                 DEFAULT_TYPE, DEFAULT_RARITY);
    //     });
    //     assertEquals("Durability cannot be negative", exception.getMessage());
    // }

    @Test
    public void testZeroAttackSpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, ZERO_ATTACK_SPEED,
                    DEFAULT_TYPE, DEFAULT_RARITY);
        });
        assertEquals("Attack speed must be positive", exception.getMessage());
    }

    // @Test
    // public void testNegativeWeight() {
    //     Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    //         new Weapon("Invalid Weapon", NEGATIVE_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
    //                 DEFAULT_TYPE, DEFAULT_RARITY);
    //     });
    //     assertEquals("Weight cannot be negative", exception.getMessage());
    // }

    // @Test
    // public void testZeroWeight() {
    //     Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    //         final double ZERO_WEIGHT = 0.0;
    //         new Weapon("Invalid Weapon", ZERO_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
    //                 DEFAULT_TYPE, DEFAULT_RARITY);
    //     });
    //     assertEquals("Weight cannot be negative", exception.getMessage());
    // }

    @Test
    public void testCalculateDPS() {
        Weapon weapon = createPhysicalWeapon();
        double expectedDPS = DEFAULT_DAMAGE * DEFAULT_ATTACK_SPEED;
        assertEquals(expectedDPS, weapon.calculateDPS(), "DPS should be calculated correctly");
    }

    @Test
    public void testDetermineRarity() {
        final int VERY_LOW_DAMAGE = 10;
        final int LOW_DAMAGE = 20;
        final int HIGH_DAMAGE = 40;
        final int VERY_HIGH_DAMAGE = 100;

        final double LOW_ATTACK_SPEED = 1.0;
        final double HIGH_ATTACK_SPEED = 2.0;

        Weapon commonWeapon = new Weapon("Common Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, VERY_LOW_DAMAGE,
                LOW_ATTACK_SPEED, DEFAULT_TYPE, DEFAULT_RARITY);
        assertEquals(Rarity.COMMON, commonWeapon.determineRarity(), "Weapon with low DPS should be COMMON");

        Weapon uncommonWeapon = new Weapon("Uncommon Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, LOW_DAMAGE,
                HIGH_ATTACK_SPEED, DEFAULT_TYPE, DEFAULT_RARITY);
        assertEquals(Rarity.UNCOMMON, uncommonWeapon.determineRarity(),
                "Weapon with moderate DPS should be UNCOMMON");

        Weapon epicWeapon = new Weapon("Epic Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, HIGH_DAMAGE, HIGH_ATTACK_SPEED,
                DEFAULT_TYPE, DEFAULT_RARITY);
        assertEquals(Rarity.EPIC, epicWeapon.determineRarity(), "Weapon with high DPS should be EPIC");

        Weapon legendaryWeapon = new Weapon("Legendary Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, VERY_HIGH_DAMAGE,
                HIGH_ATTACK_SPEED, DEFAULT_TYPE, DEFAULT_RARITY);
        assertEquals(Rarity.LEGENDARY, legendaryWeapon.determineRarity(),
                "Weapon with very high DPS should be LEGENDARY");
    }

    @Test
    public void testUseWeaponUntilBroken() {
        final int INITIAL_DURABILITY = 5;
        Weapon weapon = new Weapon("Fragile Sword", TEST_DEFAULT_WEIGHT, INITIAL_DURABILITY, TEST_DEFAULT_DAMAGE,
                TEST_DEFAULT_ATTACK_SPEED, TEST_DEFAULT_TYPE, TEST_DEFAULT_RARITY);

        for (int i = 0; i < INITIAL_DURABILITY; i++) {
            weapon.use();
        }

        assertTrue(weapon.isBroken(), "Weapon should be broken after being used until durability reaches 0");
        assertEquals(0, weapon.getDurability(), "Weapon durability should be 0 after being used until broken");
    }


}