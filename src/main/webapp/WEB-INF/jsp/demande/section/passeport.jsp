<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.Passeport" %>
<%@ page import="com.example.backoffice.entity.Demande" %>
<%
    Demande demande = (Demande) request.getAttribute("demande");
    Passeport passeport = demande != null  ? demande.getVisaTransformable().getPasseport() : null;
    if(passeport == null) passeport = (Passeport) request.getAttribute("passeport");
%>
<div id="step2" class="form-section step-content" style="display: none;">
    <h5 class="mb-4 fw-bold" style="color: #1B5E5A;"><i class="bi bi-bookmark-check"></i> Passeport en cours de validité</h5>
    <div class="row g-4">
        <div class="col-md-6">
            <label class="form-label required-field">N° passeport</label>
            <input type="text" class="form-control" id="passeport_ref" name="passeport.reference" placeholder="PC1234567" value="<%= passeport != null ? passeport.getReference() : "" %>" required>
            <div class="invalid-feedback">Numéro requis</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Date de délivrance</label>
            <input type="date" class="form-control" id="date_delivrance" name="passeport.dateDelivrance" value="<%= passeport != null ? passeport.getDateDelivrance() : null %>" required>
            <div class="invalid-feedback">Date délivrance</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Lieu de délivrance</label>
            <input type="text" class="form-control" id="lieu_delivrance" name="passeport.lieuDelivrance" value="<%= passeport != null ? passeport.getLieuDelivrance() : "" %>" placeholder="Ambassade France / Antananarivo" required>
            <div class="invalid-feedback">Lieu requis</div>
        </div>
        <div class="col-md-6">
            <label class="form-label required-field">Date d'expiration</label>
            <input type="date" class="form-control" id="date_expiration" name="passeport.dateExpiration" value="<%= passeport != null ? passeport.getDateExpiration() : null %>" required>
            <div class="invalid-feedback">Expiration requise</div>
        </div>
    </div>
    <div class="d-flex justify-content-between mt-5">
        <button type="button" class="btn btn-outline-secondary btn-navigation prev-step">
            <i class="bi bi-arrow-left-short"></i> Retour
        </button>
        <button type="button" class="btn btn-primary btn-navigation next-step">
            Suivant <i class="bi bi-arrow-right-short"></i>
        </button>
    </div>
</div>