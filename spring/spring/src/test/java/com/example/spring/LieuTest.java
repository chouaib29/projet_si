package com.example.spring;
import com.example.spring.entities.Evenement;
import com.example.spring.entities.Lieu;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LieuTest {

    @Test
    public void testGettersSetters() {
        // Arrange
        Lieu lieu = new Lieu();

        // Setters
        lieu.setId(1L);
        lieu.setNom("Akrahm City");
        lieu.setAdresse("13 quartier chenain from the city and something else, this is a long test to see if we can store it or not");
        lieu.setCapaciteAccueil(50);

        // Getters and Assertions
        assertEquals(1L, lieu.getId());
        assertEquals("Akrahm City", lieu.getNom());
        assertEquals("13 quartier chenain from the city and something else, " +
                "this is a long test to see if we can store it or not", lieu.getAdresse());
        assertEquals(50, lieu.getCapaciteAccueil());
        assertNull(lieu.getEvenements());
    }

    @Test
    public void testConstructeurParDefaut() {
        Lieu lieu = new Lieu();

        assertNull(lieu.getId());
        assertNull(lieu.getNom());
        assertNull(lieu.getAdresse());
        assertEquals(0,lieu.getCapaciteAccueil());
        assertNull(lieu.getEvenements());
    }

    @Test
    public void testConstructeurParam() {
        String nom = "Der Chenan";
        String adresse = "12th avenue, Los angles, the city of angels. America";
        int capaciteAccueil = 100;

        Lieu lieu = new Lieu(nom, adresse, capaciteAccueil);

        assertNull(lieu.getId());
        assertEquals(nom, lieu.getNom());
        assertEquals(adresse, lieu.getAdresse());
        assertEquals(capaciteAccueil, lieu.getCapaciteAccueil());
        assertNull(lieu.getEvenements());
    }

    @Test
    public void testGetSetEvenements() {
        Lieu lieu = new Lieu();
        Set<Evenement> evenements = new HashSet<>();
        Evenement event1 = new Evenement();
        Evenement event2 = new Evenement();
        evenements.add(event1);
        evenements.add(event2);

        lieu.setEvenements(evenements);

        assertNotNull(lieu.getEvenements());
        assertEquals(evenements, lieu.getEvenements());
        assertTrue(lieu.getEvenements().contains(event1));
        assertTrue(lieu.getEvenements().contains(event2));

        Set<Evenement> newEvenements = new HashSet<>();
        Evenement event3 = new Evenement();
        newEvenements.add(event3);

        lieu.setEvenements(newEvenements);

        assertNotNull(lieu.getEvenements());
        assertEquals(newEvenements, lieu.getEvenements());
        assertFalse(lieu.getEvenements().contains(event1));
        assertFalse(lieu.getEvenements().contains(event2));
        assertTrue(lieu.getEvenements().contains(event3));
    }
}
