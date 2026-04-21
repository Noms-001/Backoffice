package com.example.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.VisaTransformable;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Long> {
    
}