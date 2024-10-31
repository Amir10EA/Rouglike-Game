package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WeaponTest {

    private static final int DEFAULT_DAMAGE = 50;
    private static final double DEFAULT_ATTACK_SPEED = 1.5;
    private static final int DEFAULT_DURABILITY = 100;
    private static final double DEFAULT_WEIGHT = 10.0;
    private static final WeaponType PHYSICAL_TYPE = WeaponType.SWORD;
    private static final WeaponType MAGICAL_TYPE = WeaponType.STAFF;
    private static final int NEGATIVE_VALUE = -10;
    private static final double ZERO_ATTACK_SPEED = 0.0;
    private static final double MINIMUM_POSITIVE_ATTACK_SPEED = 0.1;

    private static final int MAX_DAMAGE = 200;
    private static final double MAX_ATTACK_SPEED = 3.0;

    private static final int EXPECTED_DPS = (int) (DEFAULT_DAMAGE * DEFAULT_ATTACK_SPEED);

    private static final Cost DEFAULT_COST = new Cost(100, List.of(
        new Item("Iron Ingot", 3, ItemType.SMITHING_STONE),
        new Item("Wood", 1, ItemType.SMITHING_STONE)
    ));

    @Test
    public void testAllWeaponTypesCreation() {
        for (WeaponType type : WeaponType.values()) {
            Weapon weapon = new Weapon("TestWeapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                    DEFAULT_ATTACK_SPEED, type, DEFAULT_COST);

            assertNotNull(weapon);
            assertEquals("TestWeapon", weapon.getName());
            assertEquals(DEFAULT_WEIGHT, weapon.getWeight());
            assertEquals(DEFAULT_DURABILITY, weapon.getDurability());
            assertEquals(DEFAULT_DAMAGE, weapon.getDamage());
            assertEquals(DEFAULT_ATTACK_SPEED, weapon.getAttackSpeed());
            assertEquals(type, weapon.getType());
            assertEquals(type.getDamageType(), weapon.getType().getDamageType());
        }
    }

    private Weapon createPhysicalWeapon() {
        return new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                WeaponType.SWORD, DEFAULT_COST);
    }

    private Weapon createMagicalWeapon() {
        return new Weapon("Staff of Fire", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, DEFAULT_ATTACK_SPEED,
                WeaponType.STAFF, DEFAULT_COST);
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
        final int REPAIR_AMOUNT = 20;
        physicalWeapon.repair(REPAIR_AMOUNT);
        assertEquals(DEFAULT_DURABILITY + REPAIR_AMOUNT, physicalWeapon.getDurability(),
                "Repair should not exceed initial durability");
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
    public void testUseWhenWeaponIsBroken() {
        Weapon physicalWeapon = createPhysicalWeapon();

        while (!physicalWeapon.isBroken()) {
            physicalWeapon.reduceDurability(10);
        }
        assertTrue(physicalWeapon.isBroken(), "Weapon should be broken");
        assertEquals("The weapon is broken!", physicalWeapon.use());
    }
    @Test
    public void testCalculateDamage() {
        Weapon weapon = createPhysicalWeapon();
        assertEquals(EXPECTED_DPS, weapon.calculateDamage(),
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
    public void testNegativeDamage() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, NEGATIVE_VALUE, DEFAULT_ATTACK_SPEED,
                    PHYSICAL_TYPE, DEFAULT_COST);
        });
    }

    @Test
    public void testNegativeAttackSpeed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, NEGATIVE_VALUE,
                    PHYSICAL_TYPE, DEFAULT_COST);
        });
    }

    @Test
    public void testMinimumPositiveAttackSpeed() {
        Weapon weapon = new Weapon("Excalibur", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                MINIMUM_POSITIVE_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        assertEquals(MINIMUM_POSITIVE_ATTACK_SPEED, weapon.getAttackSpeed(),
                "Weapon attack speed should be the minimum positive value");
    }

    @Test
    public void testZeroAttackSpeed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Invalid Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, ZERO_ATTACK_SPEED,
                    PHYSICAL_TYPE, DEFAULT_COST);
        });
    }

    @Test
    public void testCalculateDPS() {
        Weapon weapon = createPhysicalWeapon();
        double expectedDPS = DEFAULT_DAMAGE * DEFAULT_ATTACK_SPEED;
        assertEquals(expectedDPS, weapon.calculateDPS(), "DPS should be calculated correctly");
    }

    @Test
    public void testDetermineRarity() {
        final double ATTACK_SPEED = 1.0;

        final int COMMON_DAMAGE = 10;
        final int UNCOMMON_DAMAGE = 25;
        final int RARE_DAMAGE = 50;
        final int EPIC_DAMAGE = 75;
        final int LEGENDARY_DAMAGE = 100;

        Weapon commonWeapon = new Weapon("Common Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, COMMON_DAMAGE,
                ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        assertEquals(Equipment.Rarity.COMMON, commonWeapon.getRarity(), "Weapon with low DPS should be COMMON");

        Weapon uncommonWeapon = new Weapon("Uncommon Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, UNCOMMON_DAMAGE,
                ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        assertEquals(Equipment.Rarity.UNCOMMON, uncommonWeapon.getRarity(),
                "Weapon with moderate DPS should be UNCOMMON");

        Weapon rareWeapon = new Weapon("Rare Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, RARE_DAMAGE,
                ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        assertEquals(Equipment.Rarity.RARE, rareWeapon.getRarity(), "Weapon with moderate-high DPS should be RARE");

        Weapon epicWeapon = new Weapon("Epic Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, EPIC_DAMAGE,
                ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        assertEquals(Equipment.Rarity.EPIC, epicWeapon.getRarity(), "Weapon with high DPS should be EPIC");

        Weapon legendaryWeapon = new Weapon("Legendary Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, LEGENDARY_DAMAGE,
                ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        assertEquals(Equipment.Rarity.LEGENDARY, legendaryWeapon.getRarity(),
                "Weapon with very high DPS should be LEGENDARY");
    }

    @Test
    public void testUseWeaponUntilBroken() {
        final int INITIAL_DURABILITY = 5;
        Weapon weapon = new Weapon("Fragile Sword", DEFAULT_WEIGHT, INITIAL_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);

        for (int i = 0; i < INITIAL_DURABILITY; i++) {
            weapon.use();
        }

        assertTrue(weapon.isBroken(), "Weapon should be broken after durability reaches 0");
    }

    @Test
    public void testOverMaxDamage() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Overpowered Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, MAX_DAMAGE + 1, DEFAULT_ATTACK_SPEED,
                    PHYSICAL_TYPE, DEFAULT_COST);
        });
    }

    @Test
    public void testOverMaxAttackSpeed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Weapon("Overpowered Weapon", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE, MAX_ATTACK_SPEED + 0.1,
                    PHYSICAL_TYPE, DEFAULT_COST);
        });
    }

    @Test
    public void testPhysicalWeaponUpgradeWithSmithingStones() {
        Weapon weapon = new Weapon("Physical Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
        weapon.upgrade(upgradeItems, 100.0);
        assertEquals(DEFAULT_DAMAGE + 5, weapon.getDamage(), "Damage should increase by 5 after upgrade");
        assertEquals(DEFAULT_ATTACK_SPEED + 0.5, weapon.getAttackSpeed(),
                "Attack speed should increase by 0.5 after upgrade");
    }

    @Test
    public void testMagicalWeaponUpgradeWithMagicalStones() {
        Weapon weapon = new Weapon("Magical Staff", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, MAGICAL_TYPE, DEFAULT_COST);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Magical Stone", 10, ItemType.MAGICAL_STONE));
        weapon.upgrade(upgradeItems, 100.0);
        assertEquals(DEFAULT_DAMAGE + 5, weapon.getDamage(), "Damage should increase by 5 after upgrade");
        assertEquals(DEFAULT_ATTACK_SPEED + 0.5, weapon.getAttackSpeed(),
                "Attack speed should increase by 0.5 after upgrade");
    }

    @Test
    public void testPhysicalWeaponUpgradeFailsWithMagicalStones() {
        Weapon weapon = new Weapon("Physical Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Magical Stone", 10, ItemType.MAGICAL_STONE));
        assertThrows(IllegalArgumentException.class, () -> {
            weapon.upgrade(upgradeItems, 100.0);
        }, "Upgrading a physical weapon with magical stones should fail");
    }

    @Test
    public void testMagicalWeaponUpgradeFailsWithSmithingStones() {
        Weapon weapon = new Weapon("Magical Staff", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
                DEFAULT_ATTACK_SPEED, MAGICAL_TYPE, DEFAULT_COST);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
        assertThrows(IllegalArgumentException.class, () -> {
            weapon.upgrade(upgradeItems, 100.0);
        }, "Upgrading a magical weapon with smithing stones should fail");
    }
