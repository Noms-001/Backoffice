(function () {
    const form = document.getElementById('transfertVisaForm');
    const numeroVisaInput = document.getElementById('numeroVisa');
    const nouveauPasseportNum = document.getElementById('nouveauPasseportNum');
    const dateDelivrance = document.getElementById('dateDelivrance');
    const lieuDelivrance = document.getElementById('lieuDelivrance');
    const dateExpiration = document.getElementById('dateExpiration');
    const motifTransfert = document.getElementById('motifTransfert');
    const resetBtn = document.getElementById('resetFormBtn');
    const alertDiv = document.getElementById('formAlert');
    const alertTextSpan = document.getElementById('alertText');

    // Helper pour afficher une alerte (style succès ou erreur)
    function showAlert(message, isSuccess = true) {
        alertDiv.style.display = 'flex';
        alertDiv.classList.remove('alert-danger', 'alert-success');
        if (isSuccess) {
            alertDiv.classList.add('alert-success', 'bg-success-subtle', 'text-success', 'border', 'border-success');
            alertDiv.style.background = '#e0f7f2';
            alertDiv.style.color = '#1d6f63';
        } else {
            alertDiv.classList.add('alert-danger', 'bg-danger-subtle', 'text-danger', 'border', 'border-danger');
            alertDiv.style.background = '#fee9e9';
            alertDiv.style.color = '#c23d3d';
        }
        alertTextSpan.innerHTML = '<i class="bi bi-info-circle-fill me-2" style="font-size: 1.1rem;"></i> ' + message;
        // auto scroll to alert
        alertDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        // masquer après 6s si succès mais on laisse l'utiliser
        if (isSuccess) {
            setTimeout(() => {
                if (alertDiv.style.display === 'flex') {
                    alertDiv.style.opacity = '0.9';
                }
            }, 5000);
        }
    }

    function hideAlert() {
        alertDiv.style.display = 'none';
        alertDiv.classList.remove('alert-success', 'alert-danger', 'bg-success-subtle', 'bg-danger-subtle');
    }

    // validation des champs (côté client avant envoi simulé)
    function validateForm() {
        let isValid = true;
        const requiredFields = [
            { field: numeroVisaInput, name: "Numéro de visa" },
            { field: nouveauPasseportNum, name: "Numéro de passeport" },
            { field: dateDelivrance, name: "Date de délivrance" },
            { field: lieuDelivrance, name: "Lieu de délivrance" },
            { field: dateExpiration, name: "Date d'expiration" }
        ];

        // remove previous invalid styles
        requiredFields.forEach(({ field }) => {
            field.classList.remove('is-invalid');
        });

        for (let { field, name } of requiredFields) {
            if (!field.value.trim()) {
                field.classList.add('is-invalid');
                isValid = false;
                // show first error alert but let's accumulate but display specific
                showAlert(`Le champ "${name}" est obligatoire.`, false);
                field.focus();
                return false;
            }
        }

        // validation date expiration > date délivrance
        const delDate = new Date(dateDelivrance.value);
        const expDate = new Date(dateExpiration.value);
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        if (expDate <= delDate) {
            dateExpiration.classList.add('is-invalid');
            showAlert("La date d'expiration doit être postérieure à la date de délivrance.", false);
            return false;
        }

        if (expDate <= today) {
            dateExpiration.classList.add('is-invalid');
            showAlert("Le passeport est déjà expiré ou expiré aujourd'hui. Le transfert nécessite un passeport valide.", false);
            return false;
        }

        return true;
    }

    // Fonction de réinitialisation complète du formulaire et nettoyage des alertes / validations
    function resetFormFields() {
        form.reset();
        // Supprimer les classes d'invalidité
        const inputs = form.querySelectorAll('.form-control');
        inputs.forEach(input => {
            input.classList.remove('is-invalid', 'is-valid');
        });
        hideAlert();

        numeroVisaInput.value = '';
        nouveauPasseportNum.value = '';
        dateDelivrance.value = '';
        lieuDelivrance.value = '';
        dateExpiration.value = '';
        motifTransfert.value = '';
        // enlever alerte et style
        alertDiv.style.display = 'none';
        // focus sur premier champ
        numeroVisaInput.focus();
    }

    // Event listeners
    resetBtn.addEventListener('click', (e) => {
        e.preventDefault();
        resetFormFields();
        showAlert("Formulaire réinitialisé. Vous pouvez saisir un nouveau transfert.", true);
        // masquer l'alerte après 3s
        setTimeout(() => {
            if (alertDiv.style.display === 'flex') {
                hideAlert();
            }
        }, 2800);
    });

    // validation en temps réel pour retirer les erreurs lors de la frappe
    const allInputs = [numeroVisaInput, nouveauPasseportNum, dateDelivrance, lieuDelivrance, dateExpiration];
    allInputs.forEach(inp => {
        inp.addEventListener('input', function () {
            this.classList.remove('is-invalid');
            if (alertDiv.style.display === 'flex' && alertDiv.classList.contains('alert-danger')) {
                // optionnel : cacher l'alerte d'erreur quand l'utilisateur commence à taper
                if (this.classList.contains('is-invalid') === false) {
                    hideAlert();
                }
            }
        });
    });

    // Ajout d'un contrôle de cohérence date expiration vs date délivrance à la volée (user friendly)
    dateDelivrance.addEventListener('change', function () {
        if (dateExpiration.value && dateDelivrance.value) {
            const delDate = new Date(dateDelivrance.value);
            const expDate = new Date(dateExpiration.value);
            if (expDate <= delDate) {
                dateExpiration.classList.add('is-invalid');
            } else {
                dateExpiration.classList.remove('is-invalid');
            }
        }
    });
    dateExpiration.addEventListener('change', function () {
        if (dateDelivrance.value && dateExpiration.value) {
            const delDate = new Date(dateDelivrance.value);
            const expDate = new Date(dateExpiration.value);
            if (expDate <= delDate) {
                dateExpiration.classList.add('is-invalid');
                showAlert("La date d'expiration doit être postérieure à la date de délivrance.", false);
            } else {
                dateExpiration.classList.remove('is-invalid');
                hideAlert();
            }
        }
    });

    // petite initialisation : focus élégant
    numeroVisaInput.focus();
})();