package com.example.backoffice.dto;
import com.example.backoffice.entity.Demande;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.backoffice.entity.HistoriqueDemande;

@Data
public class DemandeDTO {

    private Long id;
    private String reference;
    private String typeDemande;
    private String categorieDemande;
    private String statutActuel;
    private LocalDate dateDemande;

    private DemandeurDTO demandeur;

    private PasseportDTO passeport;
    private VisaDTO visa;
    private VisaTransformableDTO visaTransformable;
    private CarteResidentDTO carteResident;

    private List<DocumentDemandeDTO> documents;
    private List<HistoriqueDemandeDTO> historiques;

    private byte[] qrcode;

    // 🔹 Constructeur basé sur l'entité Demande
    public DemandeDTO(Demande demande) {
        this.id = demande.getId();
        this.reference = demande.getReference();
        this.typeDemande = demande.getTypeDemande().getLibelle();
        this.categorieDemande = demande.getCategorieDemande().getLibelle();
        this.statutActuel = demande.getHistoriqueDemandes()
                .stream()
                .filter(h -> h.getDateChangement() != null && h.getStatutDemande() != null)
                .max(Comparator.comparing(HistoriqueDemande::getDateChangement))
                .map(h -> h.getStatutDemande().getLibelle())
                .orElse(null);
        this.dateDemande = demande.getDateDemande();

        // 🔹 Objet imbriqué
        if (demande.getDemandeur() != null) {
            this.demandeur = new DemandeurDTO(demande.getDemandeur());
        }

        if (demande.getVisaTransformable() != null) {
            this.visaTransformable = new VisaTransformableDTO(demande.getVisaTransformable());
            this.passeport = new PasseportDTO(demande.getVisaTransformable().getPasseport());
        }

        if (demande.getVisa() != null) {
            this.visa = new VisaDTO(demande.getVisa());
            this.passeport = new PasseportDTO(demande.getVisa().getPasseport());
        }

        if (demande.getCarteResident() != null) {
            this.carteResident = new CarteResidentDTO(demande.getCarteResident());
        }

        // 🔹 Liste documents
        if (demande.getDocumentDemandes() != null) {
            this.documents = demande.getDocumentDemandes()
                    .stream()
                    .map(DocumentDemandeDTO::new)
                    .collect(Collectors.toList());
        }

        // 🔹 Liste historiques
        if (demande.getHistoriqueDemandes() != null) {
            this.historiques = demande.getHistoriqueDemandes()
                    .stream()
                    .map(HistoriqueDemandeDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
