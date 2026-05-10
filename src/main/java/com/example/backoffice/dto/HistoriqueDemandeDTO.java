package com.example.backoffice.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.example.backoffice.entity.HistoriqueDemande;

@Data
public class HistoriqueDemandeDTO {

    private String statut;
    private LocalDateTime dateChangement;

    public HistoriqueDemandeDTO(HistoriqueDemande historique) {
        this.statut = historique.getStatutDemande().getLibelle();
        this.dateChangement = historique.getDateChangement();
    }
}
