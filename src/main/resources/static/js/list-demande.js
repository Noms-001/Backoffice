document.addEventListener("DOMContentLoaded", () => {

    // ---------- DONNÉES ----------
    let demandesList = [];

    // ---------- LOAD FROM API ----------
    async function loadDemandes() {
        try {
            const response = await fetch(contextPath + "/api/demandes");
            const data = await response.json();

            // Mapping backend → frontend
            demandesList = data.map(d => ({
                id: d.id,
                nom: d.nomDemandeur,
                prenom: d.prenomDemandeur,
                passeport: d.numeroPasseport,
                numeroVisa: d.numeroVisaTransformable,
                categorie: normalizeCategorie(d.categorieDemande),
                dateDemande: d.dateDemande,
                statut: normalizeStatut(d.statutDemande)
            }));

            renderAll();

        } catch (error) {
            console.error("Erreur chargement demandes :", error);
        }
    }

    // ---------- NORMALISATION ----------
    function normalizeStatut(statut) {
        if (!statut) return "indefini";

        const s = statut.toLowerCase();

        if (s.includes("creer")) return "creer";

        return "indefini";
    }

    function normalizeCategorie(cat) {
        if (!cat) return "autre";
        const c = cat.toLowerCase();
        if (c.includes("invest")) return "investisseur";
        if (c.includes("trav")) return "travailleur";
        return c;
    }

    // ---------- STATUT UI ----------
    function getStatutBadge(statut) {
        switch (statut) {
            case 'creer':
                return '<span class="status-badge status-en-attente"><i class="bi bi-plus-circle"></i> Créé</span>';
            default:
                return '<span class="status-badge">Indéfini</span>';
        }
    }

    function getStatutText(statut) {
        if (statut === "creer") return "Créé";
        return "Indéfini";
    }

    function getStatutIcon(statut) {
        if (statut === "creer") return '<i class="bi bi-plus-circle"></i>';
        return '<i class="bi bi-question-circle"></i>';
    }

    // ---------- FORMAT ----------
    function formatDate(dateString) {
        if (!dateString) return "—";
        const options = { year: 'numeric', month: 'short', day: 'numeric' };
        return new Date(dateString).toLocaleDateString('fr-FR', options);
    }

    function getCategorieLabel(categorie) {
        if (categorie === "investisseur") return '<span class="badge-category"><i class="bi bi-building me-1"></i>Investisseur</span>';
        if (categorie === "travailleur") return '<span class="badge-category"><i class="bi bi-briefcase me-1"></i>Travailleur</span>';
        return categorie;
    }

    function getCategorieText(categorie) {
        return categorie === "investisseur" ? "Investisseur" : "Travailleur";
    }

    function getCategorieIcon(categorie) {
        return categorie === "investisseur"
            ? '<i class="bi bi-building me-1"></i>'
            : '<i class="bi bi-briefcase me-1"></i>';
    }

    // ---------- FILTER ----------
    let currentSearch = "";
    let currentCategorie = "all";
    let currentStatut = "all";

    function getFilteredDemandes() {
        let filtered = [...demandesList];

        if (currentSearch.trim() !== "") {
            const searchLower = currentSearch.toLowerCase();
            filtered = filtered.filter(d =>
                d.nom?.toLowerCase().includes(searchLower) ||
                d.prenom?.toLowerCase().includes(searchLower) ||
                d.passeport?.toLowerCase().includes(searchLower) ||
                d.numeroVisa?.toLowerCase().includes(searchLower)
            );
        }

        if (currentCategorie !== "all") {
            filtered = filtered.filter(d => d.categorie === currentCategorie);
        }

        if (currentStatut !== "all") {
            filtered = filtered.filter(d => d.statut === currentStatut);
        }

        return filtered;
    }

    // ---------- ACTION ----------
    function handleEditClick(id) {
        const demande = demandesList.find(d => d.id === id);

        if (!demande) {
            alert("Demande introuvable.");
            return;
        }

        // Redirection vers le formulaire avec l'id
        window.location.href = contextPath + `/demande/form?id=${id}`;
    }

    // ---------- RENDER TABLE ----------
    function renderDesktopTable(filtered) {
        const tbody = document.getElementById("visaTableBodyDesktop");
        if (!tbody) return;

        if (filtered.length === 0) {
            tbody.innerHTML = `<tr><td colspan="7" class="text-center py-4 text-muted">Aucune demande trouvée</td></tr>`;
            return;
        }

        let html = "";

        filtered.forEach(d => {
            html += `
                <tr>
                    <td><strong>${escapeHtml(d.nom + " " + d.prenom)}</strong></td>
                    <td>${escapeHtml(d.passeport || "")}</td>
                    <td>${escapeHtml(d.numeroVisa || "")}</td>
                    <td>${getCategorieLabel(d.categorie)}</td>
                    <td>${formatDate(d.dateDemande)}</td>
                    <td>${getStatutBadge(d.statut)}</td>
                    <td>
                        <button class="btn btn-modifier edit-btn" data-id="${d.id}" data-view="demande-edit">
                            Modifier
                        </button>
                    </td>
                </tr>
            `;
        });

        tbody.innerHTML = html;

        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener("click", () => {
                handleEditClick(parseInt(btn.dataset.id));
            });
        });
    }

    // ---------- RENDER ----------
    function renderAll() {
        const filtered = getFilteredDemandes();

        document.getElementById("totalCount").innerText = filtered.length;

        renderDesktopTable(filtered);
    }

    // ---------- UTILS ----------
    function escapeHtml(str) {
        if (!str) return "";
        return str.replace(/[&<>]/g, m => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;'
        }[m]));
    }

    // ---------- EVENTS ----------
    const searchInput = document.getElementById("searchInput");
    const categorieFilter = document.getElementById("categorieFilter");
    const statutFilter = document.getElementById("statutFilter");
    const resetBtn = document.getElementById("resetFilterBtn");

    function updateFiltersAndRender() {
        currentSearch = searchInput.value;
        currentCategorie = categorieFilter.value;
        currentStatut = statutFilter.value;
        renderAll();
    }

    searchInput?.addEventListener("input", updateFiltersAndRender);
    categorieFilter?.addEventListener("change", updateFiltersAndRender);
    statutFilter?.addEventListener("change", updateFiltersAndRender);

    resetBtn?.addEventListener("click", () => {
        searchInput.value = "";
        categorieFilter.value = "all";
        statutFilter.value = "all";
        updateFiltersAndRender();
    });

    // 🚀 LOAD DATA
    loadDemandes();
});