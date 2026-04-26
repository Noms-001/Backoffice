package com.example.backoffice.dto;

import lombok.Data;

@Data
public class DemandeDTO {
    Long id;
    String nomDemandeur;
    String prenomDemandeur;
    String numeroPasseport;
    String numeroVisaTransformable;
    String categorieDemande;
    String statutDemande;
    String dateDemande;
}
