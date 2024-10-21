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

    
}
