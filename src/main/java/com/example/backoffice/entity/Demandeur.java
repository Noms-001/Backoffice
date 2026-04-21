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
public class Demandeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String nomJeuneFille;
    private LocalDate dateNaissance;
    private String lieuNaissance;

    @ManyToOne
    private SituationFamiliale situationFamiliale;

    @ManyToOne
    private Nationalite nationalite;

    private String adresseMada;
    private String email;
    private String numero;

    @OneToMany(mappedBy = "demandeur")
    private List<Passeport> passeports;

    @OneToMany(mappedBy = "demandeur")
    private List<Demande> demandes;

    public Demandeur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomJeuneFille() {
        return nomJeuneFille;
    }

    public void setNomJeuneFille(String nomJeuneFille) {
        this.nomJeuneFille = nomJeuneFille;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        if (lieuNaissance == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.lieuNaissance = lieuNaissance;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
        if (situationFamiliale == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.situationFamiliale = situationFamiliale;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public void setNationalite(Nationalite nationalite) {
        if (nationalite == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.nationalite = nationalite;
    }

    public String getAdresseMada() {
        return adresseMada;
    }

    public void setAdresseMada(String adresseMada) {
        if (adresseMada == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.adresseMada = adresseMada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero == null) {
            throw new IllegalArgumentException("l'argument est obligatoire");
        }
        this.numero = numero;
    }

    public List<Passeport> getPasseports() {
        return passeports;
    }

    public void setPasseports(List<Passeport> passeports) {
        this.passeports = passeports;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
}
