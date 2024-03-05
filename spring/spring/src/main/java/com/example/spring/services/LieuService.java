package com.example.spring.services;

import com.example.spring.entities.Lieu;
import java.util.List;

public interface LieuService {
    List<Lieu> getAllLieux();
    Lieu getLieuById(Long id);
    Lieu saveLieu(Lieu lieu);
    Lieu updateLieu(Long id, Lieu lieu);
    void deleteLieu(Long id);
}
