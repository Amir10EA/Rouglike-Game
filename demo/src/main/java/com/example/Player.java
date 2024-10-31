package com.example;

import java.util.List;

public class Player extends BaseCharacter {
    private int experience;
    private EquipmentManager equipmentManager;

    private int environmentalStrengthModifier = 0;

    public Player(String name, int health, int strength, int level, Race race) {
        super(name, health, strength, level, race);
        this.experience = 0;
        this.equipmentManager = new EquipmentManager();
    }

    public void applyEnvironmentEffect(String environmentType) {
        clearEnvironmentEffect(); //Reset any previous effects

        switch (environmentType.toLowerCase()) {
            case "lava":
                System.out.println("Lava environment: Player takes 5 damage!");
                takeDamage(5);
                break;
            case "stormy":
                System.out.println("Stormy environment: Player strength temporarily increased!");
                environmentalStrengthModifier = 2; 
                break;
            case "icy":
                System.out.println("Icy environment: Player takes slight damage!");
                takeDamage(2);
                break;
            case "forest":
                System.out.println("Forest environment: Player heals slightly!");
                heal(3);
                break;
            case "sandy":
                System.out.println("Sandy environment: Player strength temporarily decreased!");
                environmentalStrengthModifier = -1; 
                break;
            case "normal":
                System.out.println("Normal environment: No special effects.");
                break;
            default:
                throw new IllegalArgumentException("Unknown environment type: " + environmentType);
        }
    }

    public void clearEnvironmentEffect() {
        environmentalStrengthModifier = 0;
        System.out.println("Cleared environment effects.");
    }

    @Override
    public int getStrength() {
        return super.getStrength() + environmentalStrengthModifier;
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