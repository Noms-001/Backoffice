package com.example.backoffice.dto;

import com.example.backoffice.entity.DocumentDemande;

import lombok.Data;

@Data
public class DocumentDemandeDTO {

    private String libelle;
    private String chemin;

    public DocumentDemandeDTO(DocumentDemande document) {
        this.libelle = document.getDocument().getLibelle();
        this.chemin = document.getPath();
    }
}
