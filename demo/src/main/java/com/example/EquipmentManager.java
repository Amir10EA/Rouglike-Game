package com.example;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {
    private List<Weapon> weapons;
    private Weapon activeWeapon;

    public EquipmentManager() {
        this.weapons = new ArrayList<>();
        this.activeWeapon = null;
    }

    public void addWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon cannot be null.");
        }
        for (Weapon w : weapons) {
            if (w.getName().equals(weapon.getName())) {
                throw new IllegalArgumentException("Weapon already exists.");
            }
        }
        weapons.add(weapon);
    }


    public void equipWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon cannot be null.");
        }
        if (!weapons.contains(weapon)) {
            throw new IllegalArgumentException("Weapon not in inventory.");
        }
        activeWeapon = weapon;
    }

    public void removeWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon cannot be null.");
        }
        if (!weapons.contains(weapon)) {
            throw new IllegalArgumentException("Weapon not in inventory.");
        }
        weapons.remove(weapon);
        if (activeWeapon != null && activeWeapon.equals(weapon)) {
            activeWeapon = null;
        }
        
    }

    public void switchWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon cannot be null.");
        }
        if (!weapons.contains(weapon)) {
            throw new IllegalArgumentException("Weapon not in inventory.");
        }
        activeWeapon = weapon;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }       
}
