package com.example.backoffice.dto;
import java.util.Date;
public class PasseportDTO {
    private String reference; private Date dateDelivrance; private String lieuDelivrance; private Date dateExpiration;
    public PasseportDTO() {}
    public PasseportDTO(String reference, Date dateDelivrance, String lieuDelivrance, Date dateExpiration) {
        this.reference = reference; this.dateDelivrance = dateDelivrance; this.lieuDelivrance = lieuDelivrance; this.dateExpiration = dateExpiration;
    }
}
