package com.example;

public class EnemyTile extends Tile {
    private Enemy enemy;

    public EnemyTile(Enemy enemy) {
        super(true, "enemy");
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}