@Test
public void testUpgradeIncreasesDamageAndAttackSpeed() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
            DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 100.0);
    assertEquals(DEFAULT_DAMAGE + 5, weapon.getDamage(), "Damage should increase by 5 after upgrade");
    assertEquals(DEFAULT_ATTACK_SPEED + 0.5, weapon.getAttackSpeed(),
            "Attack speed should increase by 0.5 after upgrade");
}

@Test
public void testUpgradeJustBelowMaxDamage() {
    int initialDamage = MAX_DAMAGE - 5;
    Weapon weapon = new Weapon("High Damage Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, initialDamage,
            DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 20, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 200.0);
    assertEquals(MAX_DAMAGE, weapon.getDamage(), "Damage should not exceed max limit after upgrade");
}

@Test
public void testUpgradeJustBelowMaxAttackSpeed() {
    double initialAttackSpeed = MAX_ATTACK_SPEED - 0.1;
    Weapon weapon = new Weapon("High Speed Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
            initialAttackSpeed, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 20, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 200.0);
    assertEquals(MAX_ATTACK_SPEED, weapon.getAttackSpeed(),
            "Attack speed should not exceed max limit after upgrade");
}

@Test
public void testUpgradeWhenAttackSpeedMaxButNotDamage() {
    int initialDamage = DEFAULT_DAMAGE;
    double initialAttackSpeed = MAX_ATTACK_SPEED;
    Weapon weapon = new Weapon("Mixed Max Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, initialDamage,
            initialAttackSpeed, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 100.0);
    assertEquals(initialDamage + 5, weapon.getDamage(), "Damage should increase by 5");
    assertEquals(MAX_ATTACK_SPEED, weapon.getAttackSpeed(), "Attack speed should remain at max limit");
}

