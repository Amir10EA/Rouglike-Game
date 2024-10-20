package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArmorTest {

    private static final double DEFAULT_WEIGHT = 2.0;
    private static final int DEFAULT_DURABILITY = 100;
    private static final int DEFAULT_PHYSICAL_DEFENSE = 10;
    private static final int DEFAULT_MAGICAL_DEFENSE = 5;

    @Test
    public void testAllArmorTypesCreation() {
        for (ArmorType type : ArmorType.values()) {
            Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE, type);

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
            new Armor("Invalid Armor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, negativePhysicalDefense, validMagicalDefense, ArmorType.HELMET);
        });
    }

    @Test
    public void testNegativeMagicalDefense() {
        int validPhysicalDefense = 10;
        int negativeMagicalDefense = -5;

        assertThrows(IllegalArgumentException.class, () -> {
            new Armor("Invalid Armor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, validPhysicalDefense, negativeMagicalDefense, ArmorType.HELMET);
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

        Armor commonArmor = new Armor("Common Armor", DEFAULT_WEIGHT, durability, commonDefense, commonDefense, ArmorType.HELMET);
        Armor uncommonArmor = new Armor("Uncommon Armor", DEFAULT_WEIGHT, durability, uncommonDefense, uncommonDefense, ArmorType.HELMET);
        Armor rareArmor = new Armor("Rare Armor", DEFAULT_WEIGHT, durability, rareDefense, rareDefense, ArmorType.HELMET);
        Armor epicArmor = new Armor("Epic Armor", DEFAULT_WEIGHT, durability, epicDefense, epicDefense, ArmorType.HELMET);
        Armor legendaryArmor = new Armor("Legendary Armor", DEFAULT_WEIGHT, durability, legendaryDefense, legendaryDefense, ArmorType.HELMET);

        assertEquals(Equipment.Rarity.COMMON, commonArmor.determineRarity());
        assertEquals(Equipment.Rarity.UNCOMMON, uncommonArmor.determineRarity());
        assertEquals(Equipment.Rarity.RARE, rareArmor.determineRarity());
        assertEquals(Equipment.Rarity.EPIC, epicArmor.determineRarity());
        assertEquals(Equipment.Rarity.LEGENDARY, legendaryArmor.determineRarity());
    }

    @Test
    public void testCalculateTotalProtection() {
        int expectedTotalProtection = DEFAULT_PHYSICAL_DEFENSE + DEFAULT_MAGICAL_DEFENSE;

        Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE, ArmorType.HELMET);
        assertEquals(expectedTotalProtection, armor.calculateTotalProtection());
    }

    @Test
    public void testCalculateDamageTaken() {
        int incomingPhysicalDamage = 25;
        int incomingMagicalDamage = 15;

        int expectedDamageTaken = (int) (incomingPhysicalDamage / (1 + DEFAULT_PHYSICAL_DEFENSE / 100.0)) + (int) (incomingMagicalDamage / (1 + DEFAULT_MAGICAL_DEFENSE / 100.0));

        Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE, ArmorType.HELMET);
        assertEquals(expectedDamageTaken, armor.calculateDamageTaken(incomingPhysicalDamage, incomingMagicalDamage));
    }

    @Test
    public void testCalculateDamageTakenWithZeroDamage() {
        int zeroDamage = 0;

        Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE, ArmorType.HELMET);
        assertEquals(zeroDamage, armor.calculateDamageTaken(zeroDamage, zeroDamage));
    }

    @Test
    public void testUpgrade() {
        int additionalPhysicalDefense = 5;
        int additionalMagicalDefense = 3;

        Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, DEFAULT_DURABILITY, DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE, ArmorType.HELMET);
        armor.upgrade(additionalPhysicalDefense, additionalMagicalDefense);

        assertEquals(DEFAULT_PHYSICAL_DEFENSE + additionalPhysicalDefense, armor.getPhysicalDefense());
        assertEquals(DEFAULT_MAGICAL_DEFENSE + additionalMagicalDefense, armor.getMagicalDefense());
    }

    @Test
    public void testUse() {
        int initialDurability = DEFAULT_DURABILITY;
        int expectedDurabilityAfterUse = initialDurability - 1;

        Armor armor = new Armor("TestArmor", DEFAULT_WEIGHT, initialDurability, DEFAULT_PHYSICAL_DEFENSE, DEFAULT_MAGICAL_DEFENSE, ArmorType.HELMET);
        armor.use();
        assertEquals(expectedDurabilityAfterUse, armor.getDurability());
        assertFalse(armor.isBroken());

        for (int i = 0; i < initialDurability - 1; i++) {
            armor.use();
        }
        assertTrue(armor.isBroken());
        assertEquals("The armor is broken!", armor.use());
    }
}