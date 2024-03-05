package com.example.spring.controllers;

import com.example.spring.entities.Membre;
import com.example.spring.services.impl.MembreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/membre")
public class MembreController {

    private final MembreServiceImpl membreService;

    @Autowired
    public MembreController(MembreServiceImpl membreService){
        this.membreService = membreService;
    }

    @GetMapping("/lister")
    public List<Membre> getMembre() {
        return membreService.getMembre();
    }
    @GetMapping("/chercherParNom")
    public Membre getMembreParNom(@RequestParam String nom) {
        return membreService.getMembreParNom(nom);
    }

    @PostMapping
    public void registerNewMembre(@RequestBody Membre membre) {
        membreService.addNewMembre(membre);
    }

    @DeleteMapping(path = "{membreId}")
    public void deleteMembre(@PathVariable("membreId") Long membreId) {
        membreService.deleteMembre(membreId);
    }

    @PutMapping(path = "{membreId}")
    public void updateMembre(
            @PathVariable("membreId") Long membreId,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String adresse,
            @RequestParam(required = false) Integer age) {
        membreService.updateMembre(membreId,
                nom,
                prenom,
                adresse,
                age);
    }
}
