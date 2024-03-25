package com.javarush.quest.anokhov.ownQuest.entity;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest extends TestCase {
    private Player player;

    @Before
    public void setUp() {
        player = new Player("TestPlayer");
    }

    @Test
    public void testFindPistol() {
        assertFalse(player.isPistol());
        player.findPistol();
        assertTrue(player.isPistol());
    }

    @Test
    public void testDecreaseChemicalProtection() {
        player.findChemicalProtection();
        int initialProtection = player.getChemicalProtection();
        player.decreaseChemicalProtection();
        assertEquals(initialProtection - 1, player.getChemicalProtection());
    }
}