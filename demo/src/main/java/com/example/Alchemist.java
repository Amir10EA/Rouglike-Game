package com.example;

public class Alchemist extends BaseCharacter {
    private int alchemySkill;
    private int potionCraftingSkill;
    private int bombThrowingSkill;

    public Alchemist(String name, int health, int strength, int level, Race race) {
        super(name, health, strength, level, race);  
        this.alchemySkill = calculateAlchemySkill(level);
        this.potionCraftingSkill = calculatePotionCraftingSkill(level);
        this.bombThrowingSkill = calculateBombThrowingSkill(level);
    }

    private int calculateAlchemySkill(int level) {
        return level * 3 + getRace().getIntelligence();
    }

    private int calculatePotionCraftingSkill(int level) {
        return level * 4 + getRace().getIntelligence();
    }

    private int calculateBombThrowingSkill(int level) {
        return level * 2 + getRace().getStrength();
    }

    public boolean throwBomb(int enemyDefense) {
        return bombThrowingSkill >= enemyDefense;
    }

    public int getAlchemySkill() {
        return alchemySkill;
    }

    public int getPotionCraftingSkill() {
        return potionCraftingSkill;
    }

    public int getBombThrowingSkill() {
        return bombThrowingSkill;
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
        this.alchemySkill = calculateAlchemySkill(level);
        this.potionCraftingSkill = calculatePotionCraftingSkill(level);
        this.bombThrowingSkill = calculateBombThrowingSkill(level);
    }

    @Override
    public String toString() {
        return String.format("Alchemist{name='%s', health=%d, level=%d, race=%s, alchemySkill=%d, potionCraftingSkill=%d, bombThrowingSkill=%d}",
                getName(), getHealth(), getLevel(), getRace(), alchemySkill, potionCraftingSkill, bombThrowingSkill);
    }
}

