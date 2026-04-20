package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.HistoriqueDemande;

public interface HistoriqueDemandeRepository extends JpaRepository<HistoriqueDemande, Long> {
    
}
