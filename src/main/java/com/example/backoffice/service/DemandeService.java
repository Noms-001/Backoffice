package com.example.backoffice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backoffice.entity.*;
import com.example.backoffice.repository.CategorieDemandeRepository;
import com.example.backoffice.repository.DemandeRepository;
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

    public Demande create(Demandeur demandeur, CategorieDemande categorieDemande,
            TypeDemande typeDemande, List<Document> documents) {
        Demande demande = new Demande();
        demande.setDemandeur(demandeur);
        demande.setCategorieDemande(categorieDemande);
        demande.setTypeDemande(typeDemande);
        demande.setDateDemande(LocalDate.now());
        return demande;
    }

    public String saveNouveauTitre(Demandeur demandeur, Passeport passeport, VisaTransformable visaTransformable,
            List<Document> documents, Long idCategorieDemande) {

        // 1. Save Demandeur
        Demandeur savedDemandeur = demandeurService.save(demandeur);
        if (savedDemandeur == null) {
            throw new RuntimeException("Echec de l'enregistrement du demandeur");
        }

        // 2. Save Passeport
        passeport.setDemandeur(savedDemandeur); // liaison
        Passeport savedPasseport = passeportService.save(passeport);
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
        Demande demande = create(savedDemandeur, categorieDemande, typeDemande, documents);

        // 4. Save Demande
        Demande savedDemande = this.save(demande);
        if (savedDemande == null) {
            throw new RuntimeException("Echec de l'enregistrement de la demande");
        }

        // 5. Save VisaTransformable
        visaTransformable.setDemande(savedDemande); 
        visaTransformable.setPasseport(savedPasseport);
        VisaTransformable savedVisa = visaTransformableService.save(visaTransformable);
        if (savedVisa == null) {
            throw new RuntimeException("Echec de l'enregistrement du visa transformable");
        }

        // 6. HistoriqueDemande
        StatutDemande statutDemande = statutDemandeRepository
                .findById(StatutDemandeEnum.CREER.getCode())
                .orElseThrow(() -> new RuntimeException("StatutDemande introuvable"));
        HistoriqueDemande historique = historiqueDemandeService.create(savedDemande, statutDemande);

        HistoriqueDemande savedHistorique = historiqueDemandeService.save(historique);
        if (savedHistorique == null) {
            throw new RuntimeException("Echec de l'enregistrement du statut de demande");
        }

        return "Enregistrement effectué avec succès";
    }
}
