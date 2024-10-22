package com.example;

public enum WeaponType {
    SWORD("Physical"), 
    AXE("Physical"), 
    MACE("Physical"), 
    SPEAR("Physical"), 
    DAGGER("Physical"), 
    STAFF("Magical"), 
    WAND("Magical"), 
    ORB("Magical"), 
    TOME("Magical");

    private final String damageType;

    WeaponType(String damageType) {
        this.damageType = damageType;
    }

    public String getDamageType() {
        return damageType;
    }
}