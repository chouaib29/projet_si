package com.example.spring.services.impl;

import com.example.spring.entities.Lieu;
import com.example.spring.repositories.LieuRepository;
import com.example.spring.services.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LieuServiceImpl implements LieuService {

    @Autowired
    private LieuRepository lieuRepository;

    /**
     * Récupère tous les lieux disponibles.
     *
     * @return une liste de tous les lieux.
     */
    public List<Lieu> getAllLieux() {
        return lieuRepository.findAll();
    }

    /**
     * Récupère un lieu spécifique par son identifiant.
     *
     * @param id L'identifiant du lieu à récupérer.
     * @return le lieu correspondant ou null si non trouvé.
     */
    public Lieu getLieuById(Long id) {
        return lieuRepository.findById(id).orElse(null);
    }

    /**
     * Enregistre un nouveau lieu dans la base de données.
     *
     * @param lieu Le lieu à enregistrer.
     * @return le lieu enregistré.
     */
    public Lieu saveLieu(Lieu lieu) {
        return lieuRepository.save(lieu);
    }

    /**
     * Met à jour les informations d'un lieu existant.
     *
     * @param id L'identifiant du lieu à mettre à jour.
     * @param lieu Les nouvelles informations du lieu.
     * @return le lieu mis à jour ou null si le lieu n'existe pas.
     */
    public Lieu updateLieu(Long id, Lieu lieu) {
        if (lieuRepository.existsById(id)) {
            lieu.setId(id);
            return lieuRepository.save(lieu);
        } else {
            return null;
        }
    }

    /**
     * Supprime un lieu de la base de données.
     *
     * @param id L'identifiant du lieu à supprimer.
     */
    public void deleteLieu(Long id) {
        lieuRepository.deleteById(id);
    }
}
