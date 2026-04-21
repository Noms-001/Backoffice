package com.example.backoffice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HistoriqueDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "id_statut_demande", nullable = false)
    private StatutDemande statutDemande;

    @Column(name = "date_changement")
    private LocalDateTime dateChangement;

    @Column(name = "commentaire")
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

    public LocalDateTime getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDateTime dateChangement) {
        this.dateChangement = dateChangement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
