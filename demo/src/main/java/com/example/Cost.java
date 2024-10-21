package com.example;

import java.util.Map;
import java.util.HashMap;

public class Cost {

    private double money;
    private Map<String, Integer> materials;

    public Cost(double money, Map<String, Integer> materials) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative.");
        }
        if (materials == null || materials.isEmpty()) {
            throw new IllegalArgumentException("Materials cannot be null or empty.");
        }
        this.money = money;
        this.materials = new HashMap<>(materials);
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative.");
        }
        this.money = money;
    }

    public Map<String, Integer> getMaterials() {
        return new HashMap<>(materials);
    }

    public void addMaterial(String material, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Material quantity must be greater than zero.");
        }
        materials.merge(material, quantity, Integer::sum);
    }

    public void removeMaterial(String material, int quantity) {
        if (!materials.containsKey(material)) {
            throw new IllegalArgumentException("Material does not exist.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Material quantity must be greater than zero.");
        }
        int currentQuantity = materials.get(material);
        if (quantity > currentQuantity) {
            throw new IllegalArgumentException("Quantity exceeds available material.");
        }

        int newQuantity = currentQuantity - quantity;
        if (newQuantity == 0) {
            materials.remove(material);
        } else {
            materials.put(material, newQuantity);
        }
    }
}