package com.example.spring.repositories;

import com.example.spring.entities.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

    Optional<Membre> findMembreByNomAndPrenom(String nom, String Prenom);
    boolean existsByNomAndPrenom(String nom, String prenom);

}
