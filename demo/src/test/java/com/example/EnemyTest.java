package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class EnemyTest {

    private Enemy enemy;
    private BaseCharacter target;
    private Race enemyRace;
    private Race targetRace;
    private Weapon sword;
    private Armor shield;

    @BeforeEach
    public void setUp() {
        enemyRace = Race.GOBLIN;
        targetRace = Race.ELF;

        enemy = new Enemy("Goblin", 50, 15, 1, enemyRace, 50);
        target = new BaseCharacter("Hero", 100, 20, 1, targetRace);

        List<Item> swordMaterials = List.of(
            new Item("Iron", 2, ItemType.SMITHING_STONE)
        );
        Cost swordCost = new Cost(10.0, swordMaterials);
        sword = new Weapon("Sword", 15, 100, 20, 1.5, WeaponType.SWORD, swordCost);

        List<Item> shieldMaterials = List.of(
            new Item("Wood", 3, ItemType.ARMOR_STONE)
        );
        Cost shieldCost = new Cost(8.0, shieldMaterials);
        shield = new Armor("Shield", 10, 80, 15, 10, ArmorType.HELMET, shieldCost);
    }

    @Test
    public void testEnemyCreation() {
        assertNotNull(enemy);
        assertEquals("Goblin", enemy.getName());
        assertEquals(50, enemy.getHealth());
        assertEquals(100, enemy.getMaxHealth());
        assertEquals(15, enemy.getStrength());
        assertEquals(1, enemy.getLevel());
        assertEquals(50, enemy.getXp());
        assertNotNull(enemy.getEquipmentManager());
    }

    @Test
    public void testGainXp() {
        enemy.gainXp(30);
        assertEquals(80, enemy.getXp());

        enemy.gainXp(-20);
        assertEquals(80, enemy.getXp(), "XP ska inte minska när negativt värde skickas in.");
    }

    @Test
    public void testAddItem() {
        enemy.addItem(sword);
        assertTrue(enemy.getEquipmentManager().getInventory().contains(sword));
    }

    @Test
    public void testRemoveItem() {
        enemy.addItem(sword);
        enemy.removeItem(sword);
        assertFalse(enemy.getEquipmentManager().getInventory().contains(sword));
    }

    @Test
    public void testEquipWeapon() {
        enemy.addItem(sword);
        enemy.equipWeapon(sword);
        assertEquals(sword, enemy.getActiveWeapon());
    }

    @Test
    public void testUnequipWeapon() {
        enemy.addItem(sword);
        enemy.equipWeapon(sword);
        enemy.unequipWeapon();
        assertNull(enemy.getActiveWeapon());
    }

    @Test
    public void testEquipArmor() {
        enemy.addItem(shield);
        enemy.equipArmor(shield);
        assertTrue(enemy.getEquipmentManager().getEquippedArmor().contains(shield));
    }

    @Test
    public void testUnequipArmor() {
        enemy.addItem(shield);
        enemy.equipArmor(shield);
        enemy.unequipArmor(shield);
        assertFalse(enemy.getEquipmentManager().getEquippedArmor().contains(shield));
    }

    @Test
    public void testAttack() {
        int initialHealth = target.getHealth();
        enemy.attack(target);
        assertTrue(target.getHealth() < initialHealth, "Målets hälsa ska minska efter attack.");
    }

    @Test
    public void testAttackWhenDead() {
        enemy.takeDamage(100); // Dödar fienden
        assertFalse(enemy.isAlive());
        int initialHealth = target.getHealth();
        enemy.attack(target);
        assertEquals(initialHealth, target.getHealth(), "Målets hälsa ska ej förändras om fienden är död.");
    }

    @Test
    public void testDefend() {
        enemy.defend();
        // Eftersom defend() bara skriver ut en text, behöver vi inte göra några asserts här.
    }

    @Test
    public void testDecideAction_AboveThreshold() {
        enemy.gainXp(60); // XP är nu 110
        int initialHealth = target.getHealth();
        enemy.decideAction(target);
        assertTrue(target.getHealth() < initialHealth, "Fienden ska attackera när XP >= 100.");
    }

    @Test
    public void testDecideAction_BelowThreshold() {
        enemy.gainXp(-enemy.getXp()); // Sätter XP till 0
        enemy.decideAction(target);
        // Fienden ska försvara sig; ingen förändring i målets hälsa.
    }

    @Test
    public void testAddNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.addItem(null);
        }, "Att lägga till null ska kasta IllegalArgumentException.");
    }

    @Test
    public void testRemoveNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.removeItem(null);
        }, "Att ta bort null ska kasta IllegalArgumentException.");
    }

    @Test
    public void testEquipNullWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.equipWeapon(null);
        }, "Att utrusta null vapen ska kasta IllegalArgumentException.");
    }

    @Test
    public void testEquipNonExistentWeapon() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.equipWeapon(sword);
        }, "Att utrusta ett vapen som inte finns i inventariet ska kasta IllegalArgumentException.");
    }

    @Test
    public void testEquipNullArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.equipArmor(null);
        }, "Att utrusta null rustning ska kasta IllegalArgumentException.");
    }

    @Test
    public void testEquipNonExistentArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.equipArmor(shield);
        }, "Att utrusta en rustning som inte finns i inventariet ska kasta IllegalArgumentException.");
    }

    @Test
    public void testUnequipNullArmor() {
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.unequipArmor(null);
        }, "Att avutrusta null rustning ska kasta IllegalArgumentException.");
    }

    @Test
    public void testUnequipNonEquippedArmor() {
        enemy.addItem(shield);
        assertThrows(IllegalArgumentException.class, () -> {
            enemy.unequipArmor(shield);
        }, "Att avutrusta en rustning som inte är utrustad ska kasta IllegalArgumentException.");
    }
}