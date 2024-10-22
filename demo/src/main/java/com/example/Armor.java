package com.example;

public class Armor extends Equipment {
    private static final int MAX_DEFENSE = 100;

    private int physicalDefense;
    private int magicalDefense;
    private ArmorType armorType;

    public Armor(String name, double weight, int durability, int physicalDefense, int magicalDefense, ArmorType armorType, Cost cost) {
        super(name, weight, durability, cost);
        if (physicalDefense < 0 || physicalDefense > MAX_DEFENSE)
            throw new IllegalArgumentException("Physical defense must be between 0 and " + MAX_DEFENSE);
        if (magicalDefense < 0 || magicalDefense > MAX_DEFENSE)
            throw new IllegalArgumentException("Magical defense must be between 0 and " + MAX_DEFENSE);

        this.physicalDefense = physicalDefense;
        this.magicalDefense = magicalDefense;
        this.armorType = armorType;
        setRarity(determineRarity());
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    public int getMagicalDefense() {
        return magicalDefense;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public double calculateTotalProtection() {
        return physicalDefense + magicalDefense;
    }

    public String use() {
        if (!isBroken()) {
            reduceDurability(1);
            return "You use the " + getName() + "!";
        } else {
            return "The armor is broken!";
        }
    }

    public void upgrade(int armorStones, double money) {
        if (armorStones < 0 || money < 0) {
            throw new IllegalArgumentException("Stones and money must be non-negative");
        }

        int maxPossibleIncrease = (int) (money / 10); 
        int actualIncrease = Math.min(armorStones, maxPossibleIncrease); 

        if (actualIncrease <= 0) {
        throw new IllegalArgumentException("Not enough for an upgrade");
        }
        int additionalPhysicalDefense = actualIncrease / 2; 
        int additionalMagicalDefense = actualIncrease / 2; 

        if (armorStones >= actualIncrease && money >= actualIncrease * 10) {
            physicalDefense = Math.min(MAX_DEFENSE, physicalDefense + additionalPhysicalDefense);
            magicalDefense = Math.min(MAX_DEFENSE, magicalDefense + additionalMagicalDefense);
        } else {
            throw new IllegalArgumentException("Not enough stones or money for upgrade");
        }

        setRarity(determineRarity());
    }

    public int calculateDamageTaken(int incomingPhysicalDamage, int incomingMagicalDamage) {
        int reducedPhysicalDamage = (int) (incomingPhysicalDamage / (1 + physicalDefense / 100.0));
        int reducedMagicalDamage = (int) (incomingMagicalDamage / (1 + magicalDefense / 100.0));
        return reducedPhysicalDamage + reducedMagicalDamage;
    }
    
    @Override
    public Rarity determineRarity() {
        double totalProtection = calculateTotalProtection();
        if (totalProtection < 20) {
            return Rarity.COMMON;
        } else if (totalProtection < 40) {
            return Rarity.UNCOMMON;
        } else if (totalProtection < 60) {
            return Rarity.RARE;
        } else if (totalProtection < 80) {
            return Rarity.EPIC;
        } else {
            return Rarity.LEGENDARY;
        }
    }
}
enum ArmorType {
    HELMET, CHESTPLATE, LEGGINGS, BOOTS
}