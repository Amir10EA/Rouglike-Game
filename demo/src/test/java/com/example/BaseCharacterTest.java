package com.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaseCharacterTest {
    private BaseCharacter character;
    private Race race;

    @BeforeEach
    public void setUp() {
        character = new BaseCharacter("Hero", 100, 10, 1,race);
    }

    @Test
    public void testCharacterCreation() {
        assertNotNull(character);
        assertEquals("Hero", character.getName());
        assertEquals(100, character.getHealth());
        assertEquals(100, character.getMaxHealth());
        assertEquals(10, character.getStrength());
    } 

    @Test
    public void testInitializeWithBoundaryHealthValues() {
        BaseCharacter characterAtZeroHealth = new BaseCharacter("ZeroHealth", 0, 10, 1, race);
        assertEquals(0, characterAtZeroHealth.getHealth());
        assertFalse(characterAtZeroHealth.isAlive());

        BaseCharacter characterAtMaxHealth = new BaseCharacter("MaxHealth", 100, 10, 1, race);
        assertEquals(100, characterAtMaxHealth.getHealth());
        assertTrue(characterAtMaxHealth.isAlive());
    }

    @Test
    public void testInitializeWithNegativeHealth() {
        BaseCharacter characterWithNegativeHealth = new BaseCharacter("NegativeHealth", -10, 10, 1,race);
        assertEquals(0, characterWithNegativeHealth.getHealth());
        assertFalse(characterWithNegativeHealth.isAlive());
    }

    @Test
    public void testSetHealth() {
        character.setHealth(80);
        assertEquals(80, character.getHealth());
        character.setHealth(-10);
        assertEquals(0, character.getHealth()); 
    }

    @Test
    public void testSetMaxHealth() {
        character.setMaxHealth(120);
        assertEquals(100, character.getMaxHealth()); 
    }

    @Test
    public void testSetStrength() {
        character.setStrength(15);
        assertEquals(15, character.getStrength());
    }

    @Test
    public void testCalculateDamage() {
        character.setStrength(20);
        assertEquals(20, character.calculateDamage());
    }

    @Test
    public void testTakeDamage() {
        character.takeDamage(30);
        assertEquals(70, character.getHealth());
    }

    @Test
    public void testTakeDamageMoreThanHealth() {
        character.takeDamage(150);
        assertEquals(0, character.getHealth()); 
    }

    @Test
    public void testHeal() {
        character.takeDamage(50);
        character.heal(30);
        assertEquals(80, character.getHealth());
    }

    @Test
    public void testHealBeyondMaxHealth() {
        character.takeDamage(10);
        character.heal(20);
        assertEquals(100, character.getHealth()); 
    }

    @Test
    public void testHealOnlyWhenAlive() {
        character.takeDamage(100);
        assertFalse(character.isAlive());
        character.heal(20);
        assertEquals(0, character.getHealth()); 
    }

    @Test
    public void testAttack() {
        BaseCharacter enemy = new BaseCharacter("Enemy", 100, 5, 1,race);
        character.attack(enemy);
        assertEquals(90, enemy.getHealth());
    }

    @Test
    public void testIsAlive() {
        assertTrue(character.isAlive());
        character.takeDamage(100);
        assertFalse(character.isAlive());
    }

    @Test
    public void testCharacterDiesWhenHealthReachesZero() {
        character.takeDamage(100);
        assertFalse(character.isAlive());
    }

    @Test
    public void testAttackWithZeroStrength() {
        character.setStrength(0);
        BaseCharacter enemy = new BaseCharacter("Enemy", 100, 5, 1, race);
        character.attack(enemy);
        assertEquals(100, enemy.getHealth());
    }


    @Test
    public void testDeadCharacterCannotAttack() {
        character.takeDamage(100);
        assertFalse(character.isAlive());
        BaseCharacter enemy = new BaseCharacter("Enemy", 100, 5, 1,race);
        character.attack(enemy);
        assertEquals(100, enemy.getHealth()); // Dead characters should not attack
    }

    @Test
    public void testEnemyDiesWhenHealthReachesZero() {
        BaseCharacter enemy = new BaseCharacter("Enemy", 100, 5, 1, race);
        character.attack(enemy);
        enemy.takeDamage(95);
        assertFalse(enemy.isAlive());
    }

    @Test
    public void testNullChecks() {
        assertThrows(NullPointerException.class, () -> {
            new BaseCharacter(null, 100, 10, 1, race);
        });
    }

    @Test
    public void testLevelUp() {
        character.setLevel(5);
        assertEquals(5, character.getLevel());
    }

    @Test
    public void testRaceAssignment() {
        Race newRace = Race.ELF; 
        character.setRace(newRace);
        assertEquals(newRace, character.getRace());
    }

    @Test
    public void testSetHealthBeyondBoundaries() {
        character.setHealth(150);
        assertEquals(100, character.getHealth());
        character.setHealth(-50);
        assertEquals(0, character.getHealth()); 
    }

    @Test
    public void testSetNegativeStrength() {
        character.setStrength(-10);
        assertEquals(-10, character.getStrength());
    }

}
