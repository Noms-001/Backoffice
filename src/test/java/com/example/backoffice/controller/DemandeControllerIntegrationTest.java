// package com.example.backoffice.controller;

// import com.example.backoffice.service.*;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Standard in 3.4.x
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.bean.override.mockito.MockitoBean; // Required for 3.4+
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
// @AutoConfigureMockMvc
// class DemandeControllerIntegrationTest {

//         @Autowired
//         private MockMvc mockMvc;

//         // Mock des services pour éviter accès DB réel
//         @MockitoBean
//         private DemandeService demandeService;
//         @MockitoBean
//         private DemandeurService demandeurService;
//         @MockitoBean
//         private PasseportService passeportService;
//         @MockitoBean
//         private VisaTransformableService visaTransformableService;

//         @Test
//         void shouldSubmitFormSuccessfully() throws Exception {

//                 // Mock retour service principal
//                 org.mockito.Mockito.when(
//                                 demandeService.saveNouveauTitre(
//                                                 org.mockito.ArgumentMatchers.any(),
//                                                 org.mockito.ArgumentMatchers.any(),
//                                                 org.mockito.ArgumentMatchers.any(),
//                                                 org.mockito.ArgumentMatchers.anyList(),
//                                                 org.mockito.ArgumentMatchers.anyLong()))
//                                 .thenReturn("Enregistrement effectué avec succès");

//                 mockMvc.perform(post("/demande/insert")
//                                 .param("nom", "Rakoto")
//                                 .param("prenom", "Jean")
//                                 .param("nomJeuneFille", "")
//                                 .param("dateNaissanceStr", "1990-01-01")
//                                 .param("lieuNaissance", "Tana")
//                                 .param("situationFamilialeId", "1")
//                                 .param("nationaliteId", "1")
//                                 .param("adresseMada", "Antananarivo")
//                                 .param("email", "test@mail.com")
//                                 .param("numero", "0341234567")
//                                 .param("referencePasseport", "P123456")
//                                 .param("dateDelivranceStr", "2020-01-01")
//                                 .param("lieuDelivrance", "Tana")
//                                 .param("dateExpirationStr", "2030-01-01")
//                                 .param("numeroVisa", "VISA-001")
//                                 .param("dateEntreeStr", "2024-01-01")
//                                 .param("lieuEntree", "Ivato")
//                                 .param("dateSortieStr", "2024-12-01")
//                                 .param("lieuSortie", "Tana")
//                                 .param("categorieDemandeId", "1")
//                                 .param("documentIds", "1", "2"))
//                                 .andExpect(status().isOk())
//                                 .andExpect(model().attributeExists("message"));
//         }
// }