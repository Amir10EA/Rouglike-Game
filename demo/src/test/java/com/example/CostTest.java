package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;

public class CostTest {

    private static final double DEFAULT_MONEY = 100.0;
    private static final double NEGATIVE_MONEY = -100.0;
    private Map<String, Integer> defaultMaterials;
    private Cost defaultCost;

    private Cost createDefaultCost() {
        defaultMaterials = new HashMap<>(Map.of("Iron Ingot", 5));
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
        defaultCost.addMaterial("Wood", 3);
        assertEquals(3, defaultCost.getMaterials().get("Wood"));
    }

    @Test
    public void testRemoveMaterial() {
        defaultCost.removeMaterial("Iron Ingot", 3);
        assertEquals(2, defaultCost.getMaterials().get("Iron Ingot"));
    }

    @Test
    public void testChangeMoney() {
        defaultCost.setMoney(150.0);
        assertEquals(150.0, defaultCost.getMoney());
    }

    @Test
    public void testMoneyCannotBeNegative() {
        Map<String, Integer> materials = Map.of("Iron Ingot", 5, "Wood", 2);

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
            new Cost(DEFAULT_MONEY, new HashMap<>());
        });
    }

    @Test
    public void testAddMaterialQuantityCannotBeZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.addMaterial("Wood", 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.addMaterial("Wood", -1);
        });
    }

    @Test
    public void testRemoveMaterialQuantityCannotBeZeroOrNegative() {

        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial("Iron Ingot", 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial("Iron Ingot", -1);
        });
    }

    @Test
    public void testRemoveMaterialKeyDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial("Wood", 1);
        });
    }

    @Test
    public void testRemoveMaterialQuantityExceedsAvailable() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultCost.removeMaterial("Iron Ingot", 10);
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