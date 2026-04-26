package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.Passeport;

public interface PasseportRepository extends JpaRepository<Passeport, Long> {
    Passeport findByReference(String reference);
}
