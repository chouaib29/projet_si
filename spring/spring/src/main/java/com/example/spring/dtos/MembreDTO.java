package com.example.spring.dtos;

import lombok.Data;

@Data
public class MembreDTO {


    private Long id;
    private String nom;
    private String prenom;
    private Integer age;
    private String adresse;
}
