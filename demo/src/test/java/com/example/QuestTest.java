package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestTest {
    private QuestGiver questGiver;
    private Player player;

    @BeforeEach
    public void setUp() {
        questGiver = new QuestGiver("TestGiver");
        player = new Player("TestPlayer", 100, 10, 1, Race.HUMAN);
    }

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
        quest.setQuestGiver(questGiver);
        player.setCurrentQuest(quest);
        quest.enemyDefeated(player);
        assertEquals(1, quest.getEnemiesDefeated());
        quest.enemyDefeated(player);
        assertEquals(2, quest.getEnemiesDefeated());
        quest.enemyDefeated(player);
        assertEquals(3, quest.getEnemiesDefeated());
        assertTrue(quest.isCompleted());
    }

    @Test
    public void testEnemyDefeatedWithNullPlayer() {
        Quest quest = new Quest("Defeat 3 enemies", 3);
        assertThrows(IllegalArgumentException.class, () -> quest.enemyDefeated(null));
    }

    @Test
    public void testQuestCompletion() {
        Quest quest = new Quest("Defeat 3 enemies", 3);
        quest.setQuestGiver(questGiver);
        player.setCurrentQuest(quest);
        quest.enemyDefeated(player);
        quest.enemyDefeated(player);
        quest.enemyDefeated(player);
        assertTrue(quest.isCompleted());
        assertNull(player.getCurrentQuest());
    }

    @Test
    public void testSetQuestGiver() {
        Quest quest = new Quest("Defeat 3 enemies", 3);
        QuestGiver questGiver = new QuestGiver("TestGiver");
        quest.setQuestGiver(questGiver);
        assertEquals(questGiver, quest.getQuestGiver());
    }
}