package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AlchemistTest {

    @Test
    public void testAlchemyAndPotionSkills() {
        Alchemist alchemist = new Alchemist("Alkemist", 100, 1, Race.ELF);
        assertEquals(6, alchemist.getAlchemySkill(), "Alkemi färdigheter borde vara 6 för nivå 1 ELF");
        assertEquals(7, alchemist.getPotionCraftingSkill(), "potion tillverkning skill borde vara 7 for nivå 1 ELF");
    }

    @Test
    public void testBombThrowingSkills() {
        Alchemist alchemist = new Alchemist("Alkemist", 100, 1, Race.HUMAN);
        assertEquals(4, alchemist.getBombThrowingSkill(), "bombkastnings skill borde vara 4 för nivå 1 HUMAN");
    }

    @Test
    public void testLevelUpSkills() {
        Alchemist alchemist = new Alchemist("Alkemist", 100, 1, Race.HUMAN);
        alchemist.setLevel(2);

        // Testa att alkemi-färdigheten har ökat korrekt vid nivå 2 för en HUMAN
        assertEquals(8, alchemist.getAlchemySkill(), "Alkemi skill borde vara 8 i nivå 2 för HUMAN");
        assertEquals(10, alchemist.getPotionCraftingSkill(), "potion tillverkning skill borde vara 10 för HUMAN");
        assertEquals(6, alchemist.getBombThrowingSkill(), "bombkastnings skill borde vara 6");

    }
}
