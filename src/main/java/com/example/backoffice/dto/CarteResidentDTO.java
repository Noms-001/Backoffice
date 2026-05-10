package com.example.backoffice.dto;

import lombok.Data;
import java.time.LocalDate;

import com.example.backoffice.entity.CarteResident;

@Data
public class CarteResidentDTO {

    private String reference;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public CarteResidentDTO(CarteResident carte) {
        this.reference = carte.getReference();
        this.dateDebut = carte.getDateDebut();
        this.dateFin = carte.getDateFin();
    }
}
