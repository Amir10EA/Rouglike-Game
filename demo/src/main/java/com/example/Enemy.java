package com.example;

public class Enemy extends BaseCharacter {
    private EquipmentManager equipmentManager;
    private int xp; 

    public Enemy(String name, int health, int strength, int level, Race race, int initialXp) {
        super(name, health, strength, level, race);
        this.equipmentManager = new EquipmentManager();
        this.xp = initialXp;
    }

    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    public int getXp() {
        return xp;
    }

    public void gainXp(int amount) {
        if (amount > 0) {
            this.xp += amount;
        }
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

    public void attack(BaseCharacter target) {
        if (this.isAlive()) {
            int damage = calculateDamage();
            System.out.println(this.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
            target.takeDamage(damage);
        }
    }

    public void defend() {
        System.out.println(this.getName() + " is defending.");
    }

    public void decideAction(BaseCharacter target) {
        if (xp >= 100) { 
            attack(target);
        } else {
            defend();
        }
    }
}
