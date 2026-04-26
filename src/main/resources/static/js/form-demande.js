document.addEventListener("DOMContentLoaded", () => {
    const selectedDocumentIds = Array.from(
        document.querySelectorAll('.selected-document-id')
    ).map(input => parseInt(input.value));
    
    // ---------- LISTE COMPLETE NATIONALITES pour autocomplete (plus de 50) ----------
    const nationalitesList = [];

    document.querySelectorAll("#nationalite_data option").forEach(opt => {
        nationalitesList.push({
            id: opt.value,
            libelle: opt.textContent
        });
    });

    // autocomplete logic
    const nationaliteInput = document.getElementById('nationalite_input');
    const nationaliteHidden = document.getElementById('nationalite_hidden');
    const autocompleteList = document.getElementById('nationalite_autocomplete_list');

    if (!nationaliteInput || !autocompleteList) return;

    function closeAutocomplete() {
        autocompleteList.style.display = 'none';
        autocompleteList.innerHTML = '';
    }

    function showAutocomplete(filterText) {
        if (!filterText) { closeAutocomplete(); return; }
        const matches = nationalitesList
            .filter(n => n.libelle.toLowerCase().includes(filterText.toLowerCase()))
            .slice(0, 12);
        if (matches.length === 0) { closeAutocomplete(); return; }
        autocompleteList.innerHTML = '';
        matches.forEach(m => {
            const div = document.createElement('div');
            div.innerHTML = m.libelle;
            div.addEventListener('click', () => {
                nationaliteHidden.value = m.id;
                nationaliteInput.value = m.libelle;
                closeAutocomplete();
                nationaliteInput.classList.remove('is-invalid');
            });
            autocompleteList.appendChild(div);
        });
        autocompleteList.style.display = 'block';
    }

    nationaliteInput.addEventListener('input', function (e) {
        const val = e.target.value;
        if (val === "") {
            nationaliteHidden.value = "";
            closeAutocomplete();
        } else {
            showAutocomplete(val);
        }
    });
    document.addEventListener('click', function (e) {
        if (e.target !== nationaliteInput) closeAutocomplete();
    });
    nationaliteInput.addEventListener('blur', function () {
        setTimeout(() => {
            if (!nationaliteHidden.value && nationaliteInput.value.trim() !== "") {
                const match = nationalitesList.find(
                    n => n.libelle.toLowerCase() === nationaliteInput.value.toLowerCase()
                );
                if (match) {
                    nationaliteHidden.value = match.id;
                    nationaliteInput.value = match.libelle;
                } else {
                    nationaliteHidden.value = "";
                    nationaliteInput.classList.add('is-invalid');
                }
            } else if (nationaliteInput.value === "") {
                nationaliteHidden.value = "";
            }
            closeAutocomplete();
        }, 150);
    });

    // step management
    let currentStep = 1;
    const totalSteps = 4;
    const stepContents = { 1: document.getElementById('step1'), 2: document.getElementById('step2'), 3: document.getElementById('step3'), 4: document.getElementById('step4') };
    const stepBadges = { 1: document.getElementById('step1Badge'), 2: document.getElementById('step2Badge'), 3: document.getElementById('step3Badge'), 4: document.getElementById('step4Badge') };

    function updateStepDisplay() {
        for (let i = 1; i <= totalSteps; i++) {
            stepContents[i].style.display = i === currentStep ? 'block' : 'none';
            if (i === currentStep) { stepBadges[i].classList.add('active-step'); stepBadges[i].classList.remove('completed-step'); }
            else if (i < currentStep) { stepBadges[i].classList.add('completed-step'); stepBadges[i].classList.remove('active-step'); }
            else { stepBadges[i].classList.remove('active-step', 'completed-step'); }
        }
    }

    function validateCurrentStep() {
        let valid = true;
        if (currentStep === 1) {
            const fields = ['nom', 'date_naissance', 'lieu_naissance', 'situation_familiale', 'adresse_mada', 'numero'];
            fields.forEach(id => { let el = document.getElementById(id); if (!el.value.trim()) { el.classList.add('is-invalid'); valid = false; } else el.classList.remove('is-invalid'); });
            if (!document.getElementById('situation_familiale').value) { document.getElementById('situation_familiale').classList.add('is-invalid'); valid = false; }
            // nationalite validation
            if (!nationaliteHidden.value) { nationaliteInput.classList.add('is-invalid'); valid = false; } else nationaliteInput.classList.remove('is-invalid');
        }
        else if (currentStep === 2) {
            const ref = document.getElementById('passeport_ref'), dDel = document.getElementById('date_delivrance'), lDel = document.getElementById('lieu_delivrance'), dExp = document.getElementById('date_expiration');
            if (!ref.value.trim()) { ref.classList.add('is-invalid'); valid = false; } else ref.classList.remove('is-invalid');
            if (!dDel.value) { dDel.classList.add('is-invalid'); valid = false; } else dDel.classList.remove('is-invalid');
            if (!lDel.value.trim()) { lDel.classList.add('is-invalid'); valid = false; } else lDel.classList.remove('is-invalid');
            if (!dExp.value) { dExp.classList.add('is-invalid'); valid = false; } else dExp.classList.remove('is-invalid');
            if (dDel.value && dExp.value && new Date(dExp.value) <= new Date(dDel.value)) { alert("Expiration après délivrance"); dExp.classList.add('is-invalid'); valid = false; }
        }
        else if (currentStep === 3) {
            let num = document.getElementById('num_visa'), dateE = document.getElementById('date_entree'), lieuE = document.getElementById('lieu_entree'), catD = document.getElementById('categorie_demande');
            if (!num.value.trim()) { num.classList.add('is-invalid'); valid = false; } else num.classList.remove('is-invalid');
            if (!dateE.value) { dateE.classList.add('is-invalid'); valid = false; } else dateE.classList.remove('is-invalid');
            if (!lieuE.value.trim()) { lieuE.classList.add('is-invalid'); valid = false; } else lieuE.classList.remove('is-invalid');
            if (!catD.value) { valid = false; } else { catD.classList.remove('is-invalid'); }
        }
        else if (currentStep === 4) {
            let allCommon = true; document.querySelectorAll('.common-doc').forEach(chk => { if (!chk.checked) allCommon = false; });
            if (!allCommon) { alert("Tous les documents communs doivent être cochés."); valid = false; }
            const cat = document.getElementById('categorie_demande').value;
            const catId = parseInt(cat);
            const selectedDocs = Array.from(
                document.querySelectorAll('input[name="documentIds"]:checked')
            ).map(cb => Number(cb.value));
            let specOk = true;
            if (catId === 2) {

                const required = categorieDocumentsMap["2"]
                    .map(d => d.id); // tous les docs obligatoires backend

                const specOk = required.every(id => selectedDocs.includes(Number(id)));

                if (!specOk) {
                    document.getElementById('specificDocsError').innerText =
                        "Investisseur : documents obligatoires manquants";
                } else {
                    document.getElementById('specificDocsError').innerText = "";
                }
            }
            else if (catId === 1) {

                const required = categorieDocumentsMap["1"]
                    .map(d => d.id);

                const specOk = required.every(id => selectedDocs.includes(Number(id)));

                if (!specOk) {
                    document.getElementById('specificDocsError').innerText =
                        "Travailleur : documents obligatoires manquants";
                } else {
                    document.getElementById('specificDocsError').innerText = "";
                }
            }
            else specOk = false;
            if (!specOk) valid = false;
        }
        return valid;
    }

    const categorieDocumentsMap = {};

    document.querySelectorAll("#categorie_document_data option").forEach(opt => {
        const catId = opt.dataset.categorie;

        if (!categorieDocumentsMap[catId]) {
            categorieDocumentsMap[catId] = [];
        }

        categorieDocumentsMap[catId].push({
            id: opt.value,
            libelle: opt.textContent
        });
    });

    function renderSpecificDocuments() {
        const cat = document.getElementById('categorie_demande').value;
        const catId = parseInt(cat);
        const container = document.getElementById('specificDocsInner');

        const docs = categorieDocumentsMap[catId];

        if (!docs || docs.length === 0) {
            container.innerHTML = `<div class="text-warning">⚠️ Aucun document</div>`;
            return;
        }

        let html = "";

        docs.forEach(doc => {
            const checked = selectedDocumentIds.includes(Number(doc.id)) ? "checked" : "";
            html += `
            <div class="form-check mb-2">
                <input class="form-check-input" type="checkbox" name="documentIds" value="${doc.id}" ${checked}>
                <label class="form-check-label">${doc.libelle}</label>
            </div>`;
        });

        container.innerHTML = html;
    }

    function goNext() { if (validateCurrentStep()) { if (currentStep < totalSteps) { currentStep++; if (currentStep === 4) renderSpecificDocuments(); updateStepDisplay(); } } else { let inv = document.querySelector('.is-invalid'); if (inv) inv.scrollIntoView({ behavior: 'smooth', block: 'center' }); } }
    function goPrev() { if (currentStep > 1) { currentStep--; updateStepDisplay(); } }
    let isSubmitting = false;

    function submitFinal() {
        if (isSubmitting) return;

        if (currentStep !== 4) return;
        if (!validateCurrentStep()) return;

        isSubmitting = true;
        document.getElementById("multiStepForm").submit();
    }

    document.querySelectorAll('.next-step').forEach(btn => btn.addEventListener('click', goNext));
    document.querySelectorAll('.prev-step').forEach(btn => btn.addEventListener('click', goPrev));
    document.getElementById('submitBtn').addEventListener('click', submitFinal);
    document.getElementById('multiStepForm').addEventListener('submit', (e) => e.preventDefault());
    document.getElementById('categorie_demande').addEventListener('change', function () { if (currentStep === 4) renderSpecificDocuments(); });
    updateStepDisplay();
    // initialisation autocomplete
    nationaliteInput.value = "";

    const selectedOption = document.querySelector("#nationalite_data option[selected]");

    if (selectedOption) {
        nationaliteInput.value = selectedOption.textContent;
        nationaliteHidden.value = selectedOption.value;
    }

    setTimeout(() => {
        document.querySelectorAll('.toast').forEach(toastEl => {
            const toast = new bootstrap.Toast(toastEl, {
                animation: true,
                autohide: true,
                delay: 5000
            });
            toast.show();
        });
    }, 100);
});