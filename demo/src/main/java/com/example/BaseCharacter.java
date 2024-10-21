package com.example;

public class BaseCharacter{
    private String name;
    private int health;
    private int maxHealth;
    private int strength;

    public BaseCharacter(String name, int health, int strength) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        this.name = name;
        this.health = Math.min(Math.max(health, 0), 100); 
        this.maxHealth = 100; 
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(Math.max(health, 0), 100); 
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = Math.min(maxHealth, 100); 
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int calculateDamage() {
        return strength;
    }

    public void takeDamage(int amount) {
        setHealth(this.health - amount);
    }

    public void heal(int amount) {
        setHealth(this.health + amount);
    }

    public void attack(BaseCharacter enemy) {
        int damage = calculateDamage();
        enemy.takeDamage(damage);
    }

    public boolean isAlive() {
        return this.health > 0;
    }
  
}
