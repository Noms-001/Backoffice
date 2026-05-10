package com.example.backoffice.dto;

import com.example.backoffice.entity.Demandeur;

import lombok.Data;

@Data
public class DemandeurDTO {

    private String nom;
    private String prenom;

    public DemandeurDTO(Demandeur demandeur) {
        this.nom = demandeur.getNom();
        this.prenom = demandeur.getPrenom();
    }
}
