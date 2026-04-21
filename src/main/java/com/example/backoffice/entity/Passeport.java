package com.example.backoffice.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Passeport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private LocalDate dateDelivrance;
    private String lieuDelivrance;
    private LocalDate dateExpiration;

    @ManyToOne
    private Demandeur demandeur;

    @OneToMany(mappedBy = "passeport")
    private List<VisaTransformable> visaTransformables;

    public Passeport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public String getLieuDelivrance() {
        return lieuDelivrance;
    }

    public void setLieuDelivrance(String lieuDelivrance) {
        this.lieuDelivrance = lieuDelivrance;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public List<VisaTransformable> getVisaTransformables() {
        return visaTransformables;
    }

    public void setVisaTransformables(List<VisaTransformable> visaTransformables) {
        this.visaTransformables = visaTransformables;
    }
}
