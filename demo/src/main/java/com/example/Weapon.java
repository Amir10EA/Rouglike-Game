package com.example;

public class Weapon extends Equipment {
    private static final int MAX_DAMAGE = 200;
    private static final double MAX_ATTACK_SPEED = 3.0;

    private int damage;
    private double attackSpeed;
    private WeaponType type;

    public Weapon(String name, double weight, int durability, int damage, double attackSpeed, WeaponType type, Cost cost) {
        super(name, weight, durability, cost);
        if (damage < 0 || damage > MAX_DAMAGE)
            throw new IllegalArgumentException("Damage must be between 0 and " + MAX_DAMAGE);
        if (attackSpeed <= 0 || attackSpeed > MAX_ATTACK_SPEED)
            throw new IllegalArgumentException("Attack speed must be between 0 and " + MAX_ATTACK_SPEED);
            
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.type = type;
        setRarity(determineRarity());
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

    public void upgrade(int stones, double money) {
        if (stones < 0 || money < 0) {
            throw new IllegalArgumentException("Stones and money must be non-negative");
        }
    
        int maxPossibleIncrease = (int) (money / 10); 
        int actualIncrease = Math.min(stones, maxPossibleIncrease);
    
        if (actualIncrease <= 0) {
            throw new IllegalArgumentException("Not enough stones or money for upgrade");
        }
    
        int additionalDamage = actualIncrease / 2; 
        double additionalAttackSpeed = actualIncrease / 20.0;
    
        // Ensure weapon type is valid for upgrade
        if (!type.getDamageType().equals("Physical") && !type.getDamageType().equals("Magical")) {
            throw new IllegalArgumentException("Invalid weapon type for upgrade");
        }
    
        // Perform the upgrade
        damage = Math.min(MAX_DAMAGE, damage + additionalDamage);
        attackSpeed = Math.min(MAX_ATTACK_SPEED, attackSpeed + additionalAttackSpeed);
    
        setRarity(determineRarity());
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