package com.example;

public class Player extends BaseCharacter {
    private int experience;
    private EquipmentManager equipmentManager;

    public Player(String name, int health, int strength, Race race) {
        super(name, health, strength, race);
        this.experience = 0;
        this.equipmentManager = new EquipmentManager();
    }

    public int getExperience() {
        return experience;
    }

}
