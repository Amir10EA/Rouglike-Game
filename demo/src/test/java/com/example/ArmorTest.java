package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class ArmorTest {

    private static final double DEFAULT_WEIGHT = 2.0;
    private static final int DEFAULT_DURABILITY = 100;
    private static final int DEFAULT_PHYSICAL_DEFENSE = 10;
    private static final int DEFAULT_MAGICAL_DEFENSE = 5;
    private static final Cost DEFAULT_COST = new Cost(200.0, List.of(
        new Item("Chainmail", 10, ItemType.ARMOR_STONE),
        new Item("Leather", 5, ItemType.ARMOR_STONE)
    ));
    private static final ArmorType DEFAULT_TYPE = ArmorType.CHESTPLATE;
    private static final int MAX_DEFENSE = 100;

    private Armor createDefaultArmor(String name, int physicalDefense, int magicalDefense) {
        return new Armor(name, DEFAULT_WEIGHT, DEFAULT_DURABILITY, physicalDefense, magicalDefense, DEFAULT_TYPE,
                DEFAULT_COST);
    }

    @Test
    public void testAllArmorTypesCreation() {
        for (ArmorType type : ArmorType.values()) {
            Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_PHYSICAL_DEFENSE,
                    DEFAULT_MAGICAL_DEFENSE, type, DEFAULT_COST);

            assertNotNull(armor);
            assertEquals("TestArmor", armor.getName());
            assertEquals(DEFAULT_WEIGHT, armor.getWeight());
            assertEquals(DEFAULT_DURABILITY, armor.getDurability());
            assertEquals(DEFAULT_PHYSICAL_DEFENSE, armor.getPhysicalDefense());
            assertEquals(DEFAULT_MAGICAL_DEFENSE, armor.getMagicalDefense());
            assertEquals(type, armor.getArmorType());
        }
    }

    @Test
    public void testNegativePhysicalDefense() {
        int negativePhysicalDefense = -10;
        int validMagicalDefense = 5;

        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("Invalid Armor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, negativePhysicalDefense, validMagicalDefense,
                    ArmorType.HELMET, DEFAULT_COST);
        });
    }

    @Test
    public void testNegativeMagicalDefense() {
        int validPhysicalDefense = 10;
        int negativeMagicalDefense = -5;

        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("Invalid Armor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, validPhysicalDefense, negativeMagicalDefense,
                    ArmorType.HELMET, DEFAULT_COST);
        });
    }

    @Test
    public void testDetermineRarity() {
        int commonDefense = 5;
        int uncommonDefense = 10;
        int rareDefense = 20;
        int epicDefense = 30;
        int legendaryDefense = 40;
        int durability = 50;

        Armor commonArmor = new Armor("Common Armor", DEFAULT_WEIGHT, durability, commonDefense, commonDefense,
                ArmorType.HELMET, DEFAULT_COST);
        Armor uncommonArmor = new Armor("Uncommon Armor", DEFAULT_WEIGHT, durability, uncommonDefense, uncommonDefense,
                ArmorType.HELMET, DEFAULT_COST);
        Armor rareArmor = new Armor("Rare Armor", DEFAULT_WEIGHT, durability, rareDefense, rareDefense,
                ArmorType.HELMET, DEFAULT_COST);
        Armor epicArmor = new Armor("Epic Armor", DEFAULT_WEIGHT, durability, epicDefense, epicDefense,
                ArmorType.HELMET, DEFAULT_COST);
        Armor legendaryArmor = new Armor("Legendary Armor", DEFAULT_WEIGHT, durability, legendaryDefense,
                legendaryDefense, ArmorType.HELMET, DEFAULT_COST);

        assertEquals(Equipment.Rarity.COMMON, commonArmor.determineRarity());
        assertEquals(Equipment.Rarity.UNCOMMON, uncommonArmor.determineRarity());
        assertEquals(Equipment.Rarity.RARE, rareArmor.determineRarity());
        assertEquals(Equipment.Rarity.EPIC, epicArmor.determineRarity());
        assertEquals(Equipment.Rarity.LEGENDARY, legendaryArmor.determineRarity());
    }

    @Test
    public void testCalculateTotalProtection() {
        int expectedTotalProtection = DEFAULT_PHYSICAL_DEFENSE + DEFAULT_MAGICAL_DEFENSE;

        Armor armor = createDefaultArmor("TestArmor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        assertEquals(expectedTotalProtection, armor.calculateTotalProtection());
    }

    @Test
    public void testCalculateDamageTaken() {
        int incomingPhysicalDamage = 25;
        int incomingMagicalDamage = 15;

        int expectedDamageTaken = (int) (incomingPhysicalDamage / (1 + DEFAULT_PHYSICAL_DEFENSE / 100.0))
                + (int) (incomingMagicalDamage / (1 + DEFAULT_MAGICAL_DEFENSE / 100.0));

        Armor armor = createDefaultArmor("TestArmor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        assertEquals(expectedDamageTaken, armor.calculateDamageTaken(incomingPhysicalDamage, incomingMagicalDamage));
    }

    @Test
    public void testCalculateDamageTakenWithZeroDamage() {
        int zeroDamage = 0;

        Armor armor = createDefaultArmor("TestArmor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        assertEquals(zeroDamage, armor.calculateDamageTaken(zeroDamage, zeroDamage));
    }

    @Test
    public void testUse() {
        int initialDurability = DEFAULT_DURABILITY;
        int expectedDurabilityAfterUse = initialDurability - 1;

        Armor armor = createDefaultArmor("TestArmor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        armor.use();
        assertEquals(expectedDurabilityAfterUse, armor.getDurability());
        assertFalse(armor.isBroken());

        for (int i = 0; i < initialDurability - 1; i++) {
            armor.use();
        }
        assertTrue(armor.isBroken());
        assertEquals("The armor is broken!", armor.use());
    }

    @Test
    public void testUpgradeIncreasesDefense() {
        Armor armor = createDefaultArmor("Upgradeable Armor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 10, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 100.0);
        assertEquals(DEFAULT_PHYSICAL_DEFENSE + 5, armor.getPhysicalDefense(),
                "Physical defense should increase by 5 after upgrade");
        assertEquals(DEFAULT_MAGICAL_DEFENSE + 5, armor.getMagicalDefense(),
                "Magical defense should increase by 5 after upgrade");
    }

    @Test
    public void testUpgradeJustBelowMaxPhysicalDefense() {
        int initialPhysicalDefense = MAX_DEFENSE - 5;
        Armor armor = createDefaultArmor("High Defense Armor", initialPhysicalDefense, DEFAULT_MAGICAL_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 20, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 200.0);
        assertEquals(MAX_DEFENSE, armor.getPhysicalDefense(),
                "Physical defense should not exceed max limit after upgrade");
    }

    @Test
    public void testUpgradeJustBelowMaxMagicalDefense() {
        int initialMagicalDefense = MAX_DEFENSE - 5;
        Armor armor = createDefaultArmor("High Defense Armor", DEFAULT_PHYSICAL_DEFENSE, initialMagicalDefense);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 20, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 200.0);
        assertEquals(MAX_DEFENSE, armor.getMagicalDefense(),
                "Magical defense should not exceed max limit after upgrade");
    }

    @Test
    public void testUpgradeWhenPhysicalDefenseMaxButNotMagicalDefense() {
        int initialPhysicalDefense = MAX_DEFENSE;
        Armor armor = createDefaultArmor("Mixed Max Armor", initialPhysicalDefense, DEFAULT_MAGICAL_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 10, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 100.0);
        assertEquals(initialPhysicalDefense, armor.getPhysicalDefense(), "Physical defense should remain at max limit");
        assertEquals(DEFAULT_MAGICAL_DEFENSE + 5, armor.getMagicalDefense(), "Magical defense should increase by 5");
    }

    @Test
    public void testUpgradeWhenMagicalDefenseMaxButNotPhysicalDefense() {
        int initialMagicalDefense = MAX_DEFENSE;
        Armor armor = createDefaultArmor("Mixed Max Armor", DEFAULT_PHYSICAL_DEFENSE, initialMagicalDefense);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 10, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 100.0);
        assertEquals(MAX_DEFENSE, armor.getMagicalDefense(), "Magical defense should remain at max limit");
        assertEquals(DEFAULT_PHYSICAL_DEFENSE + 5, armor.getPhysicalDefense(), "Physical defense should increase by 5");
    }

    @Test
    public void testUpgradeNotPossibleWhenMaxed() {
        Armor armor = createDefaultArmor("Maxed Out Armor", MAX_DEFENSE, MAX_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 10, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 100.0);
        assertEquals(MAX_DEFENSE, armor.getPhysicalDefense(), "Physical defense should remain at max limit");
        assertEquals(MAX_DEFENSE, armor.getMagicalDefense(), "Magical defense should remain at max limit");
    }

    @Test
    public void testUpgradeDoesNotChangeRarityWhenProtectionRemainsInSameRange() {
        Armor armor = createDefaultArmor("Upgradeable Armor", 20, 10);
        assertEquals(Equipment.Rarity.UNCOMMON, armor.getRarity());
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 2, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 100.0);
        assertEquals(21, armor.getPhysicalDefense(), "Physical defense should increase by 1 after upgrade");
        assertEquals(11, armor.getMagicalDefense(), "Magical defense should increase by 1 after upgrade");
        assertEquals(Equipment.Rarity.UNCOMMON, armor.getRarity(),
                "Rarity should remain UNCOMMON based on new protection");
    }

    @Test
    public void testUpgradeIncreasesBothDefensesAndUpdatesRarity() {
        Armor armor = createDefaultArmor("Upgradeable Armor", 20, 10);
        assertEquals(Equipment.Rarity.UNCOMMON, armor.getRarity(),
                "Rarity should update to EPIC based on new protection");
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 60, ItemType.ARMOR_STONE));
        armor.upgrade(upgradeItems, 600.0);

        assertEquals(50, armor.getPhysicalDefense(), "Physical defense should increase by 30 after upgrade");
        assertEquals(40, armor.getMagicalDefense(), "Magical defense should increase by 30 after upgrade");

        assertEquals(Equipment.Rarity.LEGENDARY, armor.getRarity(),
                "Rarity should update to LEGENDARY based on new protection");
    }

    @Test
    public void testUpgradeWithNegativeMoney() {
        Armor armor = createDefaultArmor("Upgradeable Armor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 10, ItemType.ARMOR_STONE));
        assertThrows(IllegalArgumentException.class, () -> {
            armor.upgrade(upgradeItems, -100.0);
        });
    }

    @Test
    public void testUpgradeWithNegativeStones() {
        Armor armor = createDefaultArmor("Upgradeable Armor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        final int NEGATIVE_STONE_AMOUNT = -10;
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", NEGATIVE_STONE_AMOUNT, ItemType.ARMOR_STONE));
        assertThrows(IllegalArgumentException.class, () -> {
            armor.upgrade(upgradeItems, 100.0);
        });
    }

    @Test
    public void testUpgradeWithNotEnoughStones() {
        Armor armor = createDefaultArmor("Upgradeable Armor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 0, ItemType.ARMOR_STONE));
        assertThrows(IllegalArgumentException.class, () -> {
            armor.upgrade(upgradeItems, 100.0);
        });
    }

    @Test
    public void testUpgradeWithNotEnoughMoney() {
        Armor armor = createDefaultArmor("Upgradeable Armor", DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE);
        List<Item> upgradeItems = new ArrayList<>();
        upgradeItems.add(new Item("Armor Stone", 10, ItemType.ARMOR_STONE));
        assertThrows(IllegalArgumentException.class, () -> {
            armor.upgrade(upgradeItems, 0);
        });
    }
}