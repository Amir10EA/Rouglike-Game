package com.example;

public class Weapon extends Equipment {
    private int damage;
    private double attackSpeed;
    private WeaponType type;

    public Weapon(String name, double weight, int durability, int damage, double attackSpeed, WeaponType type) {
        super(name, weight, durability);
        if (damage < 0)
            throw new IllegalArgumentException("Damage cannot be negative");
        if (attackSpeed <= 0)
            throw new IllegalArgumentException("Attack speed must be positive");

        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.type = type;
        setRarity(determineRarity());
    }

    public enum WeaponType {
        SWORD, AXE, MACE, SPEAR, DAGGER, STAFF, WAND
    }

    public int getDamage() {
        return damage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public WeaponType getType() {
        return type;
    }

    public int attack() {
        return damage;
    }


    public int calculateDamage() {
        return (int) (damage * attackSpeed);
    }

    public double calculateDPS() {
        return damage * attackSpeed;
    }

    public String use() {
        if (!isBroken()) {
            reduceDurability(1);
            return "You use the " + getName() + "!";
        } else {
            return "The weapon is broken!";
        }
    }

    @Override
    public Rarity determineRarity() {
        double dps = calculateDPS();
        if (dps <= 10) {
            return Rarity.COMMON;
        } else if (dps <= 25) {
            return Rarity.UNCOMMON;
        } else if (dps <= 50) {
            return Rarity.RARE;
        } else if (dps <= 75) {
            return Rarity.EPIC;
        } else {
            return Rarity.LEGENDARY;
        }
    }
}