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
    public void testSetMaxHealth() {
        character.setMaxHealth(120);
        assertEquals(100, character.getMaxHealth()); // Max health should not exceed 100
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
        character.takeDamage(100);
        assertEquals(0, character.getHealth()); // Health should not be negative
    }

    @Test
    public void testHeal() {
        character.takeDamage(50);
        character.heal(30);
        assertEquals(80, character.getHealth());
        character.heal(50);
        assertEquals(100, character.getHealth()); // Health should not exceed maxHealth
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
