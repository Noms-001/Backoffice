package com.example.backoffice.repository;

import com.example.backoffice.entity.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisaRepository extends JpaRepository<Visa, Integer> {

    Optional<Visa> findByReference(String reference);
}