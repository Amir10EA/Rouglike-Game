package com.example;

public class BaseCharacter{
    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    private int level;
    private Race race;

    public BaseCharacter(String name, int health, int strength, int level, Race race) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        this.name = name;
        this.health = Math.min(Math.max(health, 0), 100); 
        this.maxHealth = 100; 
        this.strength = strength;
        this.level = level;
        this.race  = race;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Race getRace(){
        return race;
    }

    public void setRace(Race race){
        this.race = race;
    }

    public int calculateDamage() {
        return strength;
    }

    public void takeDamage(int amount) {
        setHealth(this.health - amount);
    }

    public void heal(int amount) {
        if(this.isAlive()){
            this.health += amount;
            if (this.health > this.maxHealth) {
                this.health = this.maxHealth;
            }
        }
    }

    public void attack(BaseCharacter enemy) {
       if (this.isAlive()){
        int damage = calculateDamage();
        enemy.takeDamage(damage);} 
    }

    public boolean isAlive() {
        return this.health > 0;
    }
  
}
