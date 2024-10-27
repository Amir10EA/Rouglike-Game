package com.example;

public class Item implements InventoryItem {
    private String name;
    private int quantity;
    private ItemType itemType;

    public Item(String name, int quantity, ItemType itemType) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.name = name;
        this.quantity = quantity;
        this.itemType = itemType;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void addQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.quantity += amount;
    }

    public void removeQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.quantity = Math.max(0, this.quantity - amount);
    }
}