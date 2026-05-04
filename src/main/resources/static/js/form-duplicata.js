(function () {
    // DOM éléments
    const form = document.getElementById('duplicataForm');
    const carteResidenceInput = document.getElementById('carteResidenceNum');
    const motifTextarea = document.getElementById('motifDuplicata');
    const resetBtn = document.getElementById('resetFormBtn');
    const alertDiv = document.getElementById('formAlert');
    const alertTextSpan = document.getElementById('alertText');

    // Fonctions helper pour l'alerte
    function showAlert(message, isSuccess = true, isWarning = false) {
        alertDiv.style.display = 'block';
        alertDiv.classList.remove('alert-success', 'alert-danger', 'alert-warning', 'bg-success-subtle', 'bg-danger-subtle', 'bg-warning-subtle');

        if (isSuccess) {
            alertDiv.classList.add('alert-success', 'bg-success-subtle', 'text-success', 'border', 'border-success');
            alertDiv.style.background = '#e0f7f2';
            alertDiv.style.color = '#1d6f63';
        } else if (isWarning) {
            alertDiv.classList.add('alert-warning', 'bg-warning-subtle', 'text-warning', 'border', 'border-warning');
            alertDiv.style.background = '#fff4e5';
            alertDiv.style.color = '#b45f1b';
        } else {
            alertDiv.classList.add('alert-danger', 'bg-danger-subtle', 'text-danger', 'border', 'border-danger');
            alertDiv.style.background = '#fee9e9';
            alertDiv.style.color = '#c23d3d';
        }
        alertTextSpan.innerText = message;
        // Scroll smooth pour voir l'alerte
        alertDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

        // Si succès, on peut le laisser sans auto-cacher immédiat, mais auto-hide après 7 secondes
        if (isSuccess) {
            setTimeout(() => {
                if (alertDiv.style.display === 'block') {
                    // On peut le laisser mais réduire l'opacité ou on le laisse (on choisit de le laisser)
                    // L'utilisateur peut le fermer en réinitialisant ou nouvelle soumission
                }
            }, 7000);
        }
    }

    function hideAlert() {
        alertDiv.style.display = 'none';
        alertDiv.classList.remove('alert-success', 'alert-danger', 'alert-warning');
    }

    // validation spécifique: numéro de carte de résidence non vide, longueur minimale indicative
    function validateCarteNumber(value) {
        if (!value || value.trim() === "") {
            return { valid: false, message: "Le numéro de carte de résidence est obligatoire." };
        }
        const trimmed = value.trim();
        if (trimmed.length < 4) {
            return { valid: false, message: "Le numéro de carte doit contenir au moins 4 caractères (format invalide)." };
        }
        // Vérifie la présence de caractères alphanumériques de base (tolérance tirets, slash, lettres)
        const alphanumRegex = /^[A-Za-z0-9\-\/]+$/;
        if (!alphanumRegex.test(trimmed)) {
            return { valid: false, message: "Le numéro de carte ne doit contenir que des lettres, chiffres, tirets ou slashs." };
        }
        return { valid: true, message: "" };
    }

    // Nettoyer les erreurs de validation visuelle
    function clearValidationErrors() {
        carteResidenceInput.classList.remove('is-invalid');
        // On enlève le message custom s'il existe
        const existingFeedback = document.getElementById('carteErrorMsg');
        if (existingFeedback) {
            existingFeedback.style.display = 'none';
        }
    }

    function showFieldError(message) {
        carteResidenceInput.classList.add('is-invalid');
        const errorFeedback = document.getElementById('carteErrorMsg');
        if (errorFeedback) {
            errorFeedback.innerText = message;
            errorFeedback.style.display = 'block';
        }
        hideAlert();
        showAlert(message, false);
    }

    // Réinitialisation complète du formulaire
    function resetForm() {
        carteResidenceInput.value = '';
        motifTextarea.value = '';
        clearValidationErrors();
        hideAlert();
        carteResidenceInput.classList.remove('is-invalid');
        // Efface tout message d'erreur résiduel
        const errorSpan = document.getElementById('carteErrorMsg');
        if (errorSpan) errorSpan.style.display = 'none';
        // Option : afficher une petite notification discrète
        showAlert("Formulaire réinitialisé. Vous pouvez effectuer une nouvelle demande.", true);
        setTimeout(() => {
            if (alertDiv.style.display === 'block' && alertDiv.classList.contains('alert-success')) {
                // on laisse mais on ne force pas la suppression immédiate
            }
        }, 300);
        carteResidenceInput.focus();
    }

    // Validation live pour enlever le rouge
    carteResidenceInput.addEventListener('input', function () {
        if (this.classList.contains('is-invalid')) {
            this.classList.remove('is-invalid');
            const errorSpan = document.getElementById('carteErrorMsg');
            if (errorSpan) errorSpan.style.display = 'none';
            if (alertDiv.style.display === 'block' && alertDiv.classList.contains('alert-danger')) {
                // si alerte d'erreur active, on la cache (l'utilisateur corrige)
                hideAlert();
            }
        }
    });

    // Empêcher les caractères spéciaux excessifs? just propose mais reste souple
    carteResidenceInput.addEventListener('blur', function () {
        const val = this.value.trim();
        if (val !== "" && !/^[A-Za-z0-9\-\/]+$/.test(val)) {
            // n'invalide pas totalement mais show hint
            if (!document.getElementById('specialCharHint')) {
                const hint = document.createElement('div');
                hint.id = 'specialCharHint';
                hint.className = 'text-warning small mt-1';
                hint.innerHTML = '<i class="bi bi-exclamation-triangle"></i> Utilisez uniquement lettres, chiffres, tirets ou slashs.';
                this.parentNode.insertAdjacentElement('afterend', hint);
                setTimeout(() => { if (hint) hint.remove(); }, 2500);
            }
        } else {
            const existingHint = document.getElementById('specialCharHint');
            if (existingHint) existingHint.remove();
        }
    });

    // Gestion du reset
    resetBtn.addEventListener('click', (e) => {
        e.preventDefault();
        resetForm();
    });

    // Focus init
    carteResidenceInput.focus();

    // information: bloquer l'envoi multiple rapide (option non obligatoire mais ajout)
    let submitPending = false;
    const originalSubmit = handleSubmit;
    const guardedSubmit = function (e) {
        if (submitPending) {
            e.preventDefault();
            showAlert("Une demande est déjà en cours de traitement, veuillez patienter.", false, true);
            return;
        }
        submitPending = true;
        const result = originalSubmit(e);
        setTimeout(() => { submitPending = false; }, 2000);
        return result;
    };
    form.removeEventListener('submit', handleSubmit);
    form.addEventListener('submit', guardedSubmit);
})();