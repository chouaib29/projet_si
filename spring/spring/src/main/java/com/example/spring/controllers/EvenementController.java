    package com.example.spring.controllers;


    import com.example.spring.dtos.EvenementDTO;
    import com.example.spring.entities.Evenement;
    import com.example.spring.services.impl.EvenementServiceImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.Date;
    import java.util.List;

    @RestController
    @RequestMapping(path = "api/v1/evenement")
    public class EvenementController {
        private final EvenementServiceImpl evenementService;

        @Autowired // pour dire que studentService sera automatiquement injecté dans ce constructeur
        public EvenementController(EvenementServiceImpl evenementService) {
            this.evenementService = evenementService;
        }

        @GetMapping("/getAllEvenement")
        public List<Evenement> getEvenements(){
            return evenementService.getEvenements();
        }

        @PostMapping("/insertEvenement")
        public void addNewEvent(@RequestBody EvenementDTO evenement) { // RequestBody prend la requete fournit par le
            evenementService.addNewEvent(evenement);                        // client et faire un mapping vers Student
        }

        @DeleteMapping(path = "/deleteEvenement/{eventId}")
        public void deleteEvent(@PathVariable("eventId") Long eventId){
            evenementService.deleteEvent(eventId);
        }

        @PutMapping(path = "/updateEvenement/{eventId}")
        public void updateEvent(
                @PathVariable("eventId") Long eventId,
                @RequestParam(required = false) String name,
                @RequestParam(required = false) Date date_heure,
                @RequestParam(required = false) Long lieu_id,
                @RequestParam(required = false) Integer nbMaxParticipants){
            evenementService.updateEvent(eventId, name, date_heure, lieu_id, nbMaxParticipants);
        }

    }
