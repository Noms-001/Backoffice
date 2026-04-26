package com.example.backoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.Demandeur;
import com.example.backoffice.repository.DemandeurRepository;

@Service
public class DemandeurService {

    @Autowired
    private DemandeurRepository demandeurRepository;

    public Demandeur save(Demandeur demandeur) {
        if (demandeur == null) {
            throw new IllegalArgumentException("Demandeur obligatoire");
        }
        return demandeurRepository.save(demandeur);
    }

    public Demandeur find(Demandeur demandeur) {
        return demandeurRepository.findByNomAndPrenomAndAdresseMada(demandeur.getNom(), demandeur.getPrenom(),
                demandeur.getAdresseMada()).stream().findFirst().orElse(null);
    }
}