package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ItemTest {

    private static final String DEFAULT_NAME = "Iron Ingot";
    private static final int DEFAULT_QUANTITY = 5;
    private static final ItemType DEFAULT_ITEM_TYPE = ItemType.SMITHING_STONE;

    @Test
    public void testCreateItem() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        assertEquals(DEFAULT_NAME, item.getName());
        assertEquals(DEFAULT_QUANTITY, item.getQuantity());
        assertEquals(DEFAULT_ITEM_TYPE, item.getItemType());
    }

    @Test
    public void testCreateItemWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(null, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        }, "Creating an item with null name should throw an IllegalArgumentException.");
    }

    @Test
    public void testCreateItemWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("", DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        }, "Creating an item with empty name should throw an IllegalArgumentException.");
    }

    @Test
    public void testCreateItemWithNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(DEFAULT_NAME, -1, DEFAULT_ITEM_TYPE);
        }, "Creating an item with negative quantity should throw an IllegalArgumentException.");
    }

    @Test
    public void testSetQuantity() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        item.setQuantity(10);
        assertEquals(10, item.getQuantity(), "Setting quantity should update the quantity.");
    }

    @Test
    public void testSetQuantityToNegative() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        assertThrows(IllegalArgumentException.class, () -> {
            item.setQuantity(-1);
        }, "Setting quantity to negative should throw an IllegalArgumentException.");
    }

    @Test
    public void testAddQuantity() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        item.addQuantity(5);
        assertEquals(DEFAULT_QUANTITY + 5, item.getQuantity(), "Adding quantity should increase the quantity.");
    }

    @Test
    public void testAddNegativeQuantity() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        assertThrows(IllegalArgumentException.class, () -> {
            item.addQuantity(-1);
        }, "Adding negative quantity should throw an IllegalArgumentException.");
    }

    @Test
    public void testRemoveQuantity() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        item.removeQuantity(3);
        assertEquals(DEFAULT_QUANTITY - 3, item.getQuantity(), "Removing quantity should decrease the quantity.");
    }

    @Test
    public void testRemoveNegativeQuantity() {
        Item item = new Item(DEFAULT_NAME, DEFAULT_QUANTITY, DEFAULT_ITEM_TYPE);
        assertThrows(IllegalArgumentException.class, () -> {
            item.removeQuantity(-1);
        }, "Removing negative quantity should throw an IllegalArgumentException.");
    }
}