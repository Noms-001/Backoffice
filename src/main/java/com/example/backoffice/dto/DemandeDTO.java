package com.example.backoffice.dto;

import lombok.Data;

@Data
public class DemandeDTO {
    Long id;
    String nomDemandeur;
    String prenomDemandeur;
    String categorieDemande;
    String statutDemande;
    String typeDemande;
    String dateDemande;
}
