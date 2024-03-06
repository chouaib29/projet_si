package com.example.spring.services;

import com.example.spring.dtos.MembreDTO;
import com.example.spring.entities.Membre;
import com.example.spring.repositories.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

public interface MembreService {
    List<MembreDTO> getMembreDTO();
    void addNewMembre(Membre membre);
    void deleteMembre(Long membreId);
    void updateMembre(Long membreId, String nom, String prenom, String adresse, Integer age);

    Membre getMembreParNom(String nom);
}
