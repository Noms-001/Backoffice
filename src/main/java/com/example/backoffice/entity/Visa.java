package com.example.backoffice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "visa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;
}