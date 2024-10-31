package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AlchemistTest {

    @Test
    public void testAlchemyAndPotionSkillsElfLevel1() {
        Alchemist alchemist = new Alchemist("Alchemist Elf", 100, 10, 1, Race.ELF);
        int expectedAlchemySkill = 6;
        int expectedPotionCraftingSkill = 7;

        assertEquals(expectedAlchemySkill, alchemist.getAlchemySkill(), "Alchemy skill should be 6 for level 1 ELF");
        assertEquals(expectedPotionCraftingSkill, alchemist.getPotionCraftingSkill(), "Potion crafting skill should be 7 for level 1 ELF");
    }

    @Test
    public void testAlchemyAndPotionSkillsGoblinLevel1() {
        Alchemist alchemist = new Alchemist("Alchemist Goblin", 100, 10, 1, Race.GOBLIN);
        int expectedAlchemySkill = 4;
        int expectedPotionCraftingSkill = 5;

        assertEquals(expectedAlchemySkill, alchemist.getAlchemySkill(), "Alchemy skill should be 4 for level 1 GOBLIN");
        assertEquals(expectedPotionCraftingSkill, alchemist.getPotionCraftingSkill(), "Potion crafting skill should be 5 for level 1 GOBLIN");
    }

    @Test
    public void testAlchemyAndPotionSkillsHumanLevel1() {
        Alchemist alchemist = new Alchemist("Alchemist Human", 100, 10, 1, Race.HUMAN);
        int expectedAlchemySkill = 5;
        int expectedPotionCraftingSkill = 6;

        assertEquals(expectedAlchemySkill, alchemist.getAlchemySkill(), "Alchemy skill should be 5 for level 1 HUMAN");
        assertEquals(expectedPotionCraftingSkill, alchemist.getPotionCraftingSkill(), "Potion crafting skill should be 6 for level 1 HUMAN");
    }

    @Test
    public void testBombThrowingSkillElfLevel1() {
        Alchemist alchemist = new Alchemist("Alchemist Elf", 100, 10, 1, Race.ELF);
        int expectedBombThrowingSkill = 3;

        assertEquals(expectedBombThrowingSkill, alchemist.getBombThrowingSkill(), "Bomb throwing skill should be 3 for level 1 ELF");
    }

    @Test
    public void testBombThrowingSkillGoblinLevel1() {
        Alchemist alchemist = new Alchemist("Alchemist Goblin", 100, 10, 1, Race.GOBLIN);
        int expectedBombThrowingSkill = 5;

        assertEquals(expectedBombThrowingSkill, alchemist.getBombThrowingSkill(), "Bomb throwing skill should be 5 for level 1 GOBLIN");
    }

    @Test
    public void testBombThrowingSkillHumanLevel1() {
        Alchemist alchemist = new Alchemist("Alchemist Human", 100, 10, 1, Race.HUMAN);
        int expectedBombThrowingSkill = 4;

        assertEquals(expectedBombThrowingSkill, alchemist.getBombThrowingSkill(), "Bomb throwing skill should be 4 for level 1 HUMAN");
    }

    @Test
    public void testLevelUpElf() {
        Alchemist alchemist = new Alchemist("Alchemist Elf", 100, 10, 1, Race.ELF);
        alchemist.setLevel(2);

        int expectedBombThrowingSkill = 5;
        int expectedAlchemySkill = 9;
        int expectedPotionCraftingSkill = 11;

        assertAll(
            () -> assertEquals(expectedAlchemySkill, alchemist.getAlchemySkill(), "Alchemy skill should be 9 for level 2 ELF"),
            () -> assertEquals(expectedPotionCraftingSkill, alchemist.getPotionCraftingSkill(), "Potion crafting skill should be 11 for level 2 ELF"),
            () -> assertEquals(expectedBombThrowingSkill, alchemist.getBombThrowingSkill(), "Bomb throwing skill should be 5 for level 2 ELF")
        );
    }

    @Test
    public void testLevelUpGoblin() {
        Alchemist alchemist = new Alchemist("Alchemist Goblin", 100, 10, 1, Race.GOBLIN);
        alchemist.setLevel(2);

        int expectedBombThrowingSkill = 7;
        int expectedAlchemySkill = 7;
        int expectedPotionCraftingSkill = 9;

        assertAll(
            () -> assertEquals(expectedAlchemySkill, alchemist.getAlchemySkill(), "Alchemy skill should be 7 for level 2 GOBLIN"),
            () -> assertEquals(expectedPotionCraftingSkill, alchemist.getPotionCraftingSkill(), "Potion crafting skill should be 9 for level 2 GOBLIN"),
            () -> assertEquals(expectedBombThrowingSkill, alchemist.getBombThrowingSkill(), "Bomb throwing skill should be 7 for level 2 GOBLIN")
        );
    }

    @Test
    public void testLevelUpHuman() {
        Alchemist alchemist = new Alchemist("Alchemist Human", 100, 10, 1, Race.HUMAN);
        alchemist.setLevel(2);

        int expectedBombThrowingSkill = 6;
        int expectedAlchemySkill = 8;
        int expectedPotionCraftingSkill = 10;

        assertAll(
            () -> assertEquals(expectedAlchemySkill, alchemist.getAlchemySkill(), "Alchemy skill should be 8 for level 2 HUMAN"),
            () -> assertEquals(expectedPotionCraftingSkill, alchemist.getPotionCraftingSkill(), "Potion crafting skill should be 10 for level 2 HUMAN"),
            () -> assertEquals(expectedBombThrowingSkill, alchemist.getBombThrowingSkill(), "Bomb throwing skill should be 6 for level 2 HUMAN")
        );
    }

    @Test
    public void testToString() {
        Alchemist alchemist = new Alchemist("Alchemist Elf", 100, 10, 1, Race.ELF);

        String expected = "Alchemist{name='Alchemist Elf', health=100, level=1, race=ELF(strength=1, speed=2, intelligence=3), alchemySkill=6, potionCraftingSkill=7, bombThrowingSkill=3}";
        assertEquals(expected, alchemist.toString(), "toString should return correct string representation of Alchemist");
    }
}
