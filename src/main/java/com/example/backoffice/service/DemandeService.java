package com.example.backoffice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backoffice.dto.DemandeDTO;
import com.example.backoffice.entity.*;
import com.example.backoffice.repository.CarteResidentRepository;
import com.example.backoffice.repository.CategorieDemandeRepository;
import com.example.backoffice.repository.DemandeRepository;
import com.example.backoffice.repository.HistoriqueDemandeRepository;
import com.example.backoffice.repository.TypeDemandeRepository;
import com.example.backoffice.repository.VisaRepository;
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
    private HistoriqueDemandeRepository historiqueRepository;

    @Autowired
    private VisaRepository visaRepository;

    @Autowired
    private CarteResidentRepository carteResidentRepository;

    @Autowired
    private DemandeurService demandeurService;

    @Autowired
    private PasseportService passeportService;

    @Autowired
    private VisaTransformableService visaTransformableService;

    @Autowired
    private HistoriqueDemandeService historiqueDemandeService;

    @Autowired
    private CarteResidentService carteResidentService;

    @Autowired
    private VisaService visaService;

    public Demande save(Demande demande) {
        if (demande == null) {
            throw new IllegalArgumentException("Demande obligatoire");
        }
        return demandeRepository.save(demande);
    }

    public void processDemande(Demandeur demandeur, Passeport passeport, VisaTransformable visaTransformable,
            List<Document> documents, Long idCategorieDemande, Long idTypeDemande) {
        Demande demande = saveNouveauTitre(demandeur, passeport, visaTransformable, documents, idCategorieDemande,
                null);
        validerDemande(demande, passeport);
        createDemande(
                demande.getDemandeur(),
                demande.getCategorieDemande(),
                idTypeDemande,
                demande.getVisaTransformable(),
                null,
                "Demande transfert de visa créé");
    }

    public void validerDemande(Demande demande, Passeport passeport) {
        if (demande == null) {
            throw new IllegalArgumentException("Demande obligatoire");
        }

        HistoriqueDemande historiqueDemande = historiqueDemandeService.create(demande,
                StatutDemandeEnum.APPROUVER.getCode(), "Demande validée");

        if (historiqueDemande == null) {
            throw new RuntimeException("Echec de l'enregistrement du statut");
        }

        Passeport originelPasseport = passeportService.getByReference(passeport.getReference());
        Visa savedVisa = visaService.createVisaFromDemandeAndPasseport(demande, originelPasseport);
        if (savedVisa == null) {
            throw new RuntimeException("Echec de l'enregistrement du visa");
        }
        CarteResident savedCarte = carteResidentService.createCarteResidentFromDemandeAndPasseport(demande,
                originelPasseport);
        if (savedCarte == null) {
            throw new RuntimeException("Echec de l'enregistrement de la carte de résident");
        }
    }

    public Demande saveTransfertVisa(String numeroVisa, Passeport passeport) {
        Visa visa = visaRepository.findByReference(numeroVisa).orElse(null);

        if (visa == null) {
            return null;
        }

        Demande savedDemande = createDemande(
                visa.getDemande().getDemandeur(),
                visa.getDemande().getCategorieDemande(),
                TypeDemandeEnum.TRANSFERT_VISA.getCode(),
                visa.getDemande().getVisaTransformable(),
                null,
                "Demande transfert de visa créé");
        // 2. Mise à jour passeport avec même demandeur
        passeport.setId(savedDemande.getVisaTransformable().getPasseport().getId());
        passeport.setDemandeur(savedDemande.getDemandeur());

        Passeport savedPasseport = passeportService.save(passeport);

        if (savedPasseport == null) {
            throw new RuntimeException("Echec de l'enregistrement du passeport");
        }

        return savedDemande;
    }

    public Demande saveDuplicata(String numeroCarteResident) {

        CarteResident original = carteResidentRepository.findByReference(numeroCarteResident)
                .orElse(null);

        if (original == null) {
            return null;
        }

        Demande savedDemande = createDemande(
                original.getDemande().getDemandeur(),
                original.getDemande().getCategorieDemande(),
                TypeDemandeEnum.DUPLICATA.getCode(),
                original.getDemande().getVisaTransformable(),
                null,
                "Demande duplicata créé");

        return savedDemande;
    }

    public Demande saveNouveauTitre(Demandeur demandeur, Passeport passeport, VisaTransformable visaTransformable,
            List<Document> documents, Long idCategorieDemande, Long idDemande) {
        if (idDemande != null) {
            List<HistoriqueDemande> historiqueDemandes = historiqueRepository
                    .findByDemandeIdOrderByDateChangementDesc(idDemande);
            if (!historiqueDemandes.isEmpty() || historiqueDemandes != null) {
                if (historiqueDemandes.get(0).getStatutDemande().getId() != StatutDemandeEnum.CREER.getCode()) {
                    throw new RuntimeException("La demande n'est plus modifiable !");
                }
            }
        }

        // 1. Save Demandeur
        Demandeur savedDemandeur = demandeurService.find(demandeur);
        if (savedDemandeur != null)
            demandeur.setId(savedDemandeur.getId());
        savedDemandeur = demandeurService.save(demandeur);
        if (savedDemandeur == null) {
            throw new RuntimeException("Echec de l'enregistrement du demandeur");
        }
        // 2. Save Passeport
        Passeport savedPasseport = passeportService.getByReference(passeport.getReference());
        if (savedPasseport != null)
            passeport.setId(savedPasseport.getId());
        passeport.setDemandeur(savedDemandeur);
        savedPasseport = passeportService.save(passeport);
        if (savedPasseport == null) {
            throw new RuntimeException("Echec de l'enregistrement du passeport");
        }

        // 4. Save VisaTransformable
        visaTransformable.setPasseport(savedPasseport);
        VisaTransformable savedVisa = visaTransformableService.save(visaTransformable);
        if (savedVisa == null) {
            throw new RuntimeException("Echec de l'enregistrement du visa transformable");
        }

        // 5. Save Demande
        CategorieDemande categorieDemande = categorieDemandeRepository
                .findById(idCategorieDemande)
                .orElseThrow(() -> new RuntimeException("CategorieDemande introuvable"));

        Demande savedDemande = createDemande(savedDemandeur, categorieDemande,
                TypeDemandeEnum.NOUVEAU_TITRE.getCode(), savedVisa, documents, "Demande nouveau titre créé");

        return savedDemande;
    }

    public Demande createDemande(Demandeur demandeur, CategorieDemande categorieDemande, Long idTypeDemande,
            VisaTransformable visaTransformable, List<Document> documents, String commentaire) {
        Demande demande = new Demande();
        demande.setDemandeur(demandeur);

        TypeDemande typeDemande = typeDemandeRepository
                .findById(idTypeDemande)
                .orElseThrow(() -> new RuntimeException("TypeDemande introuvable"));

        demande.setTypeDemande(typeDemande);
        demande.setCategorieDemande(categorieDemande);
        demande.setVisaTransformable(visaTransformable);
        demande.setDateDemande(LocalDate.now());
        demande.setDocuments(documents);

        Demande savedDemande = demandeRepository.save(demande);

        if (savedDemande == null) {
            throw new RuntimeException("Echec de l'enregistrement de la demande");
        }

        HistoriqueDemande savedHistorique = historiqueDemandeService.create(savedDemande,
                StatutDemandeEnum.CREER.getCode(), commentaire);

        if (savedHistorique == null) {
            throw new RuntimeException("Echec de l'enregistrement du statut de demande");
        }

        return savedDemande;
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
