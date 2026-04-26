package com.example.backoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backoffice.dto.DemandeDTO;
import com.example.backoffice.service.DemandeService;

@RestController
public class DemandeRestController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/api/demandes")
    public List<DemandeDTO> getAllDemandes() {
        return demandeService.getAllDemandeDTO();
    }
}