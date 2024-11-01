package com.example;

public class QuestGiverTile extends Tile {
    private QuestGiver questGiver;

    public QuestGiverTile(QuestGiver questGiver) {
        super(true, "questgiver");
        this.questGiver = questGiver;
    }

    public QuestGiver getQuestGiver() {
        return questGiver;
    }
}