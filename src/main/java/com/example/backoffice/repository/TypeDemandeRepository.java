package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.TypeDemande;

public interface TypeDemandeRepository extends JpaRepository<TypeDemande, Long> {
    
}
