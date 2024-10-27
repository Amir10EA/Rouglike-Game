package com.example;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {
    private List<InventoryItem> inventory;
    private Weapon activeWeapon;
    private List<Armor> equippedArmor;

    public EquipmentManager() {
        this.inventory = new ArrayList<>();
        this.activeWeapon = null;
        this.equippedArmor = new ArrayList<>();
    }

    public void addItem(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        for (InventoryItem i : inventory) {
            if (i.getName().equals(item.getName())) {
                throw new IllegalArgumentException("Item already exists in inventory.");
            }
        }
        inventory.add(item);
    }

    public void removeItem(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (!inventory.contains(item)) {
            throw new IllegalArgumentException("Item not in inventory.");
        }
        if (item.equals(activeWeapon)) {
            activeWeapon = null;
        }
        if (item instanceof Armor && equippedArmor.contains(item)) {
            equippedArmor.remove(item);
        }
        inventory.remove(item);
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void equipWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon cannot be null.");
        }
        if (!inventory.contains(weapon)) {
            throw new IllegalArgumentException("Weapon not in inventory.");
        }
        activeWeapon = weapon;
    }

    public void unequipWeapon() {
        activeWeapon = null;
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public void equipArmor(Armor armor) {
        if (armor == null) {
            throw new IllegalArgumentException("Armor cannot be null.");
        }
        if (!inventory.contains(armor)) {
            throw new IllegalArgumentException("Armor not in inventory.");
        }
        equippedArmor.removeIf(a -> a.getArmorType() == armor.getArmorType());
        equippedArmor.add(armor);
    }

    public void unequipArmor(Armor armor) {
        if (armor == null) {
            throw new IllegalArgumentException("Armor cannot be null.");
        }
        if (!equippedArmor.contains(armor)) {
            throw new IllegalArgumentException("Armor not equipped.");
        }
        equippedArmor.remove(armor);
    }

    public List<Armor> getEquippedArmor() {
        return equippedArmor;
    }
}