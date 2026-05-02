<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.Demande" %>
<%@ page import="com.example.backoffice.entity.Document" %>
<%@ page import="java.util.List" %>
<%
    List<Document> documents = (List<Document>) request.getAttribute("documents");
    Demande demande = (Demande) request.getAttribute("demande");
%>
<div id="step4" class="form-section step-content" style="display: none;">
    <h5 class="mb-3 fw-bold" style="color: #1B5E5A;"><i class="bi bi-file-earmark-zip"></i> Pièces justificatives</h5>
    <div class="alert alert-success bg-opacity-10 border-0 rounded-4"><i class="bi bi-info-circle-fill me-2"></i>
        Documents obligatoires à joindre impérativement.
    </div>

    <div class="checkbox-group-card">
        <h6 class="fw-semibold mb-3">
            <i class="bi bi-check2-circle text-success"></i> Tronc commun (tous requis)
        </h6>

        <div class="row g-3">

            <% for(Document d : documents) { %>

            <div class="col-md-6">
                <div class="form-check">
                    <input 
                        class="form-check-input common-doc" 
                        type="checkbox" 
                        value="<%= d.getId() %>" 
                        id="doc_<%= d.getId() %>"
                        name="documentIds" 
                        <%= demande != null && demande.getDocuments().contains(d) ? "checked" : "" %>>

                    <label class="form-check-label">
                        <%= d.getLibelle() %>
                    </label>
                </div>
            </div>

            <% } %>

        </div>
    </div>

    <div class="checkbox-group-card mt-3" id="specificDocsContainer">
        <h6 class="fw-semibold mb-2"><i class="bi bi-briefcase"></i> Spécifiques à votre statut</h6>
        <div id="specificDocsInner" class="mt-2"></div>
        <div id="specificDocsError" class="text-danger small mt-2 fw-semibold"></div>
    </div>

    <div class="d-flex justify-content-between mt-5">
        <button type="button" class="btn btn-outline-secondary btn-navigation prev-step">
            <i class="bi bi-arrow-left-short"></i> Retour
        </button>
        <button type="button" class="btn btn-success btn-navigation" id="submitBtn">
            <% if(demande == null) { %>
            <i class="bi bi-send-check"></i> Soumettre la demande   
            <% } else { %>
            <i class="bi bi-pencil me-2"></i> Modifier la demande
            <% } %>
        </button>
    </div>
</div>

<% if(demande != null && demande.getDocuments() != null) {
    for(Document d : demande.getDocuments()) { %>
        <input type="hidden" class="selected-document-id" value="<%= d.getId() %>">
<% } } %>