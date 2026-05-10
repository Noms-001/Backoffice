package com.example.backoffice.dto;

import lombok.Data;
import java.time.LocalDate;

import com.example.backoffice.entity.VisaTransformable;

@Data
public class VisaTransformableDTO {

    private String reference;
    private LocalDate dateEntree;
    private String lieuEntree;
    private LocalDate dateSortie;
    private String lieuSortie;

    public VisaTransformableDTO(VisaTransformable visa) {
        this.reference = visa.getNumeroVisa();
        this.dateEntree = visa.getDateEntree();
        this.lieuEntree = visa.getLieuEntree();
        this.dateSortie = visa.getDateSortie();
        this.lieuSortie = visa.getLieuSortie();
    }
}
