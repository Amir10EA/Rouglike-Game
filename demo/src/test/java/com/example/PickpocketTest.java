package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PickpocketTest {

    @Test
    public void testOpenLock() {
        Pickpocket thief = new Pickpocket("Locke", 100, 1, Race.HUMAN);
        assertTrue(thief.openLock(2), "Pickpocket borde kunna dyrka upp ett lås med svårighet 2");
    }

    @Test
    public void testFailToOpenLock() {
        Pickpocket thief = new Pickpocket("Locke", 100, 1, Race.GOBLIN);
        assertFalse(thief.openLock(5), "Pickpocket borde inte kunna dyrka upp ett lås med svårighet 5");
    }

    @Test
    public void testSneakPastEnemy() {
        Pickpocket thief = new Pickpocket("Locke", 100, 1, Race.ELF);
        assertTrue(thief.sneakPastEnemy(3), "Pickpocket borde kunna smyga förbi en fiende med vaksamhet 3");
    }

    @Test
    public void testPickPocket() {
        Pickpocket thief = new Pickpocket("Locke", 100, 1, Race.GOBLIN);
        assertTrue(thief.pickPocket(3), "Pickpocket borde kunna stjäla från NPC med vaksamhet 3");
    }

    @Test
    public void testLevelUpSkills() {
        Pickpocket thief = new Pickpocket("Locke", 100, 1, Race.HUMAN);
        thief.setLevel(2);
        assertEquals(6, thief.getLockPickingSkill(), "Lockpicking-färdigheten borde öka till 6 vid nivå 2 för Human");
        assertEquals(8, thief.getSneakingSkill(), "Smygningsfärdigheten borde öka till 8 vid nivå 2 för Human");
    }
}


