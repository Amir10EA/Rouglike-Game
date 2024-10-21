package com.example;

public abstract class AbstractCharacter implements Character{
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int strength;
    protected double damageMultiplier;

    public AbstractCharacter(String name, int health, int strength) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.strength = strength;
        this.damageMultiplier = 1.0;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

}
