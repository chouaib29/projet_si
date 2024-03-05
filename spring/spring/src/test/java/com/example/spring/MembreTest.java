package com.example.spring;
import com.example.spring.entities.Membre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MembreTest {
    @Test
    public void testGetterSetter() {
        Membre membre = new Membre();

        // Setters
        membre.setId(1L);
        membre.setPrenom("Chouaib");
        membre.setNom("Douiloi");
        membre.setAge(23);
        membre.setAdresse("20 rue machin Machin Machine 29200, Brest");
        membre.setMotDePasse("Password123456789.!");

        // Getters and Assertions
        assertEquals(1L, membre.getId());
        assertEquals("Chouaib", membre.getPrenom());
        assertEquals("Douiloi", membre.getNom());
        assertEquals(23, membre.getAge());
        assertEquals("20 rue machin Machin Machine 29200, Brest", membre.getAdresse());
        assertEquals("Password123456789.!", membre.getMotDePasse());
    }

    @Test
    public void testConstructeurParDefaut(){
        Membre membre = new Membre();

        assertNull(membre.getId());
        assertNull(membre.getPrenom());
        assertNull(membre.getNom());
        assertNull(membre.getAge());
        assertNull(membre.getAdresse());
        assertNull(membre.getMotDePasse());
    }

    @Test
    public void testConstructeurParam() {
        String nom = "Moetaz";
        String prenom = "Chouikha";
        Integer age = 25;
        String adresse = "Ceci est une adresse, rue Machine, quartier machin, 29300 Le Conquet";
        String motDePasse = "TestMotDePasse";

        Membre membre = new Membre(nom, prenom, age, adresse, motDePasse);

        assertNull(membre.getId()); // On a pas définit un Id Donc Il est null.
        assertEquals(nom, membre.getNom());
        assertEquals(prenom, membre.getPrenom());
        assertEquals(age, membre.getAge());
        assertEquals(adresse, membre.getAdresse());
        assertEquals(motDePasse, membre.getMotDePasse());
    }
}
