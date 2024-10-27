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

    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    public void addItem(InventoryItem item) {
        equipmentManager.addItem(item);
    }

    public void removeItem(InventoryItem item) {
        equipmentManager.removeItem(item);
    }

    public void equipWeapon(Weapon weapon) {
        equipmentManager.equipWeapon(weapon);
    }

    public void unequipWeapon() {
        equipmentManager.unequipWeapon();
    }

    public Weapon getActiveWeapon() {
        return equipmentManager.getActiveWeapon();
    }

    public void equipArmor(Armor armor) {
        equipmentManager.equipArmor(armor);
    }

    public void unequipArmor(Armor armor) {
        equipmentManager.unequipArmor(armor);
    }

    public List<Armor> getEquippedArmor() {
        return equipmentManager.getEquippedArmor();
    }
}