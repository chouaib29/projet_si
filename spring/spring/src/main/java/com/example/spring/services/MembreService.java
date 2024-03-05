package com.example.spring.services;

import com.example.spring.entities.Membre;
import java.util.List;

public interface MembreService {
    List<Membre> getMembre();
    void addNewMembre(Membre membre);
    void deleteMembre(Long membreId);
    void updateMembre(Long membreId, String nom, String prenom, String adresse, Integer age);
}
