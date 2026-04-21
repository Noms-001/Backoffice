package com.example.backoffice.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.backoffice.entity.CategorieDemande;
import com.example.backoffice.entity.Demande;
import com.example.backoffice.entity.Demandeur;
import com.example.backoffice.entity.Document;
import com.example.backoffice.entity.Nationalite;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.entity.SituationFamiliale;
import com.example.backoffice.entity.VisaTransformable;
import com.example.backoffice.service.CategorieDemandeService;
import com.example.backoffice.service.DemandeService;
import com.example.backoffice.service.DocumentService;
import com.example.backoffice.service.NationaliteService;
import com.example.backoffice.service.SituationFamilialeService;

@Controller
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    private CategorieDemandeService categorieDemandeService;

    @Autowired
    private NationaliteService nationaliteService;

    @Autowired
    private SituationFamilialeService situationFamilialeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/form")
    public ModelAndView showForm() {
        ModelAndView modelAndView = new ModelAndView("demande/form");

        // Appel des fonctions des services
        List<CategorieDemande> categories = categorieDemandeService.getAll();
        List<Nationalite> nationalites = nationaliteService.getAll();
        List<SituationFamiliale> situations = situationFamilialeService.getAll();
        List<Document> documents = documentService.getAllCommuns();

        // Utilisation de setAttribute
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("nationalites", nationalites);
        modelAndView.addObject("situations", situations);
        modelAndView.addObject("documents", documents);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insertDemande(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String nomJeuneFille,
            @RequestParam String dateNaissanceStr,
            @RequestParam String lieuNaissance,
            @RequestParam Long situationFamilialeId,
            @RequestParam Long nationaliteId,
            @RequestParam String adresseMada,
            @RequestParam String email,
            @RequestParam String numero,
            @RequestParam String referencePasseport,
            @RequestParam String dateDelivranceStr,
            @RequestParam String lieuDelivrance,
            @RequestParam String dateExpirationStr,
            @RequestParam String numeroVisa,
            @RequestParam String dateEntreeStr,
            @RequestParam String lieuEntree,
            @RequestParam String dateSortieStr,
            @RequestParam String lieuSortie,
            @RequestParam Long idCategorieDemande) {

        ModelAndView modelAndView = new ModelAndView("demande/form");

        try {
            // Création des objets à partir des paramètres du formulaire
            Demandeur demandeur = new Demandeur();
            demandeur.setNom(nom);
            demandeur.setPrenom(prenom);
            demandeur.setNomJeuneFille(nomJeuneFille);
            demandeur.setDateNaissance(LocalDate.parse(dateNaissanceStr));
            demandeur.setLieuNaissance(lieuNaissance);
            demandeur.setAdresseMada(adresseMada);
            demandeur.setEmail(email);
            demandeur.setNumero(numero);

            // Récupération des références
            SituationFamiliale situationFamiliale = new SituationFamiliale();
            situationFamiliale.setId(situationFamilialeId);
            demandeur.setSituationFamiliale(situationFamiliale);

            Nationalite nationalite = new Nationalite();
            nationalite.setId(nationaliteId);
            demandeur.setNationalite(nationalite);

            Passeport passeport = new Passeport();
            passeport.setReference(referencePasseport);
            passeport.setDateDelivrance(LocalDate.parse(dateDelivranceStr));
            passeport.setLieuDelivrance(lieuDelivrance);
            passeport.setDateExpiration(LocalDate.parse(dateExpirationStr));

            VisaTransformable visaTransformable = new VisaTransformable();
            visaTransformable.setNumeroVisa(numeroVisa);
            visaTransformable.setDateEntree(LocalDate.parse(dateEntreeStr));
            visaTransformable.setLieuEntree(lieuEntree);
            visaTransformable.setDateSortie(LocalDate.parse(dateSortieStr));
            visaTransformable.setLieuSortie(lieuSortie);

            List<Document> documents = null;

            // Appel de la fonction saveNouveauTitre de DemandeService
            String resultat = demandeService.saveNouveauTitre(demandeur, passeport, visaTransformable, documents,
                    idCategorieDemande);

            // Utilisation de setAttribute pour passer le message de succès
            modelAndView.addObject("message", resultat);

        } catch (Exception e) {
            // Utilisation de setAttribute pour passer le message d'erreur
            modelAndView.addObject("error", e.getMessage());

            // Récupération des listes pour réaffichage du formulaire
            List<CategorieDemande> categories = categorieDemandeService.getAll();
            List<Nationalite> nationalites = nationaliteService.getAll();
            List<SituationFamiliale> situations = situationFamilialeService.getAll();
            List<Document> documents = documentService.getAllCommuns();

            modelAndView.addObject("categories", categories);
            modelAndView.addObject("nationalites", nationalites);
            modelAndView.addObject("situations", situations);
            modelAndView.addObject("documents", documents);
        }

        return modelAndView;
    }

}
