package com.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuestGiver {
    private String name;
    private Quest currentQuest;

    public QuestGiver(String name, String environmentType) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.currentQuest = generateQuest(environmentType);
    }

    private Quest generateQuest(String environmentType) {
        List<Enemy> enemies = findEnemiesForEnvironment(environmentType);
        if (enemies.isEmpty()) {
            return null; // No enemies, no quest
        }
        Enemy targetEnemy = enemies.get(new Random().nextInt(enemies.size()));
        return new Quest("Defeat " + targetEnemy.getName(), targetEnemy);
    }

    private List<Enemy> findEnemiesForEnvironment(String environmentType) {
        // Implement logic to find enemies based on the environment type
        // This is a placeholder for the actual implementation
        // Example: return a list of enemies specific to the environmentType
        return List.of(); // Replace with actual enemy list
    }

    public void interact(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (currentQuest == null) {
            System.out.println(name + ": I have no quests for you right now.");
        } else {
            System.out.println(name + ": " + currentQuest.getDescription() + " Do you accept? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                System.out.println(name + ": Great! Come back to me once you have completed the quest.");
                player.setCurrentQuest(currentQuest);
            } else {
                System.out.println(name + ": Maybe next time.");
            }
        }
    }

    public void giveReward(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        Random random = new Random();
        InventoryItem rewardItem = generateRandomReward();
        int money = random.nextInt(100) + 50; // Random money between 50 and 150
        int experience = random.nextInt(50) + 25; // Random experience between 25 and 75

        player.addItem(rewardItem);
        player.gainExperience(experience);
        System.out.println(name + ": Here is your reward: " + rewardItem.getName() + ", " + money + " gold, and " + experience + " experience points.");
    }

    private InventoryItem generateRandomReward() {
        Random random = new Random();
        int rewardType = random.nextInt(3); // 0 for Weapon, 1 for Armor, 2 for Item

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
        return new Weapon("Random " + randomType.name(), 5.0, 100, 50, 1.5, randomType, new Cost(100, 50));
    }

    private Armor generateRandomArmor() {
        ArmorType[] armorTypes = ArmorType.values();
        ArmorType randomType = armorTypes[new Random().nextInt(armorTypes.length)];
        return new Armor("Random " + randomType.name(), 10.0, 100, 30, 20, randomType, new Cost(150, 75));
    }

    private Item generateRandomItem() {
        ItemType[] itemTypes = ItemType.values();
        ItemType randomType = itemTypes[new Random().nextInt(itemTypes.length)];
        return new Item("Random " + randomType.name(), 1, randomType);
    }

    public void checkQuestCompletion(Player player, Enemy enemy) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (enemy == null) {
            throw new IllegalArgumentException("Enemy cannot be null");
        }
        if (currentQuest != null && currentQuest.isCompleted(enemy)) {
            System.out.println(name + ": You have completed the quest!");
            giveReward(player);
            currentQuest = null; // Clear the current quest
        }
    }

    public String getName() {
        return name;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }
}

class Quest {
    private String description;
    private Enemy targetEnemy;

    public Quest(String description, Enemy targetEnemy) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (targetEnemy == null) {
            throw new IllegalArgumentException("Target enemy cannot be null");
        }
        this.description = description;
        this.targetEnemy = targetEnemy;
    }

    public String getDescription() {
        return description;
    }

    public Enemy getTargetEnemy() {
        return targetEnemy;
    }

    public boolean isCompleted(Enemy defeatedEnemy) {
        return targetEnemy.equals(defeatedEnemy);
    }
}