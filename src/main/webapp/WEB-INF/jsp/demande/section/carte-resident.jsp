<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="step4" class="form-section step-content" style="display: none;">
    <h5 class="mb-4 fw-bold" style="color: #1B5E5A;"><i class="bi bi-stamp"></i>Carte résident</h5>
    <div class="row g-4">
        <div class="col-md-6">
            <label class="form-label required-field">Date début</label>
            <input type="date" class="form-control" id="date_debut" name="dateDebutStr" required>
            <div class="invalid-feedback">Date début requis</div>
        </div>
        <div class="col-md-6">
            <label class="form-label">Date fin</label>
            <input type="date" class="form-control" id="date_fin" name="dateFinStr" required>
            <div class="invalid-feedback">Date fin requis</div>
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