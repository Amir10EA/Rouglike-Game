package com.example;

public enum Race {
    HUMAN(2, 1, 2), 
    ELF(1, 2, 3), 
    GOBLIN(3, 1, 1);

    private final int speed;
    private final int strength;
    private final int intelligence;

    Race(int strength, int speed, int intelligence){
        this.speed = speed;
        this.intelligence = intelligence;
        this.strength = strength;
    }

    public int getSpeed(){
        return speed;
    }

    public int getStrength(){
        return strength;
    }

    public int getIntelligence(){
        return intelligence;
    }
    
    @Override
    public String toString() {
        return String.format("%s(strength=%d, speed=%d, intelligence=%d)", 
                             name(), strength, speed, intelligence);
    }
}
