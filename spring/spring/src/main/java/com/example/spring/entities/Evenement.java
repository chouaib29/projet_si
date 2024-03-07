package com.example.spring.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;


@Entity
// 2 evnt ne peuvent pas avoir lieu en même temps et au même lieu
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evnt") // Spécifie le nom de la colonne
    private Long id_evnt;

    @Column(name = "nom") // Spécifie le nom de la colonne
    private String nom;

    @Column(name = "date_heure", nullable = false) // Spécifie le nom de la colonne
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date_heure; // For date and time

    @Column(name = "duree") // Spécifie le nom de la colonne
    private double duree;

    @ManyToOne
    @JoinColumn(name = "lieu_id", nullable = false) // Clé étrangère
    private Lieu lieu_id;

    @Column(name = "nombre_max_participants") // Spécifie le nom de la colonne
    private Integer nombreMaxParticipants;

    @ManyToMany
    @JoinTable(
            name = "inscription_evenement_membre",
            joinColumns = @JoinColumn(name = "id_evnt"),
            inverseJoinColumns = @JoinColumn(name = "id_membre")
    )
    @JsonIgnore // To prevent the infinite loop
    private Set<Membre> membres;

    // Constructors
    public Evenement() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.JUNE, 12);
        Date date = calendar.getTime();
        this.nom = "Événement de Printemps";
        this.date_heure = date;
        this.duree = 2.5;
        this.lieu_id = new Lieu();
        this.nombreMaxParticipants = 15;
    }

    public Evenement(Long id_evnt, String nom, Date date_heure,
                     double duree, Lieu lieu_id, Integer nombreMaxParticipants){
        this.id_evnt = id_evnt;
        this.nom = nom;
        this.date_heure = date_heure;
        this.duree = duree;
        this.lieu_id = lieu_id;
        this.nombreMaxParticipants = nombreMaxParticipants;
    }
    // Getters
    public Long getId() {
        return id_evnt;
    }

    public String getNom() { return nom; }

    public Date getDateHeure() {
        return date_heure;
    }

    public double getDuree() { return duree; }

    public Lieu getLieu() {
        return lieu_id;
    }

    public Integer getNombreMaxParticipants() {
        return nombreMaxParticipants;
    }

    // Setters
    public void setId(Long id) {
        this.id_evnt = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateHeure(Date date_heure) {
        this.date_heure = date_heure;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public void setLieu(Long lieu_id) {
        this.lieu_id.setId(lieu_id);
    }

    public void setNombreMaxParticipants(Integer nombreMaxParticipants) {
        this.nombreMaxParticipants = nombreMaxParticipants;
    }

    public Set<Membre> getMembres() {
        return membres;
    }
}


