package com.example.spring.services;

import com.example.spring.dtos.EvenementDTO;
import com.example.spring.entities.Evenement;
import java.util.Date;
import java.util.List;

public interface EvenementService {
    List<Evenement> getEvenements();
    EvenementDTO addNewEvent(EvenementDTO evenementDTO);
    void deleteEvent(Long eventId);
    void updateEvent(Long eventId, String name, Date date_heure, Long lieu_id, Integer nbMaxParticipants);

    List<EvenementDTO> getAllEvents();
}
