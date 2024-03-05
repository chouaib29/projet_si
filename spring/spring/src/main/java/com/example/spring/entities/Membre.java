package com.example.spring.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Set;

@Entity
public class Membre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membre", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false)
    //@NotEmpty(message = "Le nom ne peut pas être vide")
    private String nom;

    @Column(name = "prenom", nullable = false)
    //@NotEmpty(message = "Le prénom ne peut pas être vide")
    private String prenom;

    @Column(name = "age")
    //@Min(value = 0, message = "L'âge doit être supérieur ou égal à 0")
    private Integer age;

    @Column(name = "adresse", nullable = false)
    //@NotEmpty(message = "L'adresse ne peut pas être vide")
    private String adresse;

    @Column(name = "mot_de_passe", nullable = false)
    //@NotEmpty(message = "Le mot de passe ne peut pas être vide")
    private String motDePasse;

    @ManyToMany(mappedBy = "membres")
    private Set<Evenement> evenementsInscrits;

    // Constructeurs, getters, setters

    public Membre() {
        // Constructeur par défaut
    }

    public Membre(String nom, String prenom, Integer age, String adresse, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
    }

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", adresse='" + adresse + '\'' +
                '}';
    }

    public Set<Evenement> getEvenements() {
        return evenementsInscrits;
    }
}



