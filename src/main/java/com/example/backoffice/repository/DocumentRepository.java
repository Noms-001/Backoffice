package com.example.backoffice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backoffice.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByType(String type);
}
