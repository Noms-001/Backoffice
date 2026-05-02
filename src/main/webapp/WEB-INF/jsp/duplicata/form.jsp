<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% 
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Duplicata | Carte de résidence · Immigration Madagascar</title>
    <!-- Bootstrap 5 CSS + Icons + Google Fonts -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,300;14..32,400;14..32,500;14..32,600;14..32,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-duplicata.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/component.css">
</head>
<body>
    <jsp:include page="../component/sidebar.jsp" />
    <div class="main-content" id="mainContent">
        <!-- NAVBAR -->
        <jsp:include page="../component/navbar.jsp" />
        <div id="dynamicViewContainer">
            
            <div class="form-card">
                <div class="card-header-custom">
                    <div class="d-flex justify-content-between align-items-center flex-wrap">
                        <div>
                            <h3 class="mb-1 text-white"><i class="bi bi-files me-2"></i>Demande de duplicata</h3>
                            <p class="mb-0 opacity-75 text-white" style="font-size: 0.9rem;">Carte de résidence · perte, vol ou détérioration</p>
                        </div>
                        <div class="badge-officiel mt-2 mt-sm-0 text-white"><i class="bi bi-building"></i> République de Madagascar · Immigration</div>
                    </div>
                </div>

                <div class="form-section">

                    <form id="duplicataForm" method="post" action="insert">
                        <!-- Champ principal : numéro de carte de résidence -->
                        <div class="mb-4">
                            <label class="form-label">
                                <i class="bi bi-card-heading me-1"></i> Numéro de carte de résidence 
                                <span class="required-star">*</span>
                            </label>
                            <div class="input-icon-group">
                                <i class="bi bi-upc-scan"></i>
                                <input type="text" name="numeroCarteResident" class="form-control" id="carteResidenceNum" 
                                    placeholder="Ex: RES-12345-MG / CR2024-001234" 
                                    autocomplete="off"
                                    required>
                            </div>
                            <div class="invalid-feedback" id="carteErrorMsg">Veuillez saisir le numéro de carte de résidence.</div>
                        </div>

                        <!-- section motif optionnel -->
                        <div class="mb-3">
                            <label class="form-label">
                                <i class="bi bi-chat-square-text me-1"></i> Motif de la demande (optionnel)
                            </label>
                            <textarea class="form-control motif-area" id="motifDuplicata" rows="3" 
                                    placeholder="Ex : Perte du portefeuille, vol du document, détérioration accidentelle, changement d'état civil..."></textarea>
                        </div>

                        <!-- Ligne discrète de rappel -->
                        <div class="divider-light"></div>

                        <!-- Boutons d'action -->
                        <div class="button-group d-flex justify-content-between align-items-center gap-3 mt-3">
                            <button type="button" class="btn-reset" id="resetFormBtn"><i class="bi bi-arrow-repeat"></i> Réinitialiser</button>
                            <button type="submit" class="btn-submit text-white flex-grow-1"><i class="bi bi-file-earmark-check me-2"></i> Demander le duplicata</button>
                        </div>

                        <!-- zone d'alerte (confirmation / erreur) -->
                        <div id="formAlert" class="alert-message alert" role="alert" style="display: none;">
                            <div class="d-flex">
                                <i class="bi bi-info-circle-fill me-2" style="font-size: 1.1rem;"></i>
                                <span id="alertText"></span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <footer class="text-center mt-4">
                <small>© République de Madagascar — Direction Générale de l'Immigration. Duplicata carte de résidence.</small>
            </footer>
        </div>
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

    <script src="${pageContext.request.contextPath}/js/form-duplicata.js"></script>
    <script src="${pageContext.request.contextPath}/js/component.js"></script>
</body>
</html>