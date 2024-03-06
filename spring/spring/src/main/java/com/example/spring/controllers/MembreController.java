package com.example.spring.controllers;

import com.example.spring.dtos.MembreDTO;
import com.example.spring.entities.Membre;
import com.example.spring.services.impl.MembreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path = "api/v1/membre")
public class MembreController {

    private final MembreServiceImpl membreService;

    @Autowired
    public MembreController(MembreServiceImpl membreService){
        this.membreService = membreService;
    }

    @GetMapping("/lister")
    public List<MembreDTO> getMembreDTO() {
        return membreService.getMembreDTO();
    }

    @GetMapping("/chercherParNom")
    public Membre getMembreParNom(@RequestParam String nom) {
        return membreService.getMembreParNom(nom);
    }

    @PostMapping
    public void registerNewMembre(@RequestBody Membre membre) {
        membreService.addNewMembre(membre);
    }

    @DeleteMapping(path ="{membreId}")
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

    @PostMapping("/registerToEvent")
    public void registerMembreToEvent(@RequestParam Long membreId, @RequestParam Long eventId) {
        membreService.registerMembreToEvent(membreId, eventId);
    }

}
