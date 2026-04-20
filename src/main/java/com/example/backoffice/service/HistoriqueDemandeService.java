package com.example.backoffice.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.*;
import com.example.backoffice.repository.HistoriqueDemandeRepository;

@Service
public class HistoriqueDemandeService {
    
    @Autowired
    private HistoriqueDemandeRepository historiqueDemandeRepository;

    public HistoriqueDemande save(HistoriqueDemande historiqueDemande) {
        if(historiqueDemande == null) {
            throw new IllegalArgumentException("L'historique de demande n'existe pas");
        }
        return historiqueDemandeRepository.save(historiqueDemande);
    }

    public HistoriqueDemande create(Demande demande, StatutDemande statutDemande) {
        HistoriqueDemande historique = new HistoriqueDemande();
        historique.setDemande(demande);
        historique.setStatutDemande(statutDemande);
        historique.setDateChangement(LocalDate.now());
        historique.setCommentaire("Demande créée");
        return historique;
    }
}
