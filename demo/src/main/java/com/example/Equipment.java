package com.example;

public abstract class Equipment {
    private String name;
    private int durability;
    private double weight;
    private Rarity rarity;
    private boolean isBroken;

    public Equipment(String name, double weight, int durability) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");
        if (durability < 0)
            throw new IllegalArgumentException("Durability cannot be negative");
        if (weight <= 0)
            throw new IllegalArgumentException("Weight must be positive");

        this.name = name;
        this.weight = weight;
        this.durability = durability;
        this.isBroken = durability == 0;
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    }

    public String getName() {
        return name;
    }

    public int getDurability() {
        return durability;
    }

    public double getWeight() {
        return weight;
    }

    public Rarity getRarity() {
        return rarity;
    }

    protected void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void repair(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Repair amount cannot be negative");
        this.durability += amount;
        if (this.durability > 0) {
            isBroken = false;
        }
    }

    public void reduceDurability(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Durability reduction cannot be negative");
        durability = Math.max(0, durability - amount);
        isBroken = durability == 0;
    }

    public abstract Rarity determineRarity();
}