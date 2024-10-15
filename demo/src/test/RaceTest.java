
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class RaceTest {
    
    @Test
    public void testHumanRaceAttributes(){
        Race human = Race.HUMAN;
        assertAll(
            () -> assertEquals(2, human.getStrength()),
            () -> assertEquals(1, human.getSpeed()),
            () -> assertEquals(2, human.getIntelligence())
        );
    }

    @Test
    public void testElfRaceAttributes(){
        Race elf = Race.ELF;
        assertAll(
            () -> assertEquals(1, elf.getStrength()),
            () -> assertEquals(2, elf.getSpeed()),
            () -> assertEquals(3, elf.getIntelligence())
        );
    }

    @Test
    public void testGoblinRaceAttributes(){
        Race goblin = Race.GOBLIN;
        assertAll(
            () -> assertEquals(3, goblin.getStrength()),
            () -> assertEquals(1, goblin.getSpeed()),
            () -> assertEquals(1, goblin.getIntelligence())
        );
    }
}
