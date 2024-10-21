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
    
}
