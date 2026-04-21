package com.example.backoffice.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class HistoriqueDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Demande demande;

    @ManyToOne
    private StatutDemande statutDemande;

    private LocalDate dateChangement;
    private String commentaire;

    public HistoriqueDemande() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public StatutDemande getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(StatutDemande statutDemande) {
        this.statutDemande = statutDemande;
    }

    public LocalDate getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDate dateChangement) {
        this.dateChangement = dateChangement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
