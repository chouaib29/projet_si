package com.example.spring.services.impl;

import com.example.spring.dtos.EvenementDTO;
import com.example.spring.entities.Evenement;
import com.example.spring.entities.Lieu;
import com.example.spring.repositories.EvenementRepository;
import com.example.spring.repositories.LieuRepository;
import com.example.spring.repositories.MembreRepository;
import com.example.spring.services.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EvenementServiceImpl implements EvenementService {
    private final EvenementRepository evenementRepository;
    private  final MembreRepository membreRepository;
    private  final LieuRepository lieuRepository;

    @Autowired
    public EvenementServiceImpl(EvenementRepository evenementRepository, MembreRepository membreRepository, LieuRepository lieuRepository) {
        this.evenementRepository = evenementRepository;
        this.membreRepository = membreRepository;
        this.lieuRepository = lieuRepository;
    }

    /**
     * Récupère la liste de tous les événements.
     *
     * @return une liste d'événements.
     */
    public List<Evenement> getEvenements(){
        return evenementRepository.findAll();
    }



    /**
     * Ajoute un nouvel événement à la base de données.
     * Vérifie si l'événement existe déjà avant de l'ajouter.
     *
     * @param evenementDTO l'événement à ajouter.
     * @throws IllegalStateException si l'événement existe déjà.
     */
    @Override
    public EvenementDTO addNewEvent(EvenementDTO evenementDTO) {
        Evenement newEvent = evenementRepository.save(EvenementDtoToEntity(evenementDTO));

        // Vérification des conflits avec les événements existants
        List<EvenementDTO> existingEvents = getAllEvents();
        for (EvenementDTO existingEvent : existingEvents) {
            // Vérification du lieu
            if (existingEvent.getLieu_id().equals(evenementDTO.getLieu_id())) {
                // Vérification des plages horaires
                if (isTimeConflict(existingEvent, evenementDTO)) {
                    throw new IllegalArgumentException("Le nouvel événement chevauche avec un événement existant.");
                }
            }
        }

        return EvenementEntityToDao(newEvent);
    }

    public boolean isTimeConflict(EvenementDTO event1, EvenementDTO event2) {
        Date debut1 = event1.getDate_heure();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(debut1);
        calendar1.add(Calendar.MINUTE, (int) (event1.getDuree() * 60)); // Ajoute la durée à l'heure de début
        Date fin1 = calendar1.getTime();

        Date debut2 = event2.getDate_heure();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(debut2);
        calendar2.add(Calendar.MINUTE, (int) (event2.getDuree() * 60)); // Ajoute la durée à l'heure de début
        Date fin2 = calendar2.getTime();

        // Vérification des plages horaires
        return (debut1.before(fin2) && fin1.after(debut2)) || (debut2.before(fin1) && fin2.after(debut1));
    }

    public boolean isTimeConflict(Evenement event1, Evenement event2) {
        EvenementDTO dto1 = EvenementEntityToDao(event1);
        EvenementDTO dto2 = EvenementEntityToDao(event2);

        // Appelle la première méthode avec les objets EvenementDTO convertis
        return isTimeConflict(dto1, dto2);
    }

    /**
     * Supprime un événement de la base de données par son identifiant.
     *
     * @param eventId l'identifiant de l'événement à supprimer.
     * @throws IllegalStateException si l'événement n'existe pas.
     */
    public void deleteEvent(Long eventId) {
        boolean exists = evenementRepository.existsById(eventId);
        if(!exists){
            throw new IllegalStateException("student with id "+ eventId + " does not exists");
        }
        evenementRepository.deleteById(eventId);
        System.out.println("evenement " + eventId + " supprimé avec succées !");
    }

    /**
     * Met à jour les détails d'un événement existant.
     *
     * @param eventId l'identifiant de l'événement à mettre à jour.
     * @param name le nouveau nom de l'événement.
     * @param date_heure la nouvelle date et heure de l'événement.
     * @param lieu_id le nouvel identifiant du lieu de l'événement.
     * @param nbMaxParticipants le nouveau nombre maximum de participants.
     * @throws IllegalStateException si l'événement n'existe pas.
     */
    public void updateEvent(Long eventId, String name, Date date_heure, Long lieu_id, Integer nbMaxParticipants) {
        Evenement evenement = evenementRepository.findById(eventId)
                .orElseThrow(() -> new IllegalStateException("Evenement with id " + eventId + " does not exist"));

        if (name != null && !name.isEmpty() && !Objects.equals(name, evenement.getNom())) {
            evenement.setNom(name);
        }
        if (date_heure != null && !Objects.equals(date_heure, evenement.getDateHeure())) {
            evenement.setDateHeure(date_heure);
        }
        if(lieu_id != null){
            evenement.setLieu(lieu_id);
        }
        if (nbMaxParticipants != null && !nbMaxParticipants.equals(evenement.getNombreMaxParticipants())) {
            evenement.setNombreMaxParticipants(nbMaxParticipants);
        }
        evenementRepository.save(evenement);
        System.out.println("evenement " + eventId + " mis à jour avec succées !");
    }


    /**
     * Récupère la liste de tous les evenements.
     * @return une liste de tous les evenements existants.
     */
    @Override
    public List<EvenementDTO> getAllEvents() {
        List<EvenementDTO> evenementDtosList = new ArrayList<>();
        List<Evenement> concertList = evenementRepository.findAll();
        for (Evenement concert : concertList){
            evenementDtosList.add(EvenementEntityToDao(concert));
        }
        return evenementDtosList;
    }

    /**
     * Convertit un objet Evenement en un objet EvenementDTO.
     * @param evenement l'objet Evenement à convertir
     * @return un objet EvenementDTO correspondant à l'objet Evenement converti
     */
    private EvenementDTO EvenementEntityToDao(Evenement evenement){
        EvenementDTO evenementDTO = new EvenementDTO();
        evenementDTO.setId_evnt(evenement.getId());
        evenementDTO.setDate_heure(evenement.getDateHeure());
        evenementDTO.setDuree(evenement.getDuree());
        evenementDTO.setDuree(evenement.getDuree());
        evenementDTO.setLieu_id(evenement.getLieu().getId());
        evenementDTO.setNombreMaxParticipants(evenement.getNombreMaxParticipants());
        return evenementDTO;
    }

    /**
     * Convertit un objet EvenementDTO en un objet Evenement.
     * @param evenementDTO l'objet EvenementDTO à convertir
     * @return un objet Evenement correspondant à l'objet Evenement converti
     */
    private Evenement EvenementDtoToEntity(EvenementDTO evenementDTO) {
        Evenement evenement = new Evenement();
        evenement.setId(evenementDTO.getId_evnt());
        evenement.setDateHeure(evenementDTO.getDate_heure());
        evenement.setDuree(evenementDTO.getDuree());
        evenement.setNombreMaxParticipants(evenementDTO.getNombreMaxParticipants());

        // create new SoireeEntity and set its ID
        Lieu lieu = new Lieu();
        lieu.setId(evenementDTO.getLieu_id());
        evenement.setLieu(lieu.getId());


        return evenement;
    }
}
