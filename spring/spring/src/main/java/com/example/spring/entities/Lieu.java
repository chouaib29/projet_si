package com.example.spring.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Lieu")
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieu_id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "capaciteAccueil", nullable = false)
    private int capaciteAccueil;

    // Constructeurs

    public Lieu() {

    }

    public Lieu(String nom, String adresse, int capaciteAccueil) {
        this.nom = nom;
        this.adresse = adresse;
        this.capaciteAccueil = capaciteAccueil;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCapaciteAccueil() {
        return capaciteAccueil;
    }

    public void setCapaciteAccueil(int capaciteAccueil) {
        this.capaciteAccueil = capaciteAccueil;
    }


}

