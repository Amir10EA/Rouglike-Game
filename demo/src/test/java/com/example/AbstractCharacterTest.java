package com.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractCharacterTest {
    protected Character character;

    protected abstract Character createCharacter(String name, int health, int strength);

    @BeforeEach
    public void setUp() {
        character = createCharacter("TestCharacter", 100, 10);
    }

    @Test
    public void testInitialAttributes() {
        assertEquals("TestCharacter", character.getName());
        assertEquals(100, character.getHealth());
        assertEquals(100, character.getMaxHealth());
        assertEquals(10, character.getStrength());
        assertEquals(1.0, character.getDamageMultiplier(), 0.01);
    }

}
