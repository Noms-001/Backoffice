<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.Demande" %>
<%@ page import="com.example.backoffice.entity.Document" %>
<%@ page import="com.example.backoffice.entity.DocumentDemande" %>
<%@ page import="java.util.List" %>
<%
    List<Document> documents = (List<Document>) request.getAttribute("documents");
    Demande demande = (Demande) request.getAttribute("demande");
%>
<div id="step4" class="form-section step-content" style="display: none;">
    <h5 class="mb-3 fw-bold" style="color: #1B5E5A;"><i class="bi bi-file-earmark-zip"></i> Pièces justificatives</h5>
    <div class="alert alert-success bg-opacity-10 border-0 rounded-4">
        <i class="bi bi-info-circle-fill me-2"></i>
        Documents obligatoires à joindre impérativement (formats acceptés : PDF, JPG, PNG)
    </div>

    <div class="checkbox-group-card">
        <h6 class="fw-semibold mb-3">
            <i class="bi bi-check2-circle text-success"></i> Tronc commun (tous requis)
        </h6>

        <div class="row g-3">
            <% for(Document d : documents) { %>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="fw-semibold mb-2"><%= d.getLibelle() %></label>

                    <input 
                        type="file" 
                        class="form-control common-doc" 
                        name="documentFiles[<%= d.getId() %>]"
                        id="doc_<%= d.getId() %>"
                        accept=".pdf,.jpg,.jpeg,.png"
                        data-doc-id="<%= d.getId() %>"
                        required>

                    <small class="text-muted">PDF, JPG ou PNG</small>

                    <% if (demande != null && demande.getDocumentDemandes() != null) { 
                        for (DocumentDemande dd : demande.getDocumentDemandes()) {
                            if (dd.getDocument().getId().equals(d.getId())) { %>

                                <a class="text-success" 
                                href="<%= request.getContextPath() + "/uploads/" + dd.getPath() %>" 
                                target="_blank">
                                    Voir le document existant
                                </a>

                    <%      }
                        }
                    } %>

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

<% if(demande != null && demande.getDocumentDemandes() != null) {
    for(DocumentDemande dd : demande.getDocumentDemandes()) { %>

    <input type="hidden"
           class="uploaded-document-id"
           data-doc-id="<%= dd.getDocument().getId() %>"
           data-path="<%= dd.getPath() %>">

<% } } %>