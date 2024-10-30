import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestGiverTest {
    private QuestGiver questGiver;
    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        questGiver = new QuestGiver("TestGiver", "forest");
        player = new Player("TestPlayer", 100, 10, Race.HUMAN);
        enemy = new Enemy("TestEnemy", 50, 5, Race.ORC, 10);
    }

    @Test
    public void testQuestGiverCreation() {
        assertNotNull(questGiver);
    }

    @Test
    public void testInvalidEnvironment() {
        QuestGiver invalidQuestGiver = new QuestGiver("TestGiver", "invalidEnvironment");
        assertNull(invalidQuestGiver.getCurrentQuest());
    }

    @Test
    public void testNameNotNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new QuestGiver(null, "forest"));
        assertThrows(IllegalArgumentException.class, () -> new QuestGiver("", "forest"));
    }

    @Test
    public void testNoEnemiesForEnvironment() {
        QuestGiver emptyQuestGiver = new QuestGiver("TestGiver", "emptyEnvironment");
        assertNull(emptyQuestGiver.getCurrentQuest());
    }

    @Test
    public void testInteractWithSuccessfulInteraction() {
        questGiver.interact(player);
        assertNotNull(player.getCurrentQuest());
    }

    @Test
    public void testInteractWithNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> questGiver.interact(null));
    }

    @Test
    public void testGiveRewardWithNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> questGiver.giveReward(null));
    }

    @Test
    public void testGiveRewardSuccessfulRun() {
        questGiver.giveReward(player);
        assertTrue(player.getExperience() > 0);
    }

    @Test
    public void testGenerateRandomRewardWeapon() {
        InventoryItem reward = questGiver.generateRandomReward();
        assertTrue(reward instanceof Weapon);
    }

    @Test
    public void testGenerateRandomRewardArmor() {
        InventoryItem reward = questGiver.generateRandomReward();
        assertTrue(reward instanceof Armor);
    }

    @Test
    public void testGenerateRandomRewardItem() {
        InventoryItem reward = questGiver.generateRandomReward();
        assertTrue(reward instanceof Item);
    }

    @Test
    public void testCheckQuestCompletionSuccessfulRun() {
        player.setCurrentQuest(new Quest("Defeat TestEnemy", enemy));
        questGiver.checkQuestCompletion(player, enemy);
        assertNull(player.getCurrentQuest());
    }

    @Test
    public void testCheckQuestCompletionWithNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> questGiver.checkQuestCompletion(null, enemy));
    }

    @Test
    public void testCheckQuestCompletionWithNullEnemy() {
        assertThrows(IllegalArgumentException.class, () -> questGiver.checkQuestCompletion(player, null));
    }
}