package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class QuestTest {

    @Test
    public void testValidQuest() {
        Quest quest = new Quest("Defeat 3 enemies", 3);
        assertNotNull(quest);
        assertEquals("Defeat 3 enemies", quest.getDescription());
        assertEquals(3, quest.getTargetEnemies());
        assertEquals(0, quest.getEnemiesDefeated());
    }

    @Test
    public void testInvalidQuestWithNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Quest(null, 3));
    }

    @Test
    public void testInvalidQuestWithEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Quest("", 3));
    }

    @Test
    public void testInvalidQuestWithZeroTargetEnemies() {
        assertThrows(IllegalArgumentException.class, () -> new Quest("Defeat 0 enemies", 0));
    }

    @Test
    public void testEnemyDefeated() {
        Quest quest = new Quest("Defeat 3 enemies", 3);
        Player player = new Player("TestPlayer", 100, 10, 1, Race.HUMAN);
        quest.enemyDefeated(player);
        assertEquals(1, quest.getEnemiesDefeated());
        quest.enemyDefeated(player);
        assertEquals(2, quest.getEnemiesDefeated());
        quest.enemyDefeated(player);
        assertEquals(3, quest.getEnemiesDefeated());
        assertTrue(quest.isCompleted());
    }

    @Test
    public void testQuestCompletion() {
        Quest quest = new Quest("Defeat 3 enemies", 3);
        Player player = new Player("TestPlayer", 100, 10, 1, Race.HUMAN);
        quest.enemyDefeated(player);
        quest.enemyDefeated(player);
        quest.enemyDefeated(player);
        assertTrue(quest.isCompleted());
        assertNull(player.getCurrentQuest());
    }
}