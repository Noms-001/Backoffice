package com.example.backoffice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.HistoriqueDemande;

public interface HistoriqueDemandeRepository extends JpaRepository<HistoriqueDemande, Long> {
    List<HistoriqueDemande> findByDemandeIdOrderByDateChangementDesc(Long id);
}
