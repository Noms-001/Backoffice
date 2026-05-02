package com.example.backoffice.repository;

import com.example.backoffice.entity.CarteResident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteResidentRepository extends JpaRepository<CarteResident, Integer> {

    Optional<CarteResident> findByReference(String reference);
}