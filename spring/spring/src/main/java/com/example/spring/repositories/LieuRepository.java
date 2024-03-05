package com.example.spring.repositories;

import com.example.spring.entities.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LieuRepository  extends JpaRepository<Lieu,Long> {

    @Query("SELECT s from Lieu s where s.id = ?1")
    Optional<Lieu> findLieuBy(Long id);
}
