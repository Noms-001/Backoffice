package com.example.backoffice.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_categorie_demande")
    private CategorieDemande categorieDemande;

    @ManyToOne
    @JoinColumn(name = "id_type_demande")
    private TypeDemande typeDemande;

    @ManyToOne
    @JoinColumn(name = "id_demandeur")
    private Demandeur demandeur;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @ManyToMany
    @JoinTable(name = "document_demande", joinColumns = @JoinColumn(name = "id_demande"), inverseJoinColumns = @JoinColumn(name = "id_document"))
    private List<Document> documents;

    public Demande() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieDemande getCategorieDemande() {
        return categorieDemande;
    }

    public void setCategorieDemande(CategorieDemande categorieDemande) {
        this.categorieDemande = categorieDemande;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
