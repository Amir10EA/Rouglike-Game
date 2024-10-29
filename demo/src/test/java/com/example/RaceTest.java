package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RaceTest {

    private static final int HUMAN_STRENGTH = 2;
    private static final int ELF_STRENGTH = 1;
    private static final int GOBLIN_STRENGTH = 3;
    private static final int HUMAN_SPEED = 1;
    private static final int ELF_SPEED = 2;
    private static final int GOBLIN_SPEED = 1;
    private static final int HUMAN_INTELLIGENCE = 2;
    private static final int ELF_INTELLIGENCE = 3;
    private static final int GOBLIN_INTELLIGENCE = 1;
    private static final int RACE_VALUES_LENGTH = 3;

    @Test
    public void testRaceConstansExists(){
        assertNotNull(Race.valueOf("HUMAN"), "HUMAN should exist in the Race enum.");
        assertNotNull(Race.valueOf("ELF"), "ELF should exist in the Race enum.");
        assertNotNull(Race.valueOf("GOBLIN"), "GOBLIN should exist in the Race enum.");
    }

    @Test
    public void testHumanRaceAttributes(){
        Race human = Race.HUMAN;
        assertAll(
            () -> assertEquals(HUMAN_STRENGTH, human.getStrength(), "Human strength should be 2"),
            () -> assertEquals(HUMAN_SPEED, human.getSpeed(), "Human speed should be 1"),
            () -> assertEquals(HUMAN_INTELLIGENCE, human.getIntelligence(), "Human intelligence should be 2")
        );
    }

    @Test
    public void testElfRaceAttributes(){
        Race elf = Race.ELF;
        assertAll(
            () -> assertEquals(ELF_STRENGTH, elf.getStrength(), "Elf strength should be 1"),
            () -> assertEquals(ELF_SPEED, elf.getSpeed(), "Elf speed should be 2"),
            () -> assertEquals(ELF_INTELLIGENCE, elf.getIntelligence(), "Elf intelligence should be 3")
        );
    }

    @Test
    public void testGoblinRaceAttributes(){
        Race goblin = Race.GOBLIN;
        assertAll(
            () -> assertEquals(GOBLIN_STRENGTH, goblin.getStrength(), "Goblin strength should be 3"),
            () -> assertEquals(GOBLIN_SPEED, goblin.getSpeed(), "Goblin speed should be 1"),
            () -> assertEquals(GOBLIN_INTELLIGENCE, goblin.getIntelligence(), "Goblin intelligence should be 1")
        );
    }

    @Test
    public void testRaceEnumSize(){
        assertEquals(RACE_VALUES_LENGTH, Race.values().length, "Race enum should have 3 constans.");
    }

    @Test
    public void testEnumConstans(){
        Race[] raceConstans = {
            Race.HUMAN,
            Race.ELF,
            Race.GOBLIN
        };

        assertArrayEquals(raceConstans, Race.values(), "Race enum does not have the expected constants.");
    }

    @Test
    public void testRaceToString() {
        assertEquals("HUMAN(strength=2, speed=1, intelligence=2)", Race.HUMAN.toString());
        assertEquals("ELF(strength=1, speed=2, intelligence=3)", Race.ELF.toString());
        assertEquals("GOBLIN(strength=3, speed=1, intelligence=1)", Race.GOBLIN.toString());
    }

}
