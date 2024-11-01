package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllyEncounterTest {
    private Player player;
    private Enemy friendlyEnemy;
    private Quest quest;
    private AllyEncounter allyEncounter;

    @BeforeEach
    public void setUp() {
        player = new Player("Hero", 100, 10, 1, Race.HUMAN);
        friendlyEnemy = new Enemy("Friendly Goblin", 50, 10, 1, Race.GOBLIN, 10);
        quest = new Quest("Defeat enemies", 5);
        player.setCurrentQuest(quest);
        allyEncounter = new AllyEncounter(player, friendlyEnemy);
    }

    @Test
    public void testCollectResourcesChoice() {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        allyEncounter.startEncounter();

        assertEquals(1, player.getEquipmentManager().getInventory().size(), "Player should have one item in inventory.");
        InventoryItem item = player.getEquipmentManager().getInventory().get(0);
        assertEquals("Armor Stone", item.getName(), "Collected item should be 'Armor Stone'.");

        System.setIn(originalIn);
    }

    @Test
    public void testProgressQuestChoice() {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));

        int initialEnemiesDefeated = quest.getEnemiesDefeated();

        allyEncounter.startEncounter();

        assertEquals(initialEnemiesDefeated + 1, quest.getEnemiesDefeated(), "Quest progression should increase by one.");

        System.setIn(originalIn);
    }

    @Test
    public void testInvalidChoice() {

        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));

        int initialInventorySize = player.getEquipmentManager().getInventory().size();
        int initialEnemiesDefeated = quest.getEnemiesDefeated();

        allyEncounter.startEncounter();

        assertEquals(initialInventorySize, player.getEquipmentManager().getInventory().size(), "Inventory size should remain the same.");
        assertEquals(initialEnemiesDefeated, quest.getEnemiesDefeated(), "Quest progression should remain the same.");

        System.setIn(originalIn);
    }
}
