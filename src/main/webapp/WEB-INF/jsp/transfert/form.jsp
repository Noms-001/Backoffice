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
    <title>Transfert de visa | Nouveau passeport · Immigration Madagascar</title>
    <!-- Bootstrap 5 CSS + Icons + Google Fonts -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link
        href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,300;14..32,400;14..32,500;14..32,600;14..32,700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-transfert.css">
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
                            <h3 class="mb-1 text-white"><i class="bi bi-arrow-left-right me-2"></i>Transfert de visa</h3>
                            <p class="mb-0 opacity-75 text-white" style="font-size: 0.9rem;">Report de visa vers un nouveau
                                passeport · long séjour</p>
                        </div>
                        <div class="badge-officiel mt-2 mt-sm-0 text-white"><i class="bi bi-building"></i> République de
                            Madagascar · Immigration</div>
                    </div>
                </div>

                <div class="form-section">
                    <form id="transfertVisaForm" method="post" action="insert">
                        <!-- Section : ancien visa -->
                        <div class="mb-4">
                            <div class="d-flex align-items-center gap-2 mb-2">
                                <i class="bi bi-ticket-perforated fs-5 text-secondary"></i>
                                <h6 class="fw-bold mb-0" style="color: #1B5E5A;">Visa à transférer</h6>
                            </div>
                            <div class="row g-3">
                                <div class="col-12">
                                    <label class="form-label">Numéro du visa<span
                                            class="required-star">*</span></label>
                                    <div class="input-group-custom">
                                        <input type="text" name="numeroVisa" class="form-control" id="numeroVisa"
                                            placeholder="Ex: VTR-2451-MG" required>
                                        <div class="invalid-feedback">Veuillez indiquer le numéro de visa actuel.</div>
                                    </div>
                                    <div class="form-text text-muted small">Visa long séjour délivré précédemment</div>
                                </div>
                            </div>
                        </div>

                        <!-- séparateur visuel : nouveau passeport -->
                        <div class="divider-section">
                            <div class="divider-line"></div>
                            <div class="divider-icon"><i class="bi bi-passport me-1"></i> NOUVEAU PASSEPORT</div>
                            <div class="divider-line"></div>
                        </div>

                        <!-- Section nouveau passeport -->
                        <div class="mb-3">
                            <div class="row g-4">
                                <div class="col-md-12">
                                    <label class="form-label">Numéro de passeport <span
                                            class="required-star">*</span></label>
                                    <input type="text" name="passeport.reference" class="form-control" id="nouveauPasseportNum"
                                        placeholder="Ex: XC1234567" required>
                                    <div class="invalid-feedback">Le numéro de passeport est requis.</div>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Date de délivrance <span
                                            class="required-star">*</span></label>
                                    <input type="date" name="passeport.dateDelivrance" class="form-control" id="dateDelivrance" required>
                                    <div class="invalid-feedback">Date de délivrance obligatoire.</div>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Lieu de délivrance <span
                                            class="required-star">*</span></label>
                                    <input type="text" name="passeport.lieuDelivrance" class="form-control" id="lieuDelivrance"
                                        placeholder="Ex: Antananarivo, Paris, ..." required>
                                    <div class="invalid-feedback">Lieu de délivrance requis.</div>
                                </div>
                                <div class="col-md-12">
                                    <label class="form-label">Date d'expiration <span class="required-star">*</span></label>
                                    <input type="date" name="passeport.dateExpiration" class="form-control" id="dateExpiration" required>
                                    <div class="invalid-feedback">Veuillez indiquer la date d'expiration du passeport.</div>
                                </div>
                            </div>
                        </div>

                        <!-- informations complémentaires optionnelles : motif (pour plus de réalisme) -->
                        <div class="mt-4 mb-4">
                            <label class="form-label"><i class="bi bi-chat-left-text me-1"></i>Motif du transfert
                                (optionnel)</label>
                            <textarea class="form-control" rows="2" id="motifTransfert"
                                placeholder="Ex: Renouvellement passeport, perte / vol, changement d'état civil..."></textarea>
                        </div>

                        <!-- boutons action -->
                        <div class="button-group d-flex justify-content-end align-items-center gap-2">
                            <button type="button" class="btn-reset" id="resetFormBtn">
                                <i class="bi bi-arrow-counterclockwise"></i> Réinitialiser
                            </button>
                            <button type="submit" class="btn-submit text-white">
                                <i class="bi bi-check-lg me-2"></i> Transférer le visa
                            </button>
                        </div>

                        <!-- zone d'alerte (message de confirmation ou d'erreur) -->
                        <div id="formAlert" class="alert-message alert d-flex align-items-center" role="alert">
                            <span id="alertText"></span>
                        </div>
                    </form>

                    <!-- petite note légale -->
                    <div class="mt-4 pt-2 border-top text-center text-muted small">
                        <i class="bi bi-shield-check"></i> Après validation, le visa transféré sera associé au nouveau
                        passeport.
                    </div>
                </div>
            </div>
            <footer class="text-center mt-4">
                <small>© République de Madagascar — Direction de l'Immigration. Transfert de visa vers nouveau
                    passeport.</small>
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

    <script src="${pageContext.request.contextPath}/js/form-transfert.js"></script>
    <!--<script src="${pageContext.request.contextPath}/js/component.js"></script>-->
</body>

</html>