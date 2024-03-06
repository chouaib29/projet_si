package com.example.spring.dtos;

import com.example.spring.entities.Lieu;
import lombok.Data;

import java.util.Date;

@Data
public class EvenementDTO {
    private Long id_evnt;
    private String nom;
    private Date date_heure;
    private double duree;
    private Lieu lieu_id;
    private Integer nombreMaxParticipants;

}
