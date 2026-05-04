package com.example.backoffice.service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backoffice.entity.Demande;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.entity.Visa;
import com.example.backoffice.repository.VisaRepository;

@Service
public class VisaService {

    @Autowired
    private VisaRepository visaRepository;

    public Visa createVisaFromDemandeAndPasseport(Demande demande, Passeport passeport, LocalDate dateDebut, LocalDate dateFin) {

        Visa visa = createVisa(dateDebut, dateFin);

        if (visa.getDateDebut() == null || visa.getDateFin() == null) {
            throw new RuntimeException("Les dates sont obligatoires");
        }

        if (!visa.getDateDebut().isBefore(visa.getDateFin())) {
            throw new RuntimeException("La date de début doit être inférieure à la date de fin");
        }

        boolean exists = visaRepository.findByReference(visa.getReference()).isPresent();

        if (exists) {
            throw new RuntimeException("Un visa avec cette référence existe déjà");
        }

        visa.setDemande(demande);
        visa.setPasseport(passeport);
        
        return visaRepository.save(visa);
    }

    private String generateReference() {
        String ref = null;

        do {
            int number = ThreadLocalRandom.current().nextInt(0, 100000);
            ref = String.format("VISA-MG-%05d", number);
        } while (visaRepository.findByReference(ref).isPresent());

        return ref;
    }

    private Visa createVisa(LocalDate dateDebut, LocalDate dateFin) {
        Visa visa = new Visa();

        String reference = generateReference();

        if (reference != null) {
            visa.setReference(reference);
        }

        visa.setDateDebut(dateDebut);
        visa.setDateFin(dateFin);

        return visa;
    }

}
