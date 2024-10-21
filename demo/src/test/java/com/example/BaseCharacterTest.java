package com.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaseCharacterTest {
    private BaseCharacter character;

    @BeforeEach
    public void setUp() {
        character = new BaseCharacter("Hero", 100, 10);
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
        assertEquals(0, character.getHealth()); // Dead characters should not heal
    }

    @Test
    public void testAttack() {
        BaseCharacter enemy = new BaseCharacter("Enemy", 100, 5);
        character.attack(enemy);
        assertEquals(90, enemy.getHealth());
    }

    @Test
    public void testIsAlive() {
        assertTrue(character.isAlive());
        character.takeDamage(100);
        assertFalse(character.isAlive());
    }

    
}
