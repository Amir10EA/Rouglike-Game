package com.example;

public abstract class Equipment {

   private int durability;
   private double weight;
   private Rarity rarity;
   private boolean isBroken;


    public Equipment(int durability, double weight, Rarity rarity) {
        this.durability = durability;
        this.weight = weight;
        this.rarity = rarity;
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
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

    public boolean isBroken(){
        return isBroken;
    }

    public void repair(int amount) {
        durability += amount;
        if (durability > 0) {
            isBroken = false;
        }
    }

    public void reduceDurability(int amount) {
        if(durability - amount < 0) {
            durability = 0;
            isBroken = true;
        } else {
            durability -= amount;
        }
    }

    public abstract void determineRarity();
    

}