@Test
public void testUpgradeWhenDamageMaxButNotAttackSpeed() {
    int initialDamage = MAX_DAMAGE;
    double initialAttackSpeed = DEFAULT_ATTACK_SPEED;
    Weapon weapon = new Weapon("Mixed Max Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, initialDamage,
            initialAttackSpeed, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 100.0);
    assertEquals(MAX_DAMAGE, weapon.getDamage(), "Damage should remain at max limit");
    assertEquals(initialAttackSpeed + 0.5, weapon.getAttackSpeed(), "Attack speed should increase by 0.5");
}

@Test
public void testUpgradeNotPossibleWhenMaxed() {
    Weapon weapon = new Weapon("Maxed Out Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, MAX_DAMAGE, MAX_ATTACK_SPEED,
    PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 100.0);
    assertEquals(MAX_DAMAGE, weapon.getDamage(), "Damage should remain at max limit");
    assertEquals(MAX_ATTACK_SPEED, weapon.getAttackSpeed(), "Attack speed should remain at max limit");
}

@Test
public void testUpgradeDoesNotChangeRarityWhenDPSRemainsInSameRange() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, 20, 1.0, PHYSICAL_TYPE,
            DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 100.0);
    assertEquals(25, weapon.getDamage(), "Damage should increase by 5 after upgrade");
    assertEquals(1.5, weapon.getAttackSpeed(), "Attack speed should increase by 0.5 after upgrade");
    assertEquals(Equipment.Rarity.RARE, weapon.getRarity(), "Rarity should remain RARE based on new DPS");
}

@Test
public void testUpgradeIncreasesBothDamageAndAttackSpeedAndUpdatesRarity() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, 10, 1.0, PHYSICAL_TYPE,
            DEFAULT_COST);
    assertEquals(Equipment.Rarity.COMMON, weapon.getRarity());
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 600.0);
    assertEquals(15, weapon.getDamage(), "Damage should increase by 5 after upgrade");
    assertEquals(1.5, weapon.getAttackSpeed(), "Attack speed should increase by 0.5 after upgrade");
    assertEquals(Equipment.Rarity.UNCOMMON, weapon.getRarity());
}

@Test
public void testSuccessfulUpgrade() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
            DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    weapon.upgrade(upgradeItems, 100.0);
    assertEquals(DEFAULT_DAMAGE + 5, weapon.getDamage(), "Damage should increase by 5 after upgrade");
    assertEquals(DEFAULT_ATTACK_SPEED + 0.5, weapon.getAttackSpeed(),
            "Attack speed should increase by 0.5 after upgrade");
}

@Test
public void testUpgradeWithNegativeMoney() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
            DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    assertThrows(IllegalArgumentException.class, () -> {
        weapon.upgrade(upgradeItems, -100.0);
    });
}

@Test
public void testUpgradeWithNotEnoughStones() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
            DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 0, ItemType.SMITHING_STONE));
    assertThrows(IllegalArgumentException.class, () -> {
        weapon.upgrade(upgradeItems, 100.0);
    });
}

@Test
public void testUpgradeWithNotEnoughMoney() {
    Weapon weapon = new Weapon("Upgradeable Sword", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_DAMAGE,
            DEFAULT_ATTACK_SPEED, PHYSICAL_TYPE, DEFAULT_COST);
    List<Item> upgradeItems = new ArrayList<>();
    upgradeItems.add(new Item("Smithing Stone", 10, ItemType.SMITHING_STONE));
    assertThrows(IllegalArgumentException.class, () -> {
        weapon.upgrade(upgradeItems, 5.0);
    });
}
}
