package com.example.spring.services.impl;

import com.example.spring.dtos.MembreDTO;
import com.example.spring.entities.Evenement;
import com.example.spring.entities.Membre;
import com.example.spring.repositories.EvenementRepository;
import com.example.spring.repositories.MembreRepository;
import com.example.spring.services.EvenementService;
import com.example.spring.services.MembreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // or @Componant
public class MembreServiceImpl implements MembreService {

    private final MembreRepository membreRepository;
    private final EvenementRepository evenementRepository;
    private final EvenementService evenementService; // Needed for adding Membre to Event

    @Autowired
    public MembreServiceImpl(MembreRepository membreRepository, EvenementRepository evenementRepository, EvenementService evenementService){
        this.membreRepository = membreRepository;
        this.evenementRepository = evenementRepository;
        this.evenementService = evenementService;
    }

    public List<MembreDTO> getMembreDTO() {
        List<Membre> membres = membreRepository.findAll();
        return membres.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MembreDTO convertToDTO(Membre membre) {
        MembreDTO membreDTO = new MembreDTO();
        membreDTO.setNom(membre.getNom());
        membreDTO.setPrenom(membre.getPrenom());
        membreDTO.setAge(membre.getAge());
        membreDTO.setAdresse(membre.getAdresse());

        return membreDTO;
    }

    public void addNewMembre(Membre membre) {
        Optional<Membre> membreOptional = membreRepository.findMembreByNomAndPrenom(membre.getNom(), membre.getPrenom());
        if(membreOptional.isPresent()) {
            throw new IllegalStateException("Nom et prénom existent déjà");
        }
        boolean exists = membreRepository.existsByNomAndPrenom(membre.getNom(), membre.getPrenom());
        if(exists) {
            throw new IllegalStateException("Nom et prénom existent déjà");
        }
        membreRepository.save(membre);
    }

    public void deleteMembre(Long membreId) {
        boolean exists = membreRepository.existsById(membreId);
        if(!exists) {
            throw new IllegalStateException("Membre with is " + membreId + " doesn't exist");
        }
        membreRepository.deleteById(membreId);
    }

    @Transactional
    public void updateMembre(Long membreId, String nom, String prenom, String adresse, Integer age) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalStateException("Membre with id : " + membreId + " doesn't exist"));

        if(nom != null &&
                !nom.isEmpty() &&
                !Objects.equals(membre.getNom(), nom)) {
            membre.setNom(nom);
        }

        if(prenom != null &&
                !prenom.isEmpty() &&
                !Objects.equals(membre.getPrenom(), prenom)) {
            membre.setPrenom(prenom);
        }

        if(adresse != null &&
                !adresse.isEmpty() &&
                !Objects.equals(membre.getAdresse(), adresse)) {
            membre.setAdresse(adresse);
        }

        if((age != null) && (age > 0) && (!Objects.equals(membre.getAge(), age))){
            membre.setAge(age);
        }
        membreRepository.save(membre);
    }

    public Membre getMembreParNom(String nom){

        List<Membre> membres = membreRepository.findByNom(nom);

        // Si vous utilisez Spring Data JPA, vous pouvez définir une méthode findByNom dans votre repository
        // List<Membre> membres = membreRepository.findByNom(nom);

        // Gérez le cas où aucun membre n'est trouvé avec le nom spécifié
        if (membres.isEmpty()) {
            return null; // Ou vous pouvez lever une exception, selon vos besoins
        }

        // Retournez le premier membre trouvé avec le nom spécifié
        return membres.get(0);

    }

    public void registerMembreToEvent(Long membreId, Long eventId) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalStateException("Membre with id " + membreId + " doesn't exist"));

        Evenement event = evenementRepository.findById(eventId)
                .orElseThrow(() -> new IllegalStateException("Evenement with id " + eventId + " doesn't exist"));

        // Si l'évenement est déjà passé, throw exception
        if (event.getDateHeure().before(new Date())) {
            throw new IllegalStateException("Event with id " + eventId + " has already taken place");
        }

        // La capacité maximale est atteinte
        if (event.getMembres().size() >= event.getNombreMaxParticipants()) {
            throw new IllegalStateException("Event with id " + eventId + " is at capacity");
        }

        // Vérifier si le membre est déjà inscrit à un événement qui se chevauche
        if (membre.getEvenements().stream().anyMatch(existingEvent -> isTimeConflict(existingEvent, event))) {
            throw new IllegalStateException("Member with id " + membreId + " is already registered for an overlapping event");
        }

        // Tout est bon
        membre.getEvenements().add(event);
        event.getMembres().add(membre);

        membreRepository.save(membre);
        evenementRepository.save(event);
    }

    private boolean isTimeConflict(Evenement event1, Evenement event2) {
        return evenementService.isTimeConflict(event1, event2);
    }

}
