package com.project.achadosperdidos.repository;

import com.project.achadosperdidos.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUserId(Long user_id);
    Document findByNumberDocument(String numberDocument);
}
