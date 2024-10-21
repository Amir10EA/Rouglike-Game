package com.example;

public interface Character {
    String getName();
    int getHealth();
    void setHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
    int getStrength();
    void setStrength(int strength);
    int calculateDamage();
    void takeDamage(int amount);
    void heal(int amount);
    void attack(Character enemy);
}
