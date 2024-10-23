package com.example;

import java.util.List;

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

    public void gainExperience(int amount) {
        if (amount > 0) {
            this.experience += amount;
        }
    }

        public void addWeapon(Weapon weapon) {
        equipmentManager.addWeapon(weapon);
    }

    public void removeWeapon(Weapon weapon) {
        equipmentManager.removeWeapon(weapon);
    }

    public List<Weapon> getWeapons() {
        return equipmentManager.getWeapons();
    }

}
