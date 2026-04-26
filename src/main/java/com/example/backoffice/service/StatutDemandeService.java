package com.example.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backoffice.entity.StatutDemande;
import com.example.backoffice.repository.StatutDemandeRepository;

@Service
public class StatutDemandeService {

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    public List<StatutDemande> getAll() {
        return statutDemandeRepository.findAll();

    }
}