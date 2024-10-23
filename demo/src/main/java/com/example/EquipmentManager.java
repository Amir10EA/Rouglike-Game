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
        // Check for duplicate weapons
        for (Weapon w : weapons) {
            if (w.getName().equals(weapon.getName())) {
                throw new IllegalArgumentException("Weapon already exists.");
            }
        }
        weapons.add(weapon);
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }       
}
