package com.example.spring.services.impl;

import com.example.spring.entities.Membre;
import com.example.spring.repositories.MembreRepository;
import com.example.spring.services.MembreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service // or @Componant
public class MembreServiceImpl implements MembreService {

    private final MembreRepository membreRepository;

    @Autowired
    public MembreServiceImpl(MembreRepository membreRepository){
        this.membreRepository = membreRepository;
    }

    public List<Membre> getMembre() {
        return membreRepository.findAll();
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

}
