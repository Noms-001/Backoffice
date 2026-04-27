<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.CategorieDemande" %>
<%@ page import="com.example.backoffice.entity.Demande" %>
<%@ page import="com.example.backoffice.entity.Document" %>
<%@ page import="java.util.List" %>
<%
    List<CategorieDemande> categories = (List<CategorieDemande>) request.getAttribute("categories");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    Demande demande = (Demande) request.getAttribute("demande");
%>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Visa Long Séjour Madagascar | Formulaire Officiel Transformable</title>
    <!-- Bootstrap 5 CSS + Icons + Google Fonts + AOS (pour animations subtiles) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link
        href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,300;14..32,400;14..32,500;14..32,600;14..32,700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-demande.css">
</head>

<body>
    <div class="container">
        <div class="form-card">
            <div class="card-header-custom">
                <div class="d-flex justify-content-between align-items-center flex-wrap">
                    <div>
                        <% if(demande != null) { %>
                        <h3 class="mb-1 text-white">
                            <i class="bi bi-pencil-square me-2"></i>Modification de la demande
                        </h3>
                        <% } else { %>
                        <h3 class="mb-1 text-white">
                            <i class="bi bi-shield-check me-2"></i>Visa transformable long séjour
                        </h3>
                        <% } %>
                        <p class="mb-0 opacity-75 text-white" style="font-size: 0.9rem;">Résident · Investisseur ·
                            Professionnel</p>
                    </div>
                    <div class="badge-officiel mt-2 mt-sm-0 text-white"><i class="bi bi-building"></i> République de
                        Madagascar</div>
                </div>
            </div>

            <!-- Stepper amélioré -->
            <div class="step-indicator d-flex justify-content-between align-items-center flex-wrap">
                <div class="step-badge" id="step1Badge"><i class="bi bi-person me-1"></i> 1. État civil & demande</div>
                <div class="step-badge" id="step2Badge"><i class="bi bi-passport me-1"></i> 2. Passeport</div>
                <div class="step-badge" id="step3Badge"><i class="bi bi-card-text me-1"></i> 3. Visa entrée</div>
                <div class="step-badge" id="step4Badge"><i class="bi bi-file-earmark-check me-1"></i> 4. Justificatifs
                </div>
            </div>

            <form id="multiStepForm" method="post" action="insert">
                <input type="hidden" name="demandeId" value="<%= demande != null ? demande.getId() : "" %>">
                <jsp:include page="section/etat-civil.jsp" />
                <jsp:include page="section/passeport.jsp" />
                <jsp:include page="section/visa-transformable.jsp" />
                <jsp:include page="section/document.jsp" />

                <select id="categorie_document_data" style="display:none;">
                    <% for(CategorieDemande c : categories) { 
                        for(Document d : c.getDocuments()) { %>

                        <option 
                            data-categorie="<%= c.getId() %>" 
                            value="<%= d.getId() %>">
                            <%= d.getLibelle() %>
                        </option>

                    <% } } %>
                </select>
            </form>
        </div>
        <footer class="text-center mt-4">
            <small>© République de Madagascar — Direction de l'Immigration. Formulaire
                sécurisé.
            </small>
        </footer>
    </div>

    <!-- Toast container -->
    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1100;">
        <% if (message != null) { %>
            <div class="toast align-items-center text-bg-success border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-check-circle me-1"></i>
                        <%= message %>
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        <% } %>

        <% if (error != null) { %>
            <div class="toast align-items-center text-bg-danger border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-exclamation-triangle me-1"></i>
                        <%= error %>
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        <% } %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/form-demande.js"></script>
</body>

</html>