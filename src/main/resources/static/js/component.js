document.addEventListener("DOMContentLoaded", () => {

    const NAVBAR_CONFIG = {
        "demande-form": {
            title: "Demande de nouveau titre",
            desc: "Formulaire d'obtention de titre de séjour"
        },
        "transfert-form": {
            title: "DEmande de transfert de visa",
            desc: "Changement de passeport"
        },
        "duplicata-form": {
            title: "Demande duplicata",
            desc: "Demande de duplicata de document"
        },
        "demande-list": {
            title: "Liste des demandes",
            desc: "Suivi des dossiers administratifs"
        },
        "demande-edit": {
            title: "Modifier la demande",
            desc: "Mise à jour des informations de la demande"
        }
    };

    /* =========================
       SIDEBAR STATE
    ========================== */
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.getElementById('mainContent');
    const toggleBtn = document.getElementById('sidebarToggleBtn');

    let isSidebarClosed = false;

    function setSidebarState(closed) {
        if (!sidebar || !mainContent) return;

        sidebar.classList.toggle('closed', closed);
        mainContent.classList.toggle('expanded', closed);

        isSidebarClosed = closed;
        localStorage.setItem('sidebarClosed', closed);
    }

    if (toggleBtn) {
        toggleBtn.addEventListener('click', () => {
            setSidebarState(!isSidebarClosed);
        });
    }

    // Restore sidebar state
    const savedState = localStorage.getItem('sidebarClosed') === 'true';
    setSidebarState(savedState);

    function updateNavbar(view) {
        const config = NAVBAR_CONFIG[view];
        if (!config) return;

        const title = document.getElementById('pageTitle');
        const desc = document.getElementById('pageDescription');

        if (title) title.textContent = config.title;
        if (desc) desc.textContent = config.desc;
    }

    /* =========================
       ACTIVE LINK SIDEBAR
    ========================== */
    const navLinks = document.querySelectorAll('.nav-sidebar .nav-link');

    function setActiveLink(link) {
        navLinks.forEach(l => l.classList.remove('active'));
        link.classList.add('active');

        const view = link.getAttribute('data-view');

        if (view) {
            updateNavbar(view);
        }
    }

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            setActiveLink(link);

            // mobile UX: close sidebar after click
            if (window.innerWidth <= 768 && !isSidebarClosed) {
                setSidebarState(true);
            }
        });
    });

    function initActiveFromDataView() {
        const firstActive = document.querySelector('.nav-sidebar .nav-link[data-view]');

        if (firstActive) {
            setActiveLink(firstActive);
        }
    }

    initActiveFromDataView();

    /* =========================
    INIT NAVBAR FROM ACTIVE LINK
    ========================= */
    function initFromActiveLink() {
        const active = document.querySelector('.nav-sidebar .nav-link.active');

        if (active) {
            const view = active.getAttribute('data-view');
            updateNavbar(view);
        }
    }

    initFromActiveLink();


    /* =========================
       RESPONSIVE HANDLING
    ========================== */
    window.addEventListener('resize', () => {
        // no forced behavior, we respect user preference
    });

});