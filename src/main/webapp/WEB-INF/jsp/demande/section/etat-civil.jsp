<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.SituationFamiliale" %>
<%@ page import="com.example.backoffice.entity.Nationalite" %>
<%@ page import="com.example.backoffice.entity.Demande" %>
<%@ page import="java.util.List" %>
<%
    List<SituationFamiliale> situations = (List<SituationFamiliale>) request.getAttribute("situations");
    List<Nationalite> nationalites = (List<Nationalite>) request.getAttribute("nationalites");
    Demande demande = (Demande) request.getAttribute("demande");
    if(demande != null)
%>
<div id="step1" class="form-section step-content animate-in">
    <h5 class="mb-4 fw-bold">
        <i class="bi bi-person-vcard me-2 text-success"></i>Identité
    </h5>
    <div class="row g-4">
        <div class="col-md-6">
            <label class="form-label required-field">Nom complet</label>
            <input type="text" class="form-control" id="nom" name="demandeur.nom" placeholder="Rakoto" value="<%= demande != null ? demande.getDemandeur().getNom() : "" %>" required>
            <div class="invalid-feedback">Nom obligatoire</div>
        </div>
        <div class="col-md-6">
            <label class="form-label">Prénom (optionnel)</label>
            <input type="text" class="form-control" id="prenom" name="demandeur.prenom" placeholder="Jean" value="<%= demande != null ? demande.getDemandeur().getPrenom() : "" %>">
        </div>
        <div class="col-md-6">
            <label class="form-label">Nom de jeune fille</label>
            <input type="text" class="form-control" id="nom_jeune_fille" name="demandeur.nomJeuneFille" placeholder="Razafindratovo" value="<%= demande != null ? demande.getDemandeur().getNomJeuneFille() : "" %>">
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Date de naissance</label>
            <input type="date" class="form-control" id="date_naissance" value="<%= demande != null ? demande.getDemandeur().getDateNaissance() : null %>" name="demandeur.dateNaissance" required>
            <div class="invalid-feedback">Date naissance requise</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Lieu de naissance</label>
            <input type="text" class="form-control" id="lieu_naissance" name="demandeur.lieuNaissance" placeholder="Fianarantsoa" value="<%= demande != null ? demande.getDemandeur().getLieuNaissance() : "" %>" required>
            <div class="invalid-feedback">Lieu requis</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Situation familiale</label>
            <select class="form-select" id="situation_familiale" name="demandeur.situationFamiliale.id" required>
                <option value="">Sélectionner</option>
                <% for(SituationFamiliale s : situations) { %>
                    <option value="<%= s.getId() %>" <%= (demande != null && demande.getDemandeur().getSituationFamiliale().getId() == s.getId()) ? "selected" : "" %>><%= s.getLibelle() %></option>
                <% } %>
            </select>
            <div class="invalid-feedback">Choisir situation</div>
        </div>
        <div class="col-md-6 position-relative">
            <label class="form-label required-field">Nationalité</label>
            <select id="nationalite_data" style="display:none;">
                <% for(Nationalite n : nationalites) { %>
                    <option value="<%= n.getId() %>" <%= (demande != null && demande.getDemandeur().getNationalite().getId() == n.getId()) ? "selected" : "" %>><%= n.getLibelle() %></option>
                <% } %>
            </select>
            <div class="position-relative">
                <input type="text" class="form-control" id="nationalite_input"
                    placeholder="Tapez pour rechercher... ex: Française" autocomplete="off" required>
                <div id="nationalite_autocomplete_list" class="autocomplete-items" style="display: none;"></div>
            </div>
            <input type="hidden" id="nationalite_hidden" name="demandeur.nationalite.id" required>
            <div class="invalid-feedback">Nationalité valide requise</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Adresse à Madagascar</label>
            <input type="text" class="form-control" id="adresse_mada" name="demandeur.adresseMada" placeholder="Lot IVO 66 Bis, Antananarivo" value="<%= demande != null ? demande.getDemandeur().getAdresseMada() : "" %>"
                required>
            <div class="invalid-feedback">Adresse obligatoire</div>
        </div>
        <div class="col-md-6">
            <label class="form-label">Email (optionnel)</label>
            <input type="email" class="form-control" id="email" name="demandeur.email" placeholder="contact@exemple.mg" value="<%= demande != null ? demande.getDemandeur().getEmail() : "" %>">
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Téléphone</label>
            <input type="tel" class="form-control" id="numero" name="demandeur.numero" placeholder="+261 34 12 345 67" required value="<%= demande != null ? demande.getDemandeur().getNumero() : "" %>">
            <div class="invalid-feedback">Numéro obligatoire</div>
        </div>
    </div>
    <div class="d-flex justify-content-end mt-5 pt-2">
        <button type="button" class="btn btn-primary btn-navigation next-step">
            Continuer <i class="bi bi-arrow-right-short"></i>
        </button>
    </div>
</div>