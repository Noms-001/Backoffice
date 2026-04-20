package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.CategorieDemande;

public interface CategorieDemandeRepository extends JpaRepository<CategorieDemande, Long> {
    
}
