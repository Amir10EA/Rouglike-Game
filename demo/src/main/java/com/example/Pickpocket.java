package com.example;

public class Pickpocket extends Player{
    private int lockPickingSkill;
    private int sneakingSkill;
    private int pickpocketingSkill;

    public Pickpocket(String name, int health, int level, Race race) {
        super(name, health, level, race);
        this.lockPickingSkill = calculateLockPickingSkill(level);
        this.sneakingSkill = calculateSneakingSkill(level);
        this.pickpocketingSkill = calculatePickpocketingSkill(level);
    }

    private int calculateLockPickingSkill(int level) {
        return level * 2 + getRace().getIntelligence(); 
    }

    private int calculateSneakingSkill(int level) {
        return level * 3 + getRace().getSpeed(); 
    }

    private int calculatePickpocketingSkill(int level) {
        return level * 4 + getRace().getSpeed(); 
    }

    public boolean openLock(int lockDifficulty) {
        return lockPickingSkill >= lockDifficulty;
    }

    public boolean sneakPastEnemy(int enemyAwareness) {
        return sneakingSkill >= enemyAwareness;
    }

    public boolean pickPocket(int npcAwareness) {
        return pickpocketingSkill >= npcAwareness;
    }

    public int getLockPickingSkill() {
        return lockPickingSkill;
    }

    public int getSneakingSkill() {
        return sneakingSkill;
    }

    public int getPickpocketingSkill() {
        return pickpocketingSkill;
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
        this.lockPickingSkill = calculateLockPickingSkill(level);
        this.sneakingSkill = calculateSneakingSkill(level);
        this.pickpocketingSkill = calculatePickpocketingSkill(level);
    }
}
