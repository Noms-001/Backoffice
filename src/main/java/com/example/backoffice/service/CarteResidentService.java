package com.example.backoffice.service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backoffice.entity.CarteResident;
import com.example.backoffice.entity.Demande;
import com.example.backoffice.entity.Passeport;
import com.example.backoffice.repository.CarteResidentRepository;

@Service
public class CarteResidentService {

    @Autowired
    private CarteResidentRepository carteResidentRepository;

    public CarteResident createCarteResidentFromDemandeAndPasseport(Demande demande, Passeport passeport) {

        CarteResident carteResident = createCarteResident();

        if (carteResident.getDateDebut() == null || carteResident.getDateFin() == null) {
            throw new RuntimeException("Les dates sont obligatoires");
        }

        if (!carteResident.getDateDebut().isBefore(carteResident.getDateFin())) {
            throw new RuntimeException("La date de début doit être inférieure à la date de fin");
        }

        boolean exists = carteResidentRepository.findByReference(carteResident.getReference()).isPresent();

        if (exists) {
            throw new RuntimeException("Une carte de résident avec cette référence existe déjà");
        }

        carteResident.setDemande(demande);
        carteResident.setPasseport(passeport);

        return carteResidentRepository.save(carteResident);
    }

    private String generateReference() {
        String ref = null;
        do {
            int number = ThreadLocalRandom.current().nextInt(0, 100000);
            ref = String.format("CRT-MG-%05d", number);
        } while (carteResidentRepository.findByReference(ref).isPresent());
        return ref;
    }

    public CarteResident createCarteResident() {
        CarteResident carteResident = new CarteResident();

        String reference = generateReference();

        if (reference != null) {
            carteResident.setReference(reference);
        }

        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin = dateDebut.plusYears(10);

        carteResident.setDateDebut(dateDebut);
        carteResident.setDateFin(dateFin);

        return carteResident;
    }
}
