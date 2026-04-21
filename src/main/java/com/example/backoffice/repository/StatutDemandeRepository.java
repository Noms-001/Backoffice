package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.StatutDemande;

public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Long> {
    
}
