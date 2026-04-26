package com.example.backoffice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {

    List<Demandeur> findByNomAndPrenomAndAdresseMada(String nom, String prenom, String adresseMada);
}