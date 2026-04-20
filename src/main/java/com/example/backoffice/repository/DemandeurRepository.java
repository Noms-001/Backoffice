package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
    
}