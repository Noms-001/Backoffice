<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.backoffice.entity.CategorieDemande" %>
<%@ page import="com.example.backoffice.entity.StatutDemande" %>
<%@ page import="java.util.List" %>

<%
    List<StatutDemande> statuts = (List<StatutDemande>) request.getAttribute("statuts");
    List<CategorieDemande> categories = (List<CategorieDemande>) request.getAttribute("categories");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Gestion des demandes | Visa transformable Long Séjour</title>
    <!-- Bootstrap 5 CSS + Icons + Google Fonts -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link
        href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,300;14..32,400;14..32,500;14..32,600;14..32,700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list-demande.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/component.css">
</head>

<body>
    <!-- SIDEBAR -->
    <jsp:include page="../component/sidebar.jsp" />
    <div class="main-content" id="mainContent">
        <!-- NAVBAR -->
        <jsp:include page="../component/navbar.jsp" />
        <div id="dynamicViewContainer">
            
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
            <div class="form-card">
                <div class="card-header-custom">
                    <div class="d-flex justify-content-between align-items-center flex-wrap">
                        <div>
                            <h3 class="mb-1 text-white"><i class="bi bi-card-list me-2"></i>Demandes de visa transformable
                            </h3>
                            <p class="mb-0 opacity-75 text-white" style="font-size: 0.9rem;">Liste des dossiers éligibles au
                                long séjour</p>
                        </div>
                        <div class="badge-officiel mt-2 mt-sm-0 text-white"><i class="bi bi-building"></i> République de
                            Madagascar · Immigration</div>
                    </div>
                </div>

                <div class="form-section">
                    <!-- Barre de filtres améliorée avec statut -->
                    <div class="filter-bar">
                        <div class="row g-3 align-items-end">
                            <div class="col-md-4">
                                <label class="form-label small fw-semibold text-secondary"><i class="bi bi-search"></i>
                                    Recherche</label>
                                <div class="position-relative">
                                    <i class="bi bi-search position-absolute"
                                        style="left: 1rem; top: 50%; transform: translateY(-50%); color: #8ba3b8;"></i>
                                    <input type="text" id="searchInput" class="form-control search-input"
                                        placeholder="Nom, prénom, passeport ou visa...">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-semibold text-secondary"><i class="bi bi-tag"></i>
                                    Catégorie
                                </label>
                                <select id="categorieFilter" class="form-select" style="border-radius: 2rem;">
                                    <option value="all">Toutes catégories</option>
                                    <% for(CategorieDemande c : categories) { %>
                                        <option value="<%= c.getLibelle().toLowerCase() %>"><%= c.getLibelle() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small fw-semibold text-secondary"><i class="bi bi-flag"></i>
                                    Statut
                                </label>
                                <select id="statutFilter" class="form-select" style="border-radius: 2rem;">
                                    <option value="all">Tous statuts</option>
                                    <% for(StatutDemande s : statuts) { %>
                                    <option value="<%= s.getLibelle().toLowerCase() %>"><%= s.getLibelle() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="col-md-2 text-md-end">
                                <span class="text-muted small me-2 d-inline-block mb-1"><i class="bi bi-files"></i> Total:
                                    <span id="totalCount">0</span></span>
                                <button class="btn btn-outline-secondary btn-sm rounded-pill w-100 w-md-auto"
                                    id="resetFilterBtn" style="border-color: #cbdde6;"><i class="bi bi-arrow-repeat"></i>
                                    Réinitialiser</button>
                            </div>
                        </div>
                    </div>

                    <!-- VUE TABLEAU (DESKTOP) -->
                    <div class="desktop-table-view">
                        <div class="table-responsive-custom">
                            <table class="table table-visa align-middle">
                                <thead>
                                    <tr>
                                        <th>Nom & Prénom</th>
                                        <th>Type</th>
                                        <th>Catégorie</th>
                                        <th>Date demande</th>
                                        <th>Statut</th>
                                        <th style="width: 110px;">Action</th>
                                    </tr>
                                </thead>
                                <tbody id="visaTableBodyDesktop">
                                    <!-- Rempli par JS -->
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- VUE CARTES (MOBILE) -->
                    <div class="mobile-cards-view" id="mobileCardsContainer"></div>

                    <div id="emptyMessage" class="empty-state" style="display: none;">
                        <i class="bi bi-inbox fs-1"></i>
                        <p class="mt-2 mb-0">Aucune demande de visa ne correspond aux filtres.</p>
                    </div>
                </div>
            </div>
            <footer class="text-center mt-4">
                <small>© République de Madagascar — Direction de l'Immigration. Demandes transformables en long séjour.</small>
            </footer>
        </div>
    </div>

    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>

    <script src="${pageContext.request.contextPath}/js/list-demande.js"></script>
    <!--<script src="${pageContext.request.contextPath}/js/component.js"></script>-->
</body>

</html>