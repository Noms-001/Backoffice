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
import com.example.backoffice.entity.Demande;
import com.example.backoffice.entity.Demandeur;
import com.example.backoffice.entity.Document;
import com.example.backoffice.entity.Nationalite;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.entity.SituationFamiliale;
import com.example.backoffice.entity.StatutDemande;
import com.example.backoffice.entity.VisaTransformable;
import com.example.backoffice.service.CategorieDemandeService;
import com.example.backoffice.service.DemandeService;
import com.example.backoffice.service.DocumentService;
import com.example.backoffice.service.NationaliteService;
import com.example.backoffice.service.SituationFamilialeService;
import com.example.backoffice.service.StatutDemandeService;
import com.example.backoffice.util.TypeDemandeEnum;

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
    private StatutDemandeService statutDemandeService;

    @GetMapping("/transfert/form")
    public ModelAndView showTransfertForm() {
        ModelAndView modelAndView = new ModelAndView("transfert/form");
        return modelAndView;
    }

    @GetMapping("/duplicata/form")
    public ModelAndView showDuplicataForm() {
        ModelAndView modelAndView = new ModelAndView("duplicata/form");
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView showForm(
            @RequestParam(name = "id", required = false) Long idDemande,
            @ModelAttribute("passeport") Passeport passeport) {
        ModelAndView modelAndView = new ModelAndView("demande/form");

        if (idDemande != null) {
            modelAndView.addObject("demande", demandeService.getById(idDemande));
        }

        if (passeport != null && passeport.getReference() != null) {
            modelAndView.addObject("passeport", passeport);
        }

        List<CategorieDemande> categories = categorieDemandeService.getAll();
        List<Nationalite> nationalites = nationaliteService.getAll();
        List<SituationFamiliale> situations = situationFamilialeService.getAll();
        List<Document> documents = documentService.getAllCommuns();

        modelAndView.addObject("categories", categories);
        modelAndView.addObject("nationalites", nationalites);
        modelAndView.addObject("situations", situations);
        modelAndView.addObject("documents", documents);

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

    @PostMapping("/transfert/insert")
    public ModelAndView insertTransfert(
            @RequestParam String numeroVisa,
            @ModelAttribute("passeport") Passeport passeport,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/demande/transfert/form");
        try {

            Demande demande = demandeService.saveTransfertVisa(numeroVisa, passeport);
            if (demande == null) {
                modelAndView.setViewName("redirect:/demande/form");
                redirectAttributes.addFlashAttribute("error", "Aucun visa trouvé pour le numéro de visa fourni");
                redirectAttributes.addFlashAttribute("idTypeDemande", TypeDemandeEnum.TRANSFERT_VISA.getCode());
                redirectAttributes.addFlashAttribute("passeport", passeport);
                return modelAndView;
            }
            redirectAttributes.addFlashAttribute("message", "Demande de transfert de visa effectué avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement : " + e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/duplicata/insert")
    public ModelAndView insertDuplicata(@RequestParam String numeroCarteResident,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/demande/duplicata/form");
        try {
            Demande demande = demandeService.saveDuplicata(numeroCarteResident);
            if (demande == null) {
                modelAndView.setViewName("redirect:/demande/form");
                redirectAttributes.addFlashAttribute("error",
                        "Aucun visa trouvé pour le numéro de carte de résident fourni");
                redirectAttributes.addFlashAttribute("idTypeDemande", TypeDemandeEnum.DUPLICATA.getCode());
                return modelAndView;
            }
            redirectAttributes.addFlashAttribute("message",  "Demande de duplicata de carte de résident effectué avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement : " + e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insertDemande(
            @ModelAttribute("demandeur") Demandeur demandeur,
            @ModelAttribute("passeport") Passeport passeport,
            @ModelAttribute("visaTransformable") VisaTransformable visaTransformable,
            @RequestParam Long categorieDemandeId,
            @RequestParam(name = "demandeId", required = false) Long demandeId,
            @RequestParam List<Long> documentIds,
            @RequestParam(required = false) Long idTypeDemande,
            RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView("redirect:/demande/form");

        if (idTypeDemande != null) {
            try {
                modelAndView.setViewName("redirect:/resident");
                List<Document> documents = documentService.getAllByIds(documentIds);
                demandeService.processDemande(demandeur, passeport, visaTransformable, documents, categorieDemandeId, idTypeDemande);
                return modelAndView;

            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Erreur lors du process : " + e.getMessage());
            }
        } else {

            if (demandeId != null) {
                modelAndView.setViewName("redirect:/demande/list");
                redirectAttributes.addFlashAttribute("message", "Demande mise à jour avec succès");
                return modelAndView;
            }

            try {
                List<Document> documents = documentService.getAllByIds(documentIds);

                Demande demande = demandeService.saveNouveauTitre(demandeur, passeport, visaTransformable,
                        documents, categorieDemandeId, demandeId);

                if (demande == null) {
                    throw new RuntimeException("Echec de l'enregistrement de la demande");
                }

                redirectAttributes.addFlashAttribute("message", "Enregistrement de la demande effectué avec succès");

            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement : " + e.getMessage());
            }
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