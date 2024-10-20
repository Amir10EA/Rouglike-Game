package com.example;


public class Player {
    private String name;
    private int health;
    private int level;
    // Add x and y coordinates after
    private Race race;


    public Player(String name, int health, int level, Race race) {
        this.name = name;
        this.health = health;
        this.level = level;
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }   

    public int getLevel() {
        return level;
    }

    public Race getRace() {
        return race;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
