package com.example.backoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backoffice.dto.DemandeDTO;
import com.example.backoffice.service.DemandeService;

@RestController
public class DemandeRestController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/api/demandes")
    public List<DemandeDTO> getAllDemandes(@RequestParam(required = false) String numeroPasseport) {
        if (numeroPasseport != null) {
            return demandeService.getAllByPasseport(numeroPasseport);
        }
        return demandeService.getAllDemandeDTO();
    }

    @GetMapping("/api/demande")
    public DemandeDTO getDemande(@RequestParam String numeroDemande) {
        return demandeService.getByReference(numeroDemande);
    }
}