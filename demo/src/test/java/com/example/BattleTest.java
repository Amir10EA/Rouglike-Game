package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BattleTest {

    private Player player;
    private Enemy enemy;

    private static final int PLAYER_HEALTH = 100;
    private static final int PLAYER_STRENGTH = 15;
    private static final int ENEMY_HEALTH = 50;
    private static final int ENEMY_STRENGTH = 10;

    @BeforeEach
    public void setup() {
        player = new Player("Hero", PLAYER_HEALTH, PLAYER_STRENGTH, 1, Race.HUMAN);
        enemy = new Enemy("Goblin", ENEMY_HEALTH, ENEMY_STRENGTH, 1, Race.GOBLIN, 10);
    }

    @Test
    public void testPlayerWinsBattle() {
        List<Integer> moves = List.of(1, 1, 1, 1);
        Battle battle = new Battle(player, enemy, moves);

        boolean playerAlive = battle.startBattle();

        assertTrue(playerAlive, "Player should survive the battle.");
        assertFalse(enemy.isAlive(), "Enemy should be defeated.");
        assertEquals(PLAYER_HEALTH - (ENEMY_STRENGTH * 3), player.getHealth(),
                "Player health should reflect damage taken.");
    }

    @Test
    public void testPlayerLosesBattle() {
        enemy = new Enemy("Goblin", ENEMY_HEALTH, 50, 1, Race.GOBLIN, 10);
        List<Integer> moves = List.of(1, 1);
        Battle battle = new Battle(player, enemy, moves);

        boolean playerAlive = battle.startBattle();

        assertFalse(playerAlive, "Player should lose the battle.");
        assertTrue(enemy.isAlive(), "Enemy should survive if player loses.");
    }

    @Test
    public void testInvalidInput() {
        List<Integer> moves = List.of(2, 1);
        Battle battle = new Battle(player, enemy, moves);

        boolean playerAlive = battle.startBattle();

        assertTrue(playerAlive || !enemy.isAlive(), "Battle should progress correctly after invalid input.");
    }
}
