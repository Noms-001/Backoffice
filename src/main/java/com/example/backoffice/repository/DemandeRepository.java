package com.example.backoffice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.Demande;
import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    Optional<Demande> findByReference(String reference);
}
