package com.example.backoffice.dto;

import lombok.Data;
import java.time.LocalDate;

import com.example.backoffice.entity.Passeport;

@Data
public class PasseportDTO {

    private String reference;
    private LocalDate dateDelivrance;
    private String lieuDelivrance;
    private LocalDate dateExpiration;

    public PasseportDTO(Passeport passeport) {
        this.reference = passeport.getReference();
        this.dateDelivrance = passeport.getDateDelivrance();
        this.lieuDelivrance = passeport.getLieuDelivrance();
        this.dateExpiration = passeport.getDateExpiration();
    }
}
