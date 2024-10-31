package com.example;

public class Quest {
    private String description;
    private int targetEnemies;
    private int enemiesDefeated;
    private QuestGiver questGiver;

    public Quest(String description, int targetEnemies) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (targetEnemies <= 0) {
            throw new IllegalArgumentException("Target enemies must be greater than zero");
        }
        this.description = description;
        this.targetEnemies = targetEnemies;
        this.enemiesDefeated = 0;
    }

    public String getDescription() {
        return description;
    }

    public int getTargetEnemies() {
        return targetEnemies;
    }

    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public void enemyDefeated(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        enemiesDefeated++;
        if (isCompleted()) {
            questGiver.giveReward(player);
            player.cancelCurrentQuest();
        }
    }

    public boolean isCompleted() {
        return enemiesDefeated >= targetEnemies;
    }

    public void setQuestGiver(QuestGiver questGiver) {
        this.questGiver = questGiver;
    }

    public QuestGiver getQuestGiver() {
        return questGiver;
    }
}