package com.example.spring.repositories;

import com.example.spring.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository // cette interface elle est responsabe au accées au donées dans la BDD
public interface EvenementRepository extends JpaRepository<Evenement, Long>  {
    @Query("SELECT s from Evenement s where s.id_evnt = ?1")
    Optional<Evenement> findEvenementBy(Long id_evnt);

}
