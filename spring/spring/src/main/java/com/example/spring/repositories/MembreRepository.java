package com.example.spring.repositories;

import com.example.spring.entities.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

    Optional<Membre> findMembreByNomAndPrenom(String nom, String Prenom);
    boolean existsByNomAndPrenom(String nom, String prenom);
    @Query("SELECT s FROM Membre s WHERE LOWER(s.nom) = LOWER(?1)")
    List<Membre> findByNom(String nom);
}
