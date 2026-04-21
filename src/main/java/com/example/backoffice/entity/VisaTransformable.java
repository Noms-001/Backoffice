package com.example.backoffice.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class VisaTransformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroVisa;
    private LocalDate dateEntree;
    private String lieuEntree;
    private LocalDate dateSortie;
    private String lieuSortie;

    @OneToOne
    private Demande demande;

    @ManyToOne
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
        this.numeroVisa = numeroVisa;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public String getLieuEntree() {
        return lieuEntree;
    }

    public void setLieuEntree(String lieuEntree) {
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
