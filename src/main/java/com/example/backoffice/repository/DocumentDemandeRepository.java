package com.example.backoffice.repository;

import com.example.backoffice.entity.DocumentDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentDemandeRepository extends JpaRepository<DocumentDemande, Long> {
    List<DocumentDemande> findByDemandeId(Long idDemande);
    void deleteByDemandeId(Long idDemande);
    DocumentDemande findByDemandeIdAndDocumentId(Long demandeId, Long documentId);
}