package com.example.backoffice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.backoffice.entity.CategorieDemande;
import com.example.backoffice.entity.Demandeur;
import com.example.backoffice.entity.Document;
import com.example.backoffice.entity.Nationalite;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.entity.SituationFamiliale;
import com.example.backoffice.entity.StatutDemande;
import com.example.backoffice.entity.VisaTransformable;
import com.example.backoffice.service.CategorieDemandeService;
import com.example.backoffice.service.DemandeService;
import com.example.backoffice.service.DemandeurService;
import com.example.backoffice.service.DocumentService;
import com.example.backoffice.service.NationaliteService;
import com.example.backoffice.service.PasseportService;
import com.example.backoffice.service.SituationFamilialeService;
import com.example.backoffice.service.StatutDemandeService;

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

    @Autowired
    private DemandeurService demandeurService;

    @Autowired
    private PasseportService passeportService;

    @Autowired
    private StatutDemandeService statutDemandeService;

    @GetMapping("/form")
    public ModelAndView showForm(@RequestParam(name = "id", required = false) Long idDemande) {
        ModelAndView modelAndView = new ModelAndView("demande/form");
        if (idDemande != null) {
            modelAndView.addObject("demande", demandeService.getById(idDemande));
        }
        List<CategorieDemande> categories = categorieDemandeService.getAll();
        List<Nationalite> nationalites = nationaliteService.getAll();
        List<SituationFamiliale> situations = situationFamilialeService.getAll();
        List<Document> documents = documentService.getAllCommuns();

        modelAndView.addObject("categories", categories);
        modelAndView.addObject("nationalites", nationalites);
        modelAndView.addObject("situations", situations);
        modelAndView.addObject("documents", documents);

        // Initialiser des objets vides pour le formulaire
        modelAndView.addObject("demandeur", new Demandeur());
        modelAndView.addObject("passeport", new Passeport());
        modelAndView.addObject("visaTransformable", new VisaTransformable());

        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView showList() {
        ModelAndView model = new ModelAndView("demande/list");
        List<CategorieDemande> categories = categorieDemandeService.getAll();
        List<StatutDemande> statuts = statutDemandeService.getAll();
        model.addObject("categories", categories);
        model.addObject("statuts", statuts);
        return model;
    }

    @PostMapping("/insert")
    public ModelAndView insertDemande(
            @ModelAttribute("demandeur") Demandeur demandeur,
            @ModelAttribute("passeport") Passeport passeport,
            @ModelAttribute("visaTransformable") VisaTransformable visaTransformable,
            @RequestParam Long categorieDemandeId,
            @RequestParam(name="demandeId", required=false) Long demandeId,
            @RequestParam List<Long> documentIds,
            RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView("redirect:/demande/form");

        if(demandeId != null) {
            modelAndView.setViewName("redirect:/demande/list");
             redirectAttributes.addFlashAttribute("message", "Demande mise à jour avec succès");
             return modelAndView;
        }

        try {
            Demandeur savedDemandeur = demandeurService.save(demandeur);

            passeport.setDemandeur(savedDemandeur);
            Passeport savedPasseport = passeportService.save(passeport);

            List<Document> documents = documentService.getAllByIds(documentIds);

            String resultat = demandeService.saveNouveauTitre(savedDemandeur, savedPasseport, visaTransformable,
                    documents, categorieDemandeId, demandeId);

            redirectAttributes.addFlashAttribute("message", resultat);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement : " + e.getMessage());
        }

        return modelAndView;
    }

    @InitBinder("demandeur")
    public void initDemandeurBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("demandeur.");
    }

    @InitBinder("passeport")
    public void initPasseportBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("passeport.");
    }

    @InitBinder("visaTransformable")
    public void initVisaBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("visaTransformable.");
    }
}