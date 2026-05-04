document.addEventListener("DOMContentLoaded", () => {

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

    const navLinks = document.querySelectorAll('.nav-sidebar .nav-link');

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            setActiveLink(link);

            // mobile UX: close sidebar after click
            if (window.innerWidth <= 768 && !isSidebarClosed) {
                setSidebarState(true);
            }
        });
    });


    /* =========================
       RESPONSIVE HANDLING
    ========================== */
    window.addEventListener('resize', () => {
        // no forced behavior, we respect user preference
    });

});