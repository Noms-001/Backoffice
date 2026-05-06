package com.example.backoffice.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "id_categorie_demande")
    private CategorieDemande categorieDemande;

    @ManyToOne
    @JoinColumn(name = "id_type_demande")
    private TypeDemande typeDemande;

    @ManyToOne
    @JoinColumn(name = "id_demandeur")
    private Demandeur demandeur;

    @ManyToOne
    @JoinColumn(name = "id_visa_transformable")
    private VisaTransformable visaTransformable;

    @Column(name = "date_demande")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateDemande;

    @OneToMany(mappedBy = "demande", fetch = FetchType.LAZY)
    private List<DocumentDemande> documentDemandes;
}