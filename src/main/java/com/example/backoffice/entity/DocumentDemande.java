package com.example.backoffice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_demande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDemande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private Document document;
    
    @ManyToOne
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;
    
    @Column(name = "path", length = 255)
    private String path;
}