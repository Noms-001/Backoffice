package com.example.backoffice.service;

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

}
