package com.example;

import java.util.List;

public class Player extends BaseCharacter {
    private int experience;
    private EquipmentManager equipmentManager;
    private int environmentalStrengthModifier = 0;
    private Quest currentQuest;

    public Player(String name, int health, int strength, int level, Race race) {
        super(name, health, strength, level, race);
        this.experience = 0;
        this.equipmentManager = new EquipmentManager();
    }

    public void applyEnvironmentEffect(EnvironmentType environmentType) {
        clearEnvironmentEffect(); //Reset any previous effects

        switch (environmentType) {
            case LAVA:
                System.out.println("Lava environment: Player takes 5 damage!");
                takeDamage(5);
                break;
            case STORMY:
                System.out.println("Stormy environment: Player strength temporarily increased!");
                environmentalStrengthModifier = 2; 
                break;
            case ICY:
                System.out.println("Icy environment: Player takes slight damage!");
                takeDamage(2);
                break;
            case FOREST:
                System.out.println("Forest environment: Player heals slightly!");
                heal(3);
                break;
            case SANDY:
                System.out.println("Sandy environment: Player strength temporarily decreased!");
                environmentalStrengthModifier = -1; 
                break;
            case NORMAL:
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

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
    }

    public void cancelCurrentQuest() {
        this.currentQuest = null;
    }

    @Override
    public int calculateDamage() {
        int baseDamage = super.getStrength();
        if (getActiveWeapon() != null) {
            baseDamage += getActiveWeapon().calculateDamage();
        }
        return baseDamage;
    }

    @Override
    public void takeDamage(int amount) {
        int reducedDamage = amount;
        for (Armor armor : getEquippedArmor()) {
            reducedDamage = armor.calculateDamageTaken(reducedDamage, 0); // Assuming physical damage for simplicity
        }
        setHealth(getHealth() - reducedDamage);
    }
}