package com.example;

import java.util.List;
import java.util.ArrayList;

public class Cost {

    private double money;
    private List<Item> materials;

    public Cost(double money, List<Item> materials) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative.");
        }
        if (materials == null || materials.isEmpty()) {
            throw new IllegalArgumentException("Materials cannot be null or empty.");
        }
        this.money = money;
        this.materials = new ArrayList<>(materials);
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

    public List<Item> getMaterials() {
        return new ArrayList<>(materials);
    }

    public void addMaterial(Item item) {
        if (item == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Material quantity must be greater than zero.");
        }
        for (Item material : materials) {
            if (material.getName().equals(item.getName())) {
                material.addQuantity(item.getQuantity());
                return;
            }
        }
        materials.add(item);
    }

    public void removeMaterial(Item item) {
        if (item == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Material quantity must be greater than zero.");
        }
        for (Item material : materials) {
            if (material.getName().equals(item.getName())) {
                if (item.getQuantity() > material.getQuantity()) {
                    throw new IllegalArgumentException("Cannot remove more than available quantity.");
                }
                material.removeQuantity(item.getQuantity());
                if (material.getQuantity() == 0) {
                    materials.remove(material);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Material does not exist.");
    }
}