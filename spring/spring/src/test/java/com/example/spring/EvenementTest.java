package com.example.spring;
import com.example.spring.entities.Evenement;
import com.example.spring.entities.Lieu;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class EvenementTest {
    @Test
    public void testGetterSetter() {
        Evenement evenement = new Evenement();

        evenement.setId(1L);
        evenement.setNom("Event1");
        evenement.setDateHeure(new Date());
        evenement.setDuree(3.0);
        evenement.setLieu(new Lieu().getId());
        evenement.setNombreMaxParticipants(20);


        assertEquals(1L, evenement.getId());
        assertEquals("Event1", evenement.getNom());
        assertNotNull(evenement.getDateHeure());
        assertEquals(3.0, evenement.getDuree());
        assertNotNull(evenement.getLieu());
        assertEquals(20, evenement.getNombreMaxParticipants());

    }

    @Test
    public void testDefaultConstructor() {
        Evenement evenement = new Evenement();

        assertNull(evenement.getId());
        assertEquals("Événement de Printemps", evenement.getNom());
        assertNotNull(evenement.getDateHeure());
        assertEquals(2.5, evenement.getDuree());
        assertNotNull(evenement.getLieu());
        assertEquals(15, evenement.getNombreMaxParticipants());
    }

    @Test
    public void testParameterizedConstructor() {
        Date testDate = new Date();
        Lieu testLieu = new Lieu();

        Evenement evenement = new Evenement(1L, "Custom Event", testDate, 4.0, testLieu, 30);

        assertEquals(1L, evenement.getId());
        assertEquals("Custom Event", evenement.getNom());
        assertEquals(testDate, evenement.getDateHeure());
        assertEquals(4.0, evenement.getDuree());
        assertEquals(testLieu, evenement.getLieu());
        assertEquals(30, evenement.getNombreMaxParticipants());

    }


}
