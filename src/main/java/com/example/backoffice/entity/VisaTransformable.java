package com.example.backoffice.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class VisaTransformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_visa", nullable = false)
    private String numeroVisa;

    @Column(name = "date_entree")
    private LocalDate dateEntree;

    @Column(name = "lieu_entree")
    private String lieuEntree;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @Column(name = "lieu_sortie")
    private String lieuSortie;

    @ManyToOne
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    public VisaTransformable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroVisa() {
        return numeroVisa;
    }

    public void setNumeroVisa(String numeroVisa) {
        if (numeroVisa == null) {
            throw new IllegalArgumentException("Numero visa obligatoire");
        }
        this.numeroVisa = numeroVisa;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(LocalDate dateEntree) {
        if (dateEntree == null) {
            throw new IllegalArgumentException("Date d'entrée obligatoire");
        }
        this.dateEntree = dateEntree;
    }

    public String getLieuEntree() {
        return lieuEntree;
    }

    public void setLieuEntree(String lieuEntree) {
        if (lieuEntree == null) {
            throw new IllegalArgumentException("Lieu d'entrée obligatoire");
        }
        this.lieuEntree = lieuEntree;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getLieuSortie() {
        return lieuSortie;
    }

    public void setLieuSortie(String lieuSortie) {
        this.lieuSortie = lieuSortie;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Passeport getPasseport() {
        return passeport;
    }

    public void setPasseport(Passeport passeport) {
        this.passeport = passeport;
    }
}
