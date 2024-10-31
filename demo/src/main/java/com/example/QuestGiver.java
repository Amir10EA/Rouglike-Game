package com.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuestGiver {
    private String name;
    private Quest quest;

    public QuestGiver(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.quest = generateQuest();
    }

    public void interact(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        System.out.println(name + ": " + quest.getDescription() + " Do you accept? (yes/no)");
        
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            System.out.println(name + ": Great! Come back to me once you have completed the quest.");
            player.setCurrentQuest(quest);
        } else {
            System.out.println(name + ": Maybe next time.");
        }
    }

    private Quest generateQuest() {
        int numberOfEnemies = new Random().nextInt(4) + 2; // Random number between 2 and 5
        return new Quest("Defeat " + numberOfEnemies + " enemies", numberOfEnemies);
    }

    public void giveReward(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        Random random = new Random();
        InventoryItem rewardItem = generateRandomReward(random.nextInt(3)); // Control randomness for testing
        int money = random.nextInt(100) + 50; // Random money between 50 and 150
        int experience = random.nextInt(50) + 25; // Random experience between 25 and 75

        player.addItem(rewardItem);
        player.gainExperience(experience);
        System.out.println(name + ": Here is your reward: " + rewardItem.getName() + ", " + money + " gold, and " + experience + " experience points.");
    }

    public InventoryItem generateRandomReward(int rewardType) {
        switch (rewardType) {
            case 0:
                return generateRandomWeapon();
            case 1:
                return generateRandomArmor();
            case 2:
                return generateRandomItem();
            default:
                return new Item("Default Item", 1, ItemType.MAGICAL_STONE); // Fallback
        }
    }

    private Weapon generateRandomWeapon() {
        WeaponType[] weaponTypes = WeaponType.values();
        WeaponType randomType = weaponTypes[new Random().nextInt(weaponTypes.length)];
        List<Item> materials = List.of(new Item("Iron", 2, ItemType.SMITHING_STONE));
        Cost cost = new Cost(10.0, materials);
        return new Weapon("Random " + randomType.name(), 5.0, 100, 50, 1.5, randomType, cost);
    }

    private Armor generateRandomArmor() {
        ArmorType[] armorTypes = ArmorType.values();
        ArmorType randomType = armorTypes[new Random().nextInt(armorTypes.length)];
        List<Item> materials = List.of(new Item("Steel", 3, ItemType.SMITHING_STONE));
        Cost cost = new Cost(15.0, materials);
        return new Armor("Random " + randomType.name(), 10.0, 100, 30, 20, randomType, cost);
    }

    private Item generateRandomItem() {
        ItemType[] itemTypes = ItemType.values();
        ItemType randomType = itemTypes[new Random().nextInt(itemTypes.length)];
        return new Item("Random " + randomType.name(), 1, randomType);
    }

    public String getName() {
        return name;
    }

    public Quest getQuest() {
        return quest;
    }
}