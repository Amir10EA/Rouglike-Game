package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestGiverTest {
    private QuestGiver questGiver;
    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        questGiver = new QuestGiver("TestGiver");
        player = new Player("TestPlayer", 100, 10, 1, Race.HUMAN);
        enemy = new Enemy("TestEnemy", 50, 5, 1, Race.GOBLIN, 10);
    }

    @Test
    public void testQuestGiverCreation() {
        assertNotNull(questGiver);
        assertNotNull(questGiver.getQuest());
    }

    @Test
    public void testNameNotNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new QuestGiver(null));
        assertThrows(IllegalArgumentException.class, () -> new QuestGiver(""));
    }

    @Test
    public void testInteractWithSuccessfulInteractionYes() {
        QuestGiver questGiverSpy = new QuestGiver("TestGiver") {
            @Override
            public void interact(Player player) {
                System.out.println(getName() + ": " + getQuest().getDescription() + " Do you accept? (yes/no)");
                String response = "yes";
                if (response.equals("yes")) {
                    player.setCurrentQuest(getQuest());
                }
            }
        };
        questGiverSpy.interact(player);
        assertNotNull(player.getCurrentQuest());
    }

    @Test
    public void testInteractWithSuccessfulInteractionNo() {
        QuestGiver questGiverSpy = new QuestGiver("TestGiver") {
            @Override
            public void interact(Player player) {
                System.out.println(getName() + ": " + getQuest().getDescription() + " Do you accept? (yes/no)");
                String response = "no";
                if (response.equals("yes")) {
                    player.setCurrentQuest(getQuest());
                }
            }
        };
        questGiverSpy.interact(player);
        assertNull(player.getCurrentQuest());
    }

    @Test
    public void testInteractWithNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> questGiver.interact(null));
    }

    @Test
    public void testGenerateQuest() {
        Quest quest = questGiver.getQuest();
        assertNotNull(quest);
        assertTrue(quest.getTargetEnemies() >= 2 && quest.getTargetEnemies() <= 5);
    }

    @Test
    public void testGiveRewardWithNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> questGiver.giveReward(null));
    }

    @Test
    public void testGiveRewardSuccessfulRun() {
        questGiver.giveReward(player);
        assertTrue(player.getExperience() > 0);
    }

    @Test
    public void testGenerateRandomRewardWeapon() {
        InventoryItem reward = questGiver.generateRandomReward(0);
        assertTrue(reward instanceof Weapon);
    }

    @Test
    public void testGenerateRandomRewardArmor() {
        InventoryItem reward = questGiver.generateRandomReward(1);
        assertTrue(reward instanceof Armor);
    }

    @Test
    public void testGenerateRandomRewardItem() {
        InventoryItem reward = questGiver.generateRandomReward(2);
        assertTrue(reward instanceof Item);
    }
}