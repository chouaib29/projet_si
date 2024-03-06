package com.example.spring.controllers;

import com.example.spring.entities.Lieu;
import com.example.spring.services.impl.LieuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="api/v1/lieux")
public class LieuController {

    @Autowired
    private LieuServiceImpl lieuService;

    @GetMapping("/lister")
    public List<Lieu> getAllLieux() {
        return lieuService.getAllLieux();
    }

    @GetMapping("/{id}")
    public Lieu getLieuById(@PathVariable Long id) {
        return lieuService.getLieuById(id);
    }

    @PostMapping
    public Lieu saveLieu(@RequestBody Lieu lieu) {
        return lieuService.saveLieu(lieu);
    }

    @PutMapping("/{id}")
    public Lieu updateLieu(@PathVariable Long id, @RequestBody Lieu lieu) {
        return lieuService.updateLieu(id, lieu);
    }

    @DeleteMapping("/{id}")
    public void deleteLieu(@PathVariable Long id) {
        lieuService.deleteLieu(id);
    }
}
