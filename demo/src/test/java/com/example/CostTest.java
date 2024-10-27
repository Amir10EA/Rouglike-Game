package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class CostTest {

    private static final double DEFAULT_MONEY = 100.0;
    private static final double NEGATIVE_MONEY = -100.0;
    private List<Item> defaultMaterials;
    private Cost defaultCost;

    private Cost createDefaultCost() {
        defaultMaterials = List.of(new Item("Iron Ingot", 5, ItemType.SMITHING_STONE));
        return new Cost(DEFAULT_MONEY, defaultMaterials);
    }

    @BeforeEach
    public void setUp() {
        defaultCost = createDefaultCost();
    }

    @Test
    public void testCreateCost() {
        assertEquals(DEFAULT_MONEY, defaultCost.getMoney());
        assertEquals(defaultMaterials, defaultCost.getMaterials());
    }

    @Test
    public void testAddMaterial() {
        defaultCost.addMaterial(new Item("Wood", 3, ItemType.SMITHING_STONE));
        assertEquals(3, defaultCost.getMaterials().stream().filter(item -> item.getName().equals("Wood")).findFirst().get().getQuantity());
    }

    @Test
    public void testRemoveMaterial() {
        defaultCost.removeMaterial(new Item("Iron Ingot", 3, ItemType.SMITHING_STONE));
        assertEquals(2, defaultCost.getMaterials().stream().filter(item -> item.getName().equals("Iron Ingot")).findFirst().get().getQuantity());
    }

    @Test
    public void testChangeMoney() {
        defaultCost.setMoney(150.0);
        assertEquals(150.0, defaultCost.getMoney());
    }

    @Test
    public void testMoneyCannotBeNegative() {
        List<Item> materials = List.of(new Item("Iron Ingot", 5, ItemType.SMITHING_STONE), new Item("Wood", 2, ItemType.SMITHING_STONE));

        assertThrows(IllegalArgumentException.class, () -> {
            new Cost(NEGATIVE_MONEY, materials);
        });
    }

    @Test
    public void testMaterialsCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cost(DEFAULT_MONEY, null);
        });
    }

    @Test
    public void testMaterialsCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cost(DEFAULT_MONEY, List.of());
        });
    }

    @Test
    public void testAddMaterialQuantityCannotBeZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.addMaterial(new Item("Wood", 0, ItemType.SMITHING_STONE));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.addMaterial(new Item("Wood", -1, ItemType.SMITHING_STONE));
        });
    }

    @Test
    public void testRemoveMaterialQuantityCannotBeZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial(new Item("Iron Ingot", 0, ItemType.SMITHING_STONE));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial(new Item("Iron Ingot", -1, ItemType.SMITHING_STONE));
        });
    }

    @Test
    public void testRemoveMaterialKeyDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial(new Item("Wood", 1, ItemType.SMITHING_STONE));
        });
    }

    @Test
    public void testRemoveMaterialQuantityExceedsAvailable() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial(new Item("Iron Ingot", 10, ItemType.SMITHING_STONE));
        });
    }

    @Test
    public void testChangeMoneyCost() {
        defaultCost.setMoney(200.0);
        assertEquals(200.0, defaultCost.getMoney());

        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.setMoney(-50.0);
        });
    }
}