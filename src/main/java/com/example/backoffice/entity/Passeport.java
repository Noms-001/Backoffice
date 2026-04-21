package com.example.backoffice.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Passeport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(name = "date_delivrance", nullable = false)
    private LocalDate dateDelivrance;

    @Column(name = "lieu_delivrance", nullable = false)
    private String lieuDelivrance;

    @Column(name = "date_expiration", nullable = false)
    private LocalDate dateExpiration;

    @ManyToOne
    @JoinColumn(name = "id_demandeur", nullable = false)
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
        if (reference == null) {
            throw new IllegalArgumentException("Numero de passeport obligatoire");
        }
        this.reference = reference;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        if (dateDelivrance == null) {
            throw new IllegalArgumentException("Date de delivrance obligatoire");
        }
        this.dateDelivrance = dateDelivrance;
    }

    public String getLieuDelivrance() {
        return lieuDelivrance;
    }

    public void setLieuDelivrance(String lieuDelivrance) {
        if (lieuDelivrance == null) {
            throw new IllegalArgumentException("Lieu de delivrance obligatoire");
        }
        this.lieuDelivrance = lieuDelivrance;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        if (dateExpiration == null) {
            throw new IllegalArgumentException("Date d'expiration obligatoire");
        }
        this.dateExpiration = dateExpiration;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (demandeur == null) {
            throw new IllegalArgumentException("Demandeur obligatoire");
        }
        this.demandeur = demandeur;
    }

    public List<VisaTransformable> getVisaTransformables() {
        return visaTransformables;
    }

    public void setVisaTransformables(List<VisaTransformable> visaTransformables) {
        this.visaTransformables = visaTransformables;
    }
}
