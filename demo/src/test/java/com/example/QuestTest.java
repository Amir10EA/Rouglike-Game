import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuestTest {
    private Enemy enemy = new Enemy("TestEnemy", 50, 5, Race.ORC, 10);

    @Test
    public void testValidQuest() {
        Quest quest = new Quest("Defeat TestEnemy", enemy);
        assertNotNull(quest);
        assertEquals("Defeat TestEnemy", quest.getDescription());
        assertEquals(enemy, quest.getTargetEnemy());
    }

    @Test
    public void testInvalidQuestWithNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Quest(null, enemy));
    }

    @Test
    public void testInvalidQuestWithEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Quest("", enemy));
    }

    @Test
    public void testInvalidQuestWithNullTargetEnemy() {
        assertThrows(IllegalArgumentException.class, () -> new Quest("Defeat TestEnemy", null));
    }
}