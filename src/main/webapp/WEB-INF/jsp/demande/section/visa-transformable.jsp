<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.CategorieDemande" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.backoffice.entity.Demande" %>
<%@ page import="com.example.backoffice.entity.VisaTransformable" %>
<%
    
    Demande demande = (Demande) request.getAttribute("demande");
    List<CategorieDemande> categories = (List<CategorieDemande>) request.getAttribute("categories");
    VisaTransformable visaTransformable = demande != null ? demande.getVisaTransformable() : null;
%>
<div id="step3" class="form-section step-content" style="display: none;">
    <h5 class="mb-4 fw-bold" style="color: #1B5E5A;"><i class="bi bi-stamp"></i> Visa de court séjour / transformable</h5>
    <div class="row g-4">
        <div class="col-md-6">
            <label class="form-label required-field">Numéro de visa</label>
            <input type="text" class="form-control" id="num_visa" name="visaTransformable.numeroVisa" placeholder="VISA-MG-12345" value="<%= visaTransformable != null ? visaTransformable.getNumeroVisa() : "" %>" required>
            <div class="invalid-feedback">Numéro visa requis</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Catégorie de demande</label>
            <select class="form-select" id="categorie_demande" name="categorieDemandeId" required>
                <option value="">Choisir</option>
                <% for(CategorieDemande c : categories) { %>
                    <option value="<%= c.getId() %>" <%= demande != null && c.getId() == demande.getCategorieDemande().getId() ? "selected" : "" %>><%= c.getLibelle() %></option>
                <% } %>
            </select>
            <div class="invalid-feedback">Catégorie requise</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Date d'entrée à Madagascar</label>
            <input type="date" class="form-control" id="date_entree" name="visaTransformable.dateEntree" value="<%= visaTransformable != null ? visaTransformable.getDateEntree() : null %>" required>
            <div class="invalid-feedback">Date entrée requise</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Lieu d'entrée</label>
            <input type="text" class="form-control" id="lieu_entree" name="visaTransformable.lieuEntree" placeholder="Aéroport Ivato (TNR)" value="<%= visaTransformable != null ? visaTransformable.getLieuEntree() : "" %>" required>
            <div class="invalid-feedback">Lieu entrée requis</div>
        </div>
        <div class="col-md-6">
            <label class="form-label">Date sortie </label>
            <input type="date" class="form-control" id="date_sortie" name="visaTransformable.dateSortie" value="<%= visaTransformable != null ? visaTransformable.getDateSortie() : null %>">
        </div>
        <div class="col-md-6">
            <label class="form-label">Lieu sortie </label>
            <input type="text" class="form-control" id="lieu_sortie" name="visaTransformable.lieuSortie" value="<%= visaTransformable != null ? visaTransformable.getLieuSortie() : "" %>" placeholder="Toliara / Nosy Be">
        </div>
    </div>
    <div class="d-flex justify-content-between mt-5">
        <button type="button" class="btn btn-outline-secondary btn-navigation prev-step">
            <i class="bi bi-arrow-left-short"></i> Précédent
        </button>
        <button type="button" class="btn btn-primary btn-navigation next-step">
            Étape suivante <i class="bi bi-arrow-right-short"></i>
        </button>
    </div>
</div>