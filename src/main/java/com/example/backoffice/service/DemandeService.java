package com.example.backoffice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backoffice.dto.DemandeDTO;
import com.example.backoffice.entity.*;
import com.example.backoffice.repository.CategorieDemandeRepository;
import com.example.backoffice.repository.DemandeRepository;
import com.example.backoffice.repository.HistoriqueDemandeRepository;
import com.example.backoffice.repository.StatutDemandeRepository;
import com.example.backoffice.repository.TypeDemandeRepository;
import com.example.backoffice.util.StatutDemandeEnum;
import com.example.backoffice.util.TypeDemandeEnum;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CategorieDemandeRepository categorieDemandeRepository;

    @Autowired
    private TypeDemandeRepository typeDemandeRepository;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    @Autowired
    private HistoriqueDemandeRepository historiqueRepository;

    @Autowired
    private DemandeurService demandeurService;

    @Autowired
    private PasseportService passeportService;

    @Autowired
    private VisaTransformableService visaTransformableService;

    @Autowired
    private HistoriqueDemandeService historiqueDemandeService;

    public Demande save(Demande demande) {
        if (demande == null) {
            throw new IllegalArgumentException("Demande obligatoire");
        }
        return demandeRepository.save(demande);
    }

    public String saveNouveauTitre(Demandeur demandeur, Passeport passeport, VisaTransformable visaTransformable,
            List<Document> documents, Long idCategorieDemande, Long idDemande) {

        // 1. Save Demandeur
        Demandeur savedDemandeur = demandeurService.find(demandeur);
        if (savedDemandeur == null) {
            savedDemandeur = demandeur;
        }
        savedDemandeur = demandeurService.save(demandeur);
        if (savedDemandeur == null) {
            throw new RuntimeException("Echec de l'enregistrement du demandeur");
        }
        // 2. Save Passeport
        Passeport savedPasseport = passeportService.getByReference(passeport.getReference());
        if (savedPasseport == null) {
            savedPasseport = passeport;
        }
        passeport.setDemandeur(savedDemandeur);
        savedPasseport = passeportService.save(passeport);
        if (savedPasseport == null) {
            throw new RuntimeException("Echec de l'enregistrement du passeport");
        } 

        // 3. Création Demande
        CategorieDemande categorieDemande = categorieDemandeRepository
                .findById(idCategorieDemande)
                .orElseThrow(() -> new RuntimeException("CategorieDemande introuvable"));
        TypeDemande typeDemande = typeDemandeRepository
                .findById(TypeDemandeEnum.NOUVEAU_TITRE.getCode())
                .orElseThrow(() -> new RuntimeException("TypeDemande introuvable"));

        // 4. Save VisaTransformable
        visaTransformable.setPasseport(savedPasseport);
        VisaTransformable savedVisa = visaTransformableService.save(visaTransformable);
        if (savedVisa == null) {
            throw new RuntimeException("Echec de l'enregistrement du visa transformable");
        }

        Demande demande = new Demande(idDemande, categorieDemande, typeDemande, savedDemandeur, visaTransformable,
                LocalDate.now(), documents);

        // 5. Save Demande
        Demande savedDemande = this.save(demande);
        if (savedDemande == null) {
            throw new RuntimeException("Echec de l'enregistrement de la demande");
        }

        // 6. HistoriqueDemande
        StatutDemande statutDemande = statutDemandeRepository
                .findById(StatutDemandeEnum.CREER.getCode())
                .orElseThrow(() -> new RuntimeException("StatutDemande introuvable"));
        HistoriqueDemande historique = new HistoriqueDemande(null, savedDemande, statutDemande, LocalDateTime.now(),
                null);

        HistoriqueDemande savedHistorique = historiqueDemandeService.save(historique);
        if (savedHistorique == null) {
            throw new RuntimeException("Echec de l'enregistrement du statut de demande");
        }

        return "Enregistrement effectué avec succès";
    }

    public Demande getById(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }

    public List<Demande> getAll() {
        return demandeRepository.findAll();
    }

    public List<DemandeDTO> getAllDemandeDTO() {
        List<Demande> demandes = getAll();

        return demandes.stream().map(demande -> {
            DemandeDTO dto = new DemandeDTO();

            dto.setId(demande.getId());

            // Demandeur
            if (demande.getDemandeur() != null) {
                dto.setNomDemandeur(demande.getDemandeur().getNom());
                dto.setPrenomDemandeur(demande.getDemandeur().getPrenom());
            }

            // Visa
            if (demande.getVisaTransformable() != null) {
                dto.setNumeroVisaTransformable(
                        demande.getVisaTransformable().getNumeroVisa());
                if (demande.getVisaTransformable().getPasseport() != null) {
                    dto.setNumeroPasseport(demande.getVisaTransformable().getPasseport().getReference());
                }
            }

            // Catégorie
            if (demande.getCategorieDemande() != null) {
                dto.setCategorieDemande(
                        demande.getCategorieDemande().getLibelle());
            }

            // Date
            if (demande.getDateDemande() != null) {
                dto.setDateDemande(demande.getDateDemande().toString());
            }

            // Statut (dernier historique)
            List<HistoriqueDemande> historiques = historiqueRepository
                    .findByDemandeIdOrderByDateChangementDesc(demande.getId());

            if (!historiques.isEmpty()) {
                dto.setStatutDemande(
                        historiques.get(0).getStatutDemande().getLibelle());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
