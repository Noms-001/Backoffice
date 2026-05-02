<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h4 class="text-white mb-0"><i class="bi bi-passport me-2"></i>Immigration</h4>
        <p class="text-white-50">Madagascar · Direction générale</p>
    </div>

    <ul class="nav nav-sidebar flex-column" id="sidebarNav">

        <li class="nav-item">
            <a class="nav-link" data-view="demande-form" href="${pageContext.request.contextPath}/demande/form">
                <i class="bi bi-file-earmark-plus"></i> Nouveau titre
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-view="transfert-form" href="${pageContext.request.contextPath}/demande/transfert/form">
                <i class="bi bi-arrow-left-right"></i> Transfert de visa
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-view="duplicata-form" href="${pageContext.request.contextPath}/demande/duplicata/form">
                <i class="bi bi-files"></i> Duplicata
            </a>
        </li>

        <li class="nav-item mt-3">
            <a class="nav-link" data-view="demande-list" href="${pageContext.request.contextPath}/demande/list">
                <i class="bi bi-card-list"></i> Liste des demandes
            </a>
        </li>

    </ul>

    <div class="sidebar-footer text-white-50">
        <i class="bi bi-shield-check"></i> Sécurisé · v1.0
    </div>
</div>