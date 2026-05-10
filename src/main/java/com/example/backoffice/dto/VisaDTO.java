package com.example.backoffice.dto;

import lombok.Data;
import java.time.LocalDate;

import com.example.backoffice.entity.Visa;

@Data
public class VisaDTO {

    private String reference;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public VisaDTO(Visa visa) {
        this.reference = visa.getReference();
        this.dateDebut = visa.getDateDebut();
        this.dateFin = visa.getDateFin();
    }
}
