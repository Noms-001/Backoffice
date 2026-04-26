package com.example.backoffice.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demandeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String prenom;

    @Column(name = "nom_jeune_fille")
    private String nomJeuneFille;

    @Column(name = "date_naissance", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @ManyToOne
    @JoinColumn(name = "id_situation_familiale", nullable = false)
    private SituationFamiliale situationFamiliale;

    @ManyToOne
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;

    @Column(name = "adresse_mada", nullable = false)
    private String adresseMada;

    private String email;

    @Column(nullable = false)
    private String numero;

    @OneToMany(mappedBy = "demandeur")
    private List<Passeport> passeports;

    @OneToMany(mappedBy = "demandeur")
    private List<Demande> demandes;

    // Méthodes setter avec validation (conservées car Lombok ne gère pas les validations)
    public void setNom(String nom) {
        if (nom == null) {
            throw new IllegalArgumentException("Nom obligatoire");
        }
        this.nom = nom;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            throw new IllegalArgumentException("Date de naissance obligatoire");
        }
        this.dateNaissance = dateNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        if (lieuNaissance == null) {
            throw new IllegalArgumentException("Lieu de naissance obligatoire");
        }
        this.lieuNaissance = lieuNaissance;
    }

    public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
        if (situationFamiliale == null) {
            throw new IllegalArgumentException("Situation famialiale obligatoire");
        }
        this.situationFamiliale = situationFamiliale;
    }

    public void setNationalite(Nationalite nationalite) {
        if (nationalite == null) {
            throw new IllegalArgumentException("Nationalité obligatoire");
        }
        this.nationalite = nationalite;
    }

    public void setAdresseMada(String adresseMada) {
        if (adresseMada == null) {
            throw new IllegalArgumentException("Adresse obligatoire");
        }
        this.adresseMada = adresseMada;
    }

    public void setNumero(String numero) {
        if (numero == null) {
            throw new IllegalArgumentException("Numero obligatoire");
        }
        this.numero = numero;
    }
}