package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PickpocketTest {

    @Test
    public void testHumanOpenLock() {
        Pickpocket humanPickpocket = new Pickpocket("Human", 100, 1, Race.HUMAN);
        assertTrue(humanPickpocket.openLock(2), "Human Pickpocket should open lock with difficulty 2");
    }

    @Test
    public void testElfOpenLock() {
        Pickpocket elfPickpocket = new Pickpocket("Elf", 100, 1, Race.ELF);
        assertTrue(elfPickpocket.openLock(2), "Elf Pickpocket should open lock with difficulty 2");
    }

    @Test
    public void testGoblinOpenLock() {
        Pickpocket goblinPickpocket = new Pickpocket("Goblin", 100, 1, Race.GOBLIN);
        assertTrue(goblinPickpocket.openLock(2), "Human Pickpocket should open lock with difficulty 2");
    }

    @Test
    public void testFailToOpenLock() {
        Pickpocket humanPickpocket = new Pickpocket("HumanPickpocket", 100, 1, Race.HUMAN);
        assertFalse(humanPickpocket.openLock(7), "Human should not be able to open a lock with difficulty 5");
    }

    @Test
    public void testMaxLockDifficultyForLevel1PlayerEachRaceCanOpen() {
        int maxHumanLockDifficulty = 4; 
        int maxElfLockDifficulty = 5; 
        int maxGoblinLockDifficulty = 3;

        Pickpocket humanPickpocket = new Pickpocket("HumanPickpocket", 100, 1, Race.HUMAN);
        Pickpocket elfPickpocket = new Pickpocket("elfPickpocket", 100, 1, Race.ELF);
        Pickpocket goblinPickpocket = new Pickpocket("GoblinPickpocket", 100, 1, Race.GOBLIN);

        assertAll("Check max lock difficulty for level 1 players of each race",
        () -> assertTrue(humanPickpocket.openLock(maxHumanLockDifficulty),
            "Human should be able to open a lock with difficulty " + maxHumanLockDifficulty),
        () -> assertTrue(elfPickpocket.openLock(maxElfLockDifficulty),
            "Elf should be able to open a lock with difficulty " + maxElfLockDifficulty),
        () -> assertTrue(goblinPickpocket.openLock(maxGoblinLockDifficulty),
            "Goblin should be able to open a lock with difficulty " + maxGoblinLockDifficulty)
    );
    }

    @Test
    public void testSneakPastEnemyWithAwarenessLevelOne() {
        Pickpocket humanPickpocket = new Pickpocket("HumanPickpocket", 100, 1, Race.HUMAN);
        Pickpocket elfPickpocket = new Pickpocket("elfPickpocket", 100, 1, Race.ELF);
        Pickpocket goblinPickpocket = new Pickpocket("GoblinPickpocket", 100, 1, Race.GOBLIN);

        int npcAwareness = 1;
        
        assertAll("Check if each race's pickpocket can sneak past an enemy with awareness level " + npcAwareness,
            () -> assertTrue(humanPickpocket.sneakPastEnemy(npcAwareness), 
                "Human pickpocket should be able to sneak past an enemy with awareness " + npcAwareness),
            () -> assertTrue(elfPickpocket.sneakPastEnemy(npcAwareness), 
                "Elf pickpocket should be able to sneak past an enemy with awareness " + npcAwareness),
            () -> assertTrue(goblinPickpocket.sneakPastEnemy(npcAwareness), 
                "Goblin pickpocket should be able to sneak past an enemy with awareness " + npcAwareness)
        );
    }

    @Test
    public void testSneakPastEnemyWithAwarenessLevelFive() {
        Pickpocket humanPickpocket = new Pickpocket("HumanPickpocket", 100, 1, Race.HUMAN);
        Pickpocket elfPickpocket = new Pickpocket("elfPickpocket", 100, 1, Race.ELF);
        Pickpocket goblinPickpocket = new Pickpocket("GoblinPickpocket", 100, 1, Race.GOBLIN);

        int npcAwareness = 5;
        
        assertAll("Check if each race's pickpocket can sneak past an enemy with awareness level " + npcAwareness,
            () -> assertFalse(humanPickpocket.sneakPastEnemy(npcAwareness), 
                "Human pickpocket should not be able to sneak past an enemy with awareness " + npcAwareness),
            () -> assertTrue(elfPickpocket.sneakPastEnemy(npcAwareness), 
                "Elf pickpocket should be able to sneak past an enemy with awareness " + npcAwareness),
            () -> assertFalse(goblinPickpocket.sneakPastEnemy(npcAwareness), 
                "Goblin pickpocket should not be able to sneak past an enemy with awareness " + npcAwareness)
        );
    }

    @Test
    public void testPickPocketing() {
        Pickpocket humanPickpocket = new Pickpocket("HumanPickpocket", 100, 1, Race.HUMAN);
        Pickpocket elfPickpocket = new Pickpocket("elfPickpocket", 100, 1, Race.ELF);
        Pickpocket goblinPickpocket = new Pickpocket("GoblinPickpocket", 100, 1, Race.GOBLIN);

        int npcAwareness = 3;

        assertAll("Check if each race's pickpocket can successfully pickpocket with NPC awareness " + npcAwareness,
            () -> assertTrue(humanPickpocket.pickPocket(npcAwareness), 
                "Human pickpocket should be able to pickpocket an NPC with awareness " + npcAwareness),
            () -> assertTrue(elfPickpocket.pickPocket(npcAwareness), 
                "Elf pickpocket should be able to pickpocket an NPC with awareness " + npcAwareness),
            () -> assertTrue(goblinPickpocket.pickPocket(npcAwareness), 
                "Goblin pickpocket should be able to pickpocket an NPC with awareness " + npcAwareness)
        );
    }

    @Test
    public void testLevelUpSkillsForHuman() {
        Pickpocket humanPickpocket = new Pickpocket("HumanPickpocket", 100, 1, Race.HUMAN);
        humanPickpocket.setLevel(2);

        int expectedLockPickingSkill = 6;  
        int expectedSneakingSkill = 7;
        int expectedPickpocketingSkill = 9;

        assertAll("Level-up skill increases for Human race",
            () -> assertEquals(expectedLockPickingSkill, humanPickpocket.getLockPickingSkill(), 
                "Lockpicking skill should increase to 6 at level 2 for Human"),
            () -> assertEquals(expectedSneakingSkill, humanPickpocket.getSneakingSkill(), 
                "Sneaking skill should increase to 7 at level 2 for Human"),
            () -> assertEquals(expectedPickpocketingSkill, humanPickpocket.getPickpocketingSkill(), 
                "Pickpockting skill should increase to 9 at level 2 for Human")
        );
    }
   
    @Test
    public void testLevelUpSkillsForElf() {
        Pickpocket elfPickpocket = new Pickpocket("ElfPickpocket", 100, 1, Race.ELF);
        elfPickpocket.setLevel(2);

        int expectedLockPickingSkill = 7;
        int expectedSneakingSkill = 8;
        int expectedPickpocketingSkill = 10;

        assertAll("Level-up skill increases for Elf race",
            () -> assertEquals(expectedLockPickingSkill, elfPickpocket.getLockPickingSkill(), 
                "Lockpicking skill should increase to 6 at level 2 for Elf"),
            () -> assertEquals(expectedSneakingSkill, elfPickpocket.getSneakingSkill(), 
                "Sneaking skill should increase to 9 at level 2 for Elf"),
            () -> assertEquals(expectedPickpocketingSkill, elfPickpocket.getPickpocketingSkill(), 
                "Pickpocketing skill should increase to 11 at level 2 for Elf")
        );
    }

    @Test
    public void testLevelUpSkillsForGoblin() {
        Pickpocket goblinPickpocket = new Pickpocket("GoblinPickpocket", 100, 1, Race.GOBLIN);
        goblinPickpocket.setLevel(2);

        int expectedLockPickingSkill = 5;
        int expectedSneakingSkill = 7;
        int expectedPickpocketingSkill = 9;

        assertAll("Level-up skill increases for Goblin race",
            () -> assertEquals(expectedLockPickingSkill, goblinPickpocket.getLockPickingSkill(), 
                "Lockpicking skill should increase to 5 at level 2 for Goblin"),
            () -> assertEquals(expectedSneakingSkill, goblinPickpocket.getSneakingSkill(), 
                "Sneaking skill should increase to 8 at level 2 for Goblin"),
            () -> assertEquals(expectedPickpocketingSkill, goblinPickpocket.getPickpocketingSkill(), 
                "Pickpocketing skill should increase to 10 at level 2 for Goblin")
        );
    }


}


