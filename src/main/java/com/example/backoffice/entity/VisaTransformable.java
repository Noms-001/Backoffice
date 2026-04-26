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
public class VisaTransformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_visa", nullable = false)
    private String numeroVisa;

    @Column(name = "date_entree")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEntree;

    @Column(name = "lieu_entree")
    private String lieuEntree;

    @Column(name = "date_sortie")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateSortie;

    @Column(name = "lieu_sortie")
    private String lieuSortie;

    @OneToMany(mappedBy = "visaTransformable")
    private List<Demande> demandes;

    @ManyToOne
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    // Méthodes setter avec validation (conservées)
    public void setNumeroVisa(String numeroVisa) {
        if (numeroVisa == null) {
            throw new IllegalArgumentException("Numero visa obligatoire");
        }
        this.numeroVisa = numeroVisa;
    }

    public void setDateEntree(LocalDate dateEntree) {
        if (dateEntree == null) {
            throw new IllegalArgumentException("Date d'entrée obligatoire");
        }
        this.dateEntree = dateEntree;
    }

    public void setLieuEntree(String lieuEntree) {
        if (lieuEntree == null) {
            throw new IllegalArgumentException("Lieu d'entrée obligatoire");
        }
        this.lieuEntree = lieuEntree;
    }
}