package com.example.backoffice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.*;
import com.example.backoffice.repository.HistoriqueDemandeRepository;
import com.example.backoffice.repository.StatutDemandeRepository;

@Service
public class HistoriqueDemandeService {
    
    @Autowired
    private HistoriqueDemandeRepository historiqueDemandeRepository;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    public HistoriqueDemande save(HistoriqueDemande historiqueDemande) {
        if(historiqueDemande == null) {
            throw new IllegalArgumentException("L'historique de demande n'existe pas");
        }
        return historiqueDemandeRepository.save(historiqueDemande);
    }

    public HistoriqueDemande create(Demande demande, Long idStatutDemande, String commentaire) {
        StatutDemande statut = statutDemandeRepository
                .findById(idStatutDemande)
                .orElseThrow(() -> new RuntimeException("StatutDemande introuvable"));

        HistoriqueDemande historique = new HistoriqueDemande();
        historique.setDemande(demande);
        historique.setStatutDemande(statut);
        historique.setDateChangement(LocalDateTime.now());
        historique.setCommentaire(commentaire);
        return save(historique);
    }

